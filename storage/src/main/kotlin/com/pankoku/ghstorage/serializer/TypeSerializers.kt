package com.pankoku.ghstorage.serializer

/**
 * 基本类型序列化器
 *
 * 提供基本类型的序列化和反序列化实现
 */
object TypeSerializers {

    /**
     * Int 序列化器
     */
    object IntSerializer : Serializer<Int> {
        override fun serialize(value: Int): String = value.toString()
        override fun deserialize(value: String): Int = value.toIntOrNull() ?: 0
    }

    /**
     * Long 序列化器
     */
    object LongSerializer : Serializer<Long> {
        override fun serialize(value: Long): String = value.toString()
        override fun deserialize(value: String): Long = value.toLongOrNull() ?: 0L
    }

    /**
     * Float 序列化器
     */
    object FloatSerializer : Serializer<Float> {
        override fun serialize(value: Float): String = value.toString()
        override fun deserialize(value: String): Float = value.toFloatOrNull() ?: 0f
    }

    /**
     * Double 序列化器
     */
    object DoubleSerializer : Serializer<Double> {
        override fun serialize(value: Double): String = value.toString()
        override fun deserialize(value: String): Double = value.toDoubleOrNull() ?: 0.0
    }

    /**
     * Boolean 序列化器
     */
    object BooleanSerializer : Serializer<Boolean> {
        override fun serialize(value: Boolean): String = value.toString()
        override fun deserialize(value: String): Boolean = value.toBoolean()
    }

    /**
     * String 序列化器
     */
    object StringSerializer : Serializer<String> {
        override fun serialize(value: String): String = value
        override fun deserialize(value: String): String = value
    }
}