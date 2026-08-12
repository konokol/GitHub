package com.pankoku.ghstorage.serializer

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * JSON 序列化实现
 *
 * 使用 Gson 进行 JSON 序列化和反序列化
 */
class JsonSerializer(private val gson: Gson = Gson()) : Serializer<Any> {

    override fun serialize(value: Any): String {
        return gson.toJson(value)
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(value: String): Any {
        return gson.fromJson(value, Any::class.java)
    }

    /**
     * 反序列化为指定类型
     *
     * @param value JSON 字符串
     * @param typeToken 目标类型
     * @return 反序列化后的对象
     */
    fun <T> deserialize(value: String, typeToken: TypeToken<T>): T {
        return gson.fromJson(value, typeToken)
    }
}