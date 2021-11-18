package com.javimartd.theguardian.v2.data.datasources.remote

import com.javimartd.theguardian.v2.FileReader
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.model.RawSection
import com.javimartd.theguardian.v2.data.state.ErrorTypes
import com.javimartd.theguardian.v2.data.state.Resource
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
class RemoteDataSourceImplTest : TestCase() {

    private lateinit var sut : RemoteDataSource

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val remoteErrorHandler: ErrorHandler = RemoteErrorHandlerImpl()

    /**
     * A @Before annotation on a method specifies that it will run before every test.
     * Any method with the @After annotation will run after every test. Use these annotations
     * to keep initialization logic common to all tests.
     */
    @Before
    fun setup() {}

    @After
    fun shutdown() {}

    @Test
    fun `api successful returns success resource with news`() {

        val server = MockWebServer()
        server.start()

        sut = RemoteDataSourceImpl(getApiService(server), remoteErrorHandler)

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Resource.Success::class.java))
            Assert.assertEquals(5, actual.data?.size)
        }

        server.shutdown()
    }

    @Test
    fun `api empty data returns success resource with empty news list`() {

        val server = MockWebServer()
        server.start()

        sut = RemoteDataSourceImpl(getApiService(server), remoteErrorHandler)

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news_empty.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Resource.Success::class.java))
            Assert.assertEquals(actual.data, emptyList<RawSection>())
        }

        server.shutdown()
    }

    @Test
    fun `api null data returns success resource with empty news list`() {

        val server = MockWebServer()
        server.start()

        sut = RemoteDataSourceImpl(getApiService(server), remoteErrorHandler)

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news_null.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Resource.Success::class.java))
            Assert.assertEquals(actual.data, emptyList<RawSection>())
        }

        server.shutdown()
    }

    @Test
    fun `internal error getting news returns error resource with a server exception`() {

        val server = MockWebServer()
        server.start()

        sut = RemoteDataSourceImpl(getApiService(server), remoteErrorHandler)

        // given
        val response = MockResponse()
        response.setResponseCode(500)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Resource.Error::class.java))
            MatcherAssert.assertThat(
                actual.errorTypes,
                instanceOf(ErrorTypes.RemoteErrors.Server::class.java)
            )
        }

        server.shutdown()
    }

    @Test
    fun `api error status getting news returns error resource with an api status exception`() {

        val server = MockWebServer()
        server.start()

        sut = RemoteDataSourceImpl(getApiService(server), remoteErrorHandler)

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news_error.json"))
        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Resource.Error::class.java))
            MatcherAssert.assertThat(
                actual.errorTypes,
                instanceOf(ErrorTypes.RemoteErrors.ApiStatus::class.java)
            )
        }

        server.shutdown()
    }

    @Test
    fun `timeout error getting news returns error resource with a network exception`() {

        val server = MockWebServer()
        server.start()

        sut = RemoteDataSourceImpl(getApiService(server), remoteErrorHandler)

        // given
        val response = MockResponse()
        response.setResponseCode(200)
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news.json"))
        response.throttleBody(1024, 2, TimeUnit.SECONDS)

        server.enqueue(response)

        runBlocking {
            // when
            val actual = sut.getNews("")

            // then
            MatcherAssert.assertThat(actual, instanceOf(Resource.Error::class.java))
            MatcherAssert.assertThat(
                actual.errorTypes,
                instanceOf(ErrorTypes.RemoteErrors.Network::class.java)
            )
        }

        server.shutdown()
    }

    @Test
    fun `api successful returns success resource with sections`() {

    }

    @Test
    fun `api empty data returns success resource with empty sections list`() {

    }

    @Test
    fun `api null data returns success resource with empty sections list`() {

    }

    private fun getApiService(server: MockWebServer): ApiService {
        return Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}