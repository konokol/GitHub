package com.pankoku.ghstorage.serializer

/**
 * 字符串序列化实现
 *
 * 直接使用字符串本身，无需转换
 */
class StringSerializer : Serializer<String> {

    override fun serialize(value: String): String {
        return value
    }

    override fun deserialize(value: String): String {
        return value
    }
}