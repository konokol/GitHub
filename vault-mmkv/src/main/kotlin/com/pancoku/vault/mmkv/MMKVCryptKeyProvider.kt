package com.pancoku.vault.mmkv

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.pancoku.vault.KVStorageException
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object MMKVCryptKeyProvider {
    private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
    private const val MASTER_KEY_ALIAS = "vault_mmkv_master_key"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val GCM_TAG_BITS = 128
    internal const val PREFS_NAME = "vault_mmkv_crypt"
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
        if (wrapped != null && iv != null) return unwrap(wrapped, iv)
        val cryptKey = generateCryptKey()
        val (newWrapped, newIv) = wrap(cryptKey)
        prefs.edit().putString(KEY_WRAPPED, newWrapped).putString(KEY_IV, newIv).apply()
        return cryptKey
    }

    private fun generateCryptKey(): String {
        val random = SecureRandom()
        return (1..CRYPT_KEY_LENGTH).map { ALPHABET[random.nextInt(ALPHABET.length)] }.joinToString("")
    }

    private fun wrap(cryptKey: String): Pair<String, String> {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, masterKey())
        val ciphertext = cipher.doFinal(cryptKey.toByteArray(Charsets.UTF_8))
        return encode(ciphertext) to encode(cipher.iv)
    }

    private fun unwrap(wrapped: String, iv: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, masterKey(), GCMParameterSpec(GCM_TAG_BITS, decode(iv)))
        return String(cipher.doFinal(decode(wrapped)), Charsets.UTF_8)
    }

    private fun masterKey(): SecretKey {
        val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).apply { load(null) }
        (keyStore.getEntry(MASTER_KEY_ALIAS, null) as? KeyStore.SecretKeyEntry)?.let { return it.secretKey }
        val spec = KeyGenParameterSpec.Builder(MASTER_KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .setRandomizedEncryptionRequired(true)
            .build()
        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_PROVIDER).apply { init(spec) }.generateKey()
    }

    private fun encode(bytes: ByteArray): String = Base64.encodeToString(bytes, Base64.NO_WRAP)
    private fun decode(value: String): ByteArray = Base64.decode(value, Base64.NO_WRAP)
}