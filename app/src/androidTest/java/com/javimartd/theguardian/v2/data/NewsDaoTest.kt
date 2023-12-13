package com.javimartd.theguardian.v2.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.javimartd.theguardian.v2.factory.LocalDataSourceFactory
import com.javimartd.theguardian.v2.data.datasources.local.AppDatabase
import com.javimartd.theguardian.v2.data.datasources.local.news.NewsDao
import com.javimartd.theguardian.v2.data.datasources.local.news.db.NewsEntity
import com.javimartd.theguardian.v2.utils.randomString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
// annotation tells the compiler that it is test run frequently and it execution time should be
// less than 200ms.
@SmallTest
class NewsDaoTest {

    private val ctx = InstrumentationRegistry.getInstrumentation().context

    /*@get: Rule
    val dispatcherRule = TestDispatcherRule()*/

    private lateinit var itemDao: NewsDao
    private lateinit var itemDb: AppDatabase

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        itemDb = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
        itemDao = itemDb.newsDao()
    }

    @After
    fun cleanup() {
        itemDb.close()
    }

    @Test
    fun add_item_should_return_the_items_in_flow() = runTest {

        // given
        val news = LocalDataSourceFactory.getSomeNews(3)

        // when
        itemDao.insertAll(news)

        // then
        itemDao.getAll().test {
            val list = awaitItem()
            assert(list.contains(news[0]))
            assert(list.contains(news[1]))
            cancel()
        }
    }

    @Test
    fun replace_item_should_return_the_item_in_flow() = runTest {

        // given
        val news = LocalDataSourceFactory.getSomeNews(3)

        val item = NewsEntity(
            newsId = news[0].newsId,
            body = randomString(),
            date = "2022-11-26T16:22:34Z",
            sectionId = randomString(),
            sectionName = randomString(),
            title = randomString(),
            thumbnail = randomString(),
            webUrl = randomString()
        )

        // when
        itemDao.insertAll(news)
        itemDao.insert(item)

        // then
        itemDao.getAll().test {
            val list = awaitItem()
            assert(list.size == 3)
            assert(list.contains(item))
            cancel()
        }
    }

    @Test
    fun removed_item_shouldNot_be_present_in_flow() = runTest {

        // given
        val news = LocalDataSourceFactory.getSomeNews(2)

        // when
        itemDao.insertAll(news)
        itemDao.removeItem(news[1])

        // then
        itemDao.getAll().test {
            val list = awaitItem()
            assert(list.size == 1)
            assert(list.contains(news[0]))
            cancel()
        }
    }
}