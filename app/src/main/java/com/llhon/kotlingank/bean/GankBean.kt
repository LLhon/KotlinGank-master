package com.llhon.kotlingank.bean

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.bean
 * @author LLhon
 * @Date 2018/3/27 10:08
 * @description
 */
data class GankBean(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String,
        val images: List<String>
) {
    fun hasImage(): Boolean {
        return images != null
    }

    fun publishDate(): String {
        return publishedAt.substring(0, 10)
    }
}