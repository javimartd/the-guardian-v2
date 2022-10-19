package com.javimartd.theguardian.v2.data

import com.javimartd.theguardian.v2.FileReader
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.remote.*
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@RunWith(MockitoJUnitRunner::class)
class NewsRemoteDataSourceImplTest : TestCase() {

    private lateinit var sut : NewsRemoteDataSource
    private lateinit var server: MockWebServer

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val remoteErrorHandler: ErrorHandler = RemoteErrorHandlerImpl()

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()

        //sut = NewsRemoteDataSourceImpl(getApiService(server), remoteErrorHandler)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    /*@Test
    fun `get news when response successful returns success resource with news`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("", "")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Success::class.java))
            (actual as Result.Success)
            Assert.assertEquals(5, actual.data.size)
        }
    }

    @Test
    fun `get news when response with empty data returns success resource with empty list`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news_empty.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("", "")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Success::class.java))
            (actual as Result.Success)
            Assert.assertEquals(actual.data, emptyList<NewsRaw>())
        }
    }

    @Test
    fun `get news when response with null data returns success resource with empty list`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news_null.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("", "")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Success::class.java))
            (actual as Result.Success)
            Assert.assertEquals(actual.data, emptyList<NewsRaw>())
        }
    }

    @Test
    fun `get news when internal server error returns error resource with a server exception`() {

        // given
        val response = MockResponse()
        response.setResponseCode(500)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("", "")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Error::class.java))
            (actual as Result.Error)
            MatcherAssert.assertThat(
                actual.error,
                instanceOf(ErrorTypes.RemoteErrors.Server::class.java)
            )
        }
    }

    @Test
    fun `get news when response with status error returns error resource with a status exception`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news_error.json"))
        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("", "")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Error::class.java))
            (actual as Result.Error)
            MatcherAssert.assertThat(
                actual.error,
                instanceOf(ErrorTypes.RemoteErrors.ApiStatus::class.java)
            )
        }
    }

    @Test
    fun `get news when timeout error returns error resource with a network exception`() {

        // given
        val response = MockResponse()
        response.setResponseCode(200)
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news.json"))
        response.throttleBody(1024, 2, TimeUnit.SECONDS)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("", "")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Error::class.java))
            (actual as Result.Error)
            MatcherAssert.assertThat(
                actual.error,
                instanceOf(ErrorTypes.RemoteErrors.Network::class.java)
            )
        }
    }

    @Test
    fun `get sections when response successful returns success resource with sections`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("sections.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getSections()

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Success::class.java))
            (actual as Result.Success)
            Assert.assertEquals(22, actual.data.size)
        }
    }

    @Test
    fun `get sections when response with empty data returns success resource with empty list`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("sections_empty.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getSections()

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Success::class.java))
            (actual as Result.Success)
            Assert.assertEquals(actual.data, emptyList<SectionRaw>())
        }
    }

    @Test
    fun `get sections when response with null data returns success resource with empty list`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("sections_null.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getSections()

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Success::class.java))
            (actual as Result.Success)
            Assert.assertEquals(actual.data, emptyList<SectionRaw>())
        }
    }

    @Test
    fun `get sections when internal server error returns error resource with a server exception`() {

        // given
        val response = MockResponse()
        response.setResponseCode(500)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getSections()

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Error::class.java))
            (actual as Result.Error)
            MatcherAssert.assertThat(
                actual.error,
                instanceOf(ErrorTypes.RemoteErrors.Server::class.java)
            )
        }
    }

    @Test
    fun `get sections when response with status error returns error resource with a status exception`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("sections_error.json"))
        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getSections()

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Error::class.java))
            (actual as Result.Error)
            MatcherAssert.assertThat(
                actual.error,
                instanceOf(ErrorTypes.RemoteErrors.ApiStatus::class.java)
            )
        }
    }

    @Test
    fun `get sections when timeout error returns error resource with a network exception`() {

        // given
        val response = MockResponse()
        response.setResponseCode(200)
        response.setBody(FileReader.readFileWithoutNewLineFromResources("sections.json"))
        response.throttleBody(1024, 2, TimeUnit.SECONDS)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getSections()

            // then
            MatcherAssert.assertThat(actual, instanceOf(Result.Error::class.java))
            (actual as Result.Error)
            MatcherAssert.assertThat(
                actual.error,
                instanceOf(ErrorTypes.RemoteErrors.Network::class.java)
            )
        }
    }

    private fun getApiService(server: MockWebServer): NewsApiService {
        return Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }*/
}