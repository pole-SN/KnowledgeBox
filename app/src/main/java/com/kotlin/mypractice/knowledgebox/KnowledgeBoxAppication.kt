package com.kotlin.mypractice.knowledgebox

import android.app.Application
import io.realm.Realm

class KnowledgeBoxAppication : Application() {
    companion object {
        lateinit var instance: KnowledgeBoxAppication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Realm.init(this)
    }
}
