package com.kotlin.mypractice.knowledgebox.stepone.externals

import com.kotlin.mypractice.knowledgebox.stepone.models.Error
import com.kotlin.mypractice.knowledgebox.stepone.models.News
import com.kotlin.mypractice.knowledgebox.stepone.providers.NewsStoreInterface
import java.util.*

class NewsHandlerTest : NewsStoreInterface {
    companion object {
        const val SCORE_MAX = 100
    }

    override fun fetchNews(newsHandlerId: String,
                           completionHandler: (News?, Error?) -> Unit) {
        val info1 = "This message is test. Test type ONE."
        val info2 = "This message is test. Test type TWO"
        val info3 = "This message is test. Test type THREE."
        val info4 = "This message is test. Test type FOUR."

        val random = Random()
        val randamScore = random.nextInt(SCORE_MAX)
        val error = if ((randamScore % 5) == 0) Error.NOT_FOUND else null
        val newsInformation = when(randamScore % 5) {
            1 -> info1
            2 -> info2
            3 -> info3
            4 -> info4
            else -> ""
        }

        val news = error?.let { null } ?: News(
            newsHandlerId,
            newsInformation
        )

        completionHandler(news, error)
    }
}
