package com.pankoku.ghstorage.serializer

/**
 * 序列化接口
 *
 * 负责对象的序列化和反序列化，支持不同的序列化策略
 */
interface Serializer<T> {

    /**
     * 序列化对象为字符串
     *
     * @param value 要序列化的对象
     * @return 序列化后的字符串
     */
    fun serialize(value: T): String

    /**
     * 反序列化字符串为对象
     *
     * @param value 要反序列化的字符串
     * @return 反序列化后的对象
     */
    fun deserialize(value: String): T
}