package com.kotlin.mypractice.knowledgebox.stepone.services

import com.kotlin.mypractice.knowledgebox.stepone.models.Category
import com.kotlin.mypractice.knowledgebox.stepone.models.NewsHandler
import com.kotlin.mypractice.knowledgebox.stepone.providers.NewsHandlerStoreInterface
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NewsHandlerObject(
    @PrimaryKey open var newsHandlerId: String = "",
    open var name: String = "",
    open var category: String? = null
) : RealmObject() {
    companion object {
        fun from(newsHandler: NewsHandler): NewsHandlerObject {
            return NewsHandlerObject(
                newsHandler.newsHandlerId,
                newsHandler.title,
                newsHandler.category?.name
            )
        }
    }

    val newsHandler: NewsHandler
        get() = NewsHandler(
            newsHandlerId,
            name,
            category?.let { Category.from(it) }
        )
}

class NewsHandlerRealmStore : NewsHandlerStoreInterface {
    override fun fetchNewsHandlers(): List<NewsHandler>? {
        return Realm.getDefaultInstance().use {
            it.where(NewsHandlerObject::class.java)
                .findAll()
                .map { it.newsHandler }
        }
    }

    override fun updateNewsHandler(newsHandler: NewsHandler) {
        val newsHandlerObject = NewsHandlerObject.from(newsHandler)

        Realm.getDefaultInstance().use {
            it.run {
                beginTransaction()
                copyToRealmOrUpdate(newsHandlerObject)
                commitTransaction()
            }
        }
    }

    override fun deleteNewsHandler(newsHandlerId: String) {
        Realm.getDefaultInstance().use {
            val newsHandlerObject = it.where(NewsHandlerObject::class.java)
                .equalTo("newsHandlerId", newsHandlerId)
                .findFirst()

            it.run {
                beginTransaction()
                newsHandlerObject.deleteFromRealm()
                commitTransaction()
            }
        }
    }
}
