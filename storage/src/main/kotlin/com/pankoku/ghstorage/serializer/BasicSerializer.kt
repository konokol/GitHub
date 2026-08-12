package com.pankoku.ghstorage.serializer

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 基本序列化器接口
 *
 * 提供类型安全的序列化和反序列化方法
 */
interface BasicSerializer<T> : Serializer<T> {

    /**
     * 将字符串值反序列化为目标类型
     */
    suspend fun deserializeString(value: String?): T?

    /**
     * 将目标类型序列化为字符串值
     */
    suspend fun serializeString(value: T?): String?

    /**
     * 监听流并转换类型
     */
    fun watchFlow(flow: Flow<String?>): Flow<T?>
}

/**
 * 字符串类型序列化器
 */
class StringBasicSerializer : BasicSerializer<String?> {
    override fun serialize(value: String?): String = value ?: ""

    override fun deserialize(value: String): String? = value.ifEmpty { null }

    override suspend fun deserializeString(value: String?): String? = value

    override suspend fun serializeString(value: String?): String? = value

    override fun watchFlow(flow: Flow<String?>): Flow<String?> = flow
}

/**
 * Int 类型序列化器
 */
class IntBasicSerializer : BasicSerializer<Int?> {
    override fun serialize(value: Int?): String = value?.toString() ?: ""

    override fun deserialize(value: String): Int? = value.toIntOrNull()

    override suspend fun deserializeString(value: String?): Int? = value?.toIntOrNull()

    override suspend fun serializeString(value: Int?): String? = value?.toString()

    override fun watchFlow(flow: Flow<String?>): Flow<Int?> = flow.map { it?.toIntOrNull() }
}

/**
 * Long 类型序列化器
 */
class LongBasicSerializer : BasicSerializer<Long?> {
    override fun serialize(value: Long?): String = value?.toString() ?: ""

    override fun deserialize(value: String): Long? = value.toLongOrNull()

    override suspend fun deserializeString(value: String?): Long? = value?.toLongOrNull()

    override suspend fun serializeString(value: Long?): String? = value?.toString()

    override fun watchFlow(flow: Flow<String?>): Flow<Long?> = flow.map { it?.toLongOrNull() }
}

/**
 * Float 类型序列化器
 */
class FloatBasicSerializer : BasicSerializer<Float?> {
    override fun serialize(value: Float?): String = value?.toString() ?: ""

    override fun deserialize(value: String): Float? = value.toFloatOrNull()

    override suspend fun deserializeString(value: String?): Float? = value?.toFloatOrNull()

    override suspend fun serializeString(value: Float?): String? = value?.toString()

    override fun watchFlow(flow: Flow<String?>): Flow<Float?> = flow.map { it?.toFloatOrNull() }
}

/**
 * Double 类型序列化器
 */
class DoubleBasicSerializer : BasicSerializer<Double?> {
    override fun serialize(value: Double?): String = value?.toString() ?: ""

    override fun deserialize(value: String): Double? = value.toDoubleOrNull()

    override suspend fun deserializeString(value: String?): Double? = value?.toDoubleOrNull()

    override suspend fun serializeString(value: Double?): String? = value?.toString()

    override fun watchFlow(flow: Flow<String?>): Flow<Double?> = flow.map { it?.toDoubleOrNull() }
}

/**
 * Boolean 类型序列化器
 */
class BooleanBasicSerializer : BasicSerializer<Boolean?> {
    override fun serialize(value: Boolean?): String = value?.toString() ?: ""

    override fun deserialize(value: String): Boolean? = value.toBooleanStrictOrNull()

    override suspend fun deserializeString(value: String?): Boolean? = value?.toBooleanStrictOrNull()

    override suspend fun serializeString(value: Boolean?): String? = value?.toString()

    override fun watchFlow(flow: Flow<String?>): Flow<Boolean?> = flow.map { it?.toBooleanStrictOrNull() }
}

/**
 * 获取类型对应的序列化器
 */
fun <T> getBasicSerializer(clazz: Class<T>): BasicSerializer<*> {
    @Suppress("UNCHECKED_CAST")
    return when (clazz) {
        String::class.java -> StringBasicSerializer()
        Int::class.java, Int::class.javaPrimitiveType -> IntBasicSerializer()
        Long::class.java, Long::class.javaPrimitiveType -> LongBasicSerializer()
        Float::class.java, Float::class.javaPrimitiveType -> FloatBasicSerializer()
        Double::class.java, Double::class.javaPrimitiveType -> DoubleBasicSerializer()
        Boolean::class.java, Boolean::class.javaPrimitiveType -> BooleanBasicSerializer()
        else -> throw IllegalArgumentException("Unsupported type: ${clazz.name}")
    }
}