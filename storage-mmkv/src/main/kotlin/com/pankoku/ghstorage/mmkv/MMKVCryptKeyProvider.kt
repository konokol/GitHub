package com.pankoku.ghstorage.mmkv

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.pankoku.ghstorage.KVStorageException
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * MMKV 加密密钥提供者
 *
 * MMKV 要求以明文字符串形式传入 cryptKey，无法直接使用 AndroidKeyStore 中不可导出的密钥，
 * 因此采用包裹模式：随机生成 MMKV 的 cryptKey，用 KeyStore 持有的 AES-256-GCM 主密钥
 * 加密后落盘，取用时解密还原。落盘内容只有密文与 IV，主密钥始终留在 KeyStore 内。
 */
internal object MMKVCryptKeyProvider {

    private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
    private const val MASTER_KEY_ALIAS = "storage_mmkv_master_key"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val GCM_TAG_BITS = 128

    internal const val PREFS_NAME = "storage_mmkv_crypt"
    private const val KEY_WRAPPED = "wrapped_crypt_key"
    private const val KEY_IV = "wrapped_crypt_key_iv"

    private const val CRYPT_KEY_LENGTH = 32
    private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

    @Volatile
    private var cached: String? = null

    fun getOrCreate(context: Context): String {
        cached?.let { return it }
        return synchronized(this) {
            cached ?: loadOrCreate(context.applicationContext).also { cached = it }
        }
    }

    private fun loadOrCreate(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val wrapped = prefs.getString(KEY_WRAPPED, null)
        val iv = prefs.getString(KEY_IV, null)

        if (wrapped != null && iv != null) {
            return unwrap(wrapped, iv)
        }

        val cryptKey = generateCryptKey()
        val (newWrapped, newIv) = wrap(cryptKey)
        prefs.edit()
            .putString(KEY_WRAPPED, newWrapped)
            .putString(KEY_IV, newIv)
            .apply()
        return cryptKey
    }

    private fun generateCryptKey(): String {
        val random = SecureRandom()
        val builder = StringBuilder(CRYPT_KEY_LENGTH)
        repeat(CRYPT_KEY_LENGTH) {
            builder.append(ALPHABET[random.nextInt(ALPHABET.length)])
        }
        return builder.toString()
    }

    private fun wrap(cryptKey: String): Pair<String, String> {
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, masterKey())
            val ciphertext = cipher.doFinal(cryptKey.toByteArray(Charsets.UTF_8))
            encode(ciphertext) to encode(cipher.iv)
        } catch (e: Exception) {
            throw KVStorageException.EncryptionException("Failed to wrap MMKV crypt key", e)
        }
    }

    private fun unwrap(wrapped: String, iv: String): String {
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(
                Cipher.DECRYPT_MODE,
                masterKey(),
                GCMParameterSpec(GCM_TAG_BITS, decode(iv))
            )
            String(cipher.doFinal(decode(wrapped)), Charsets.UTF_8)
        } catch (e: Exception) {
            throw KVStorageException.EncryptionException(
                "Failed to unwrap MMKV crypt key. The KeyStore master key is unavailable.",
                e
            )
        }
    }

    private fun masterKey(): SecretKey {
        val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).apply { load(null) }
        (keyStore.getEntry(MASTER_KEY_ALIAS, null) as? KeyStore.SecretKeyEntry)?.let {
            return it.secretKey
        }

        val spec = KeyGenParameterSpec.Builder(
            MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .setRandomizedEncryptionRequired(true)
            .build()

        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_PROVIDER)
            .apply { init(spec) }
            .generateKey()
    }

    private fun encode(bytes: ByteArray): String = Base64.encodeToString(bytes, Base64.NO_WRAP)

    private fun decode(value: String): ByteArray = Base64.decode(value, Base64.NO_WRAP)
}
