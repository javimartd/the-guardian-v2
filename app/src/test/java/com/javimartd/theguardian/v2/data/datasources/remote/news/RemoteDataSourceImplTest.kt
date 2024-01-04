package com.javimartd.theguardian.v2.data.datasources.remote.news

import com.javimartd.theguardian.v2.data.common.ErrorTypes
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.RemoteDataSource
import com.javimartd.theguardian.v2.data.datasources.remote.RemoteDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.common.RemoteErrorHandlerImpl
import com.javimartd.theguardian.v2.data.datasources.remote.news.mapper.NewsRemoteMapper
import com.javimartd.theguardian.v2.data.repository.news.model.NewsData
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.utils.FileReader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
internal class RemoteDataSourceImplTest {

    private lateinit var sut : RemoteDataSource
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

        sut = RemoteDataSourceImpl(
            getApiService(server),
            NewsRemoteMapper(),
            remoteErrorHandler
        )
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `GIVEN successful response WHEN get news THEN returns success with repository data model`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getNews("", "")

            // then
            Assert.assertTrue(actual.isSuccess)
            actual.fold(
                onSuccess = {
                    Assert.assertEquals(5, it.size)
                    MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(NewsData::class.java))
                },
                onFailure = { /* nothing expected */ }
            )
        }
    }

    @Test
    fun `GIVEN response with empty data WHEN get news THEN returns success with empty data`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news_empty.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getNews("", "")

            // then
            Assert.assertTrue(actual.isSuccess)
            actual.fold(
                onSuccess = {
                    Assert.assertEquals(it, emptyList<NewsData>())
                },
                onFailure = { /* nothing expected */ }
            )
        }
    }

    @Test
    fun `GIVEN response with null data WHEN get news THEN returns success with empty data`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news_null.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getNews("", "")

            // then
            Assert.assertTrue(actual.isSuccess)
            actual.fold(
                onSuccess = {
                    Assert.assertEquals(it, emptyList<NewsData>())
                },
                onFailure = { /* nothing expected */ }
            )
        }
    }

    @Test
    fun `GIVEN response with status error WHEN get news THEN returns error with a status exception`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news_error.json"))
        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getNews("", "")

            // then
            Assert.assertTrue(actual.isFailure)
            actual.fold(
                onSuccess = { /* nothing expected */ },
                onFailure = {
                    MatcherAssert.assertThat(
                        it,
                        IsInstanceOf.instanceOf(ErrorTypes.RemoteErrors.ApiStatus::class.java)
                    )
                }
            )
        }
    }

    @Test
    fun `GIVEN internal server error WHEN get news THEN returns error with a server exception`() {

        // given
        val response = MockResponse()
        response.setResponseCode(500)

        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getNews("", "")

            // then
            Assert.assertTrue(actual.isFailure)
            actual.fold(
                onSuccess = { /* nothing expected */ },
                onFailure = {
                    MatcherAssert.assertThat(
                        it,
                        IsInstanceOf.instanceOf(ErrorTypes.RemoteErrors.Server::class.java)
                    )
                }
            )
        }
    }

    @Test
    fun `GIVEN timeout error WHEN get news THEN returns error with a network exception`() {

        // given
        val response = MockResponse()
        response.setResponseCode(200)
        response.setBody(FileReader.readFileWithoutNewLineFromResources("news.json"))
        response.throttleBody(1024, 2, TimeUnit.SECONDS)

        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getNews("", "")

            // then
            Assert.assertTrue(actual.isFailure)
            actual.fold(
                onSuccess = { /* nothing expected */ },
                onFailure = {
                    MatcherAssert.assertThat(
                        it,
                        IsInstanceOf.instanceOf(ErrorTypes.RemoteErrors.Network::class.java)
                    )
                }
            )
        }
    }

    @Test
    fun `GIVEN successful response WHEN get sections THEN returns success with repository data model`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("sections.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getSections()

            // then
            Assert.assertTrue(actual.isSuccess)
            actual.fold(
                onSuccess = {
                    Assert.assertEquals(22, it.size)
                    MatcherAssert.assertThat(it[0], IsInstanceOf.instanceOf(SectionData::class.java))
                },
                onFailure = { /* nothing expected */ }
            )
        }
    }

    @Test
    fun `GIVEN response with empty data WHEN get sections THEN returns success with empty data`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("sections_empty.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getSections()

            // then
            Assert.assertTrue(actual.isSuccess)
            actual.fold(
                onSuccess = {
                    Assert.assertEquals(it, emptyList<SectionData>())
                },
                onFailure = { /* nothing expected */ }
            )
        }
    }

    @Test
    fun `GIVEN response with null data WHEN get sections THEN returns success with empty data`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("sections_null.json"))
        response.setResponseCode(200)

        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getSections()

            // then
            Assert.assertTrue(actual.isSuccess)
            actual.fold(
                onSuccess = {
                    Assert.assertEquals(it, emptyList<SectionData>())
                },
                onFailure = { /* nothing expected */ }
            )
        }
    }

    @Test
    fun `GIVEN response with status error WHEN get sections THEN returns error with a status exception`() {

        // given
        val response = MockResponse()
        response.setBody(FileReader.readFileWithoutNewLineFromResources("sections_error.json"))
        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getSections()

            // then
            Assert.assertTrue(actual.isFailure)
            actual.fold(
                onSuccess = { /* nothing expected */ },
                onFailure = {
                    MatcherAssert.assertThat(
                        it,
                        IsInstanceOf.instanceOf(ErrorTypes.RemoteErrors.ApiStatus::class.java)
                    )
                }
            )
        }
    }

    @Test
    fun `GIVEN internal server error WHEN get sections THEN returns error with a server exception`() {

        // given
        val response = MockResponse()
        response.setResponseCode(500)

        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getSections()

            // then
            Assert.assertTrue(actual.isFailure)
            actual.fold(
                onSuccess = { /* nothing expected */ },
                onFailure = {
                    MatcherAssert.assertThat(
                        it,
                        IsInstanceOf.instanceOf(ErrorTypes.RemoteErrors.Server::class.java)
                    )
                }
            )
        }
    }

    @Test
    fun `GIVEN timeout error WHEN get sections THEN returns error with a network exception`() {

        // given
        val response = MockResponse()
        response.setResponseCode(200)
        response.setBody(FileReader.readFileWithoutNewLineFromResources("sections.json"))
        response.throttleBody(1024, 2, TimeUnit.SECONDS)

        server.enqueue(response)

        runTest {

            // when
            val actual = sut.getSections()

            // then
            Assert.assertTrue(actual.isFailure)
            actual.fold(
                onSuccess = { /* nothing expected */ },
                onFailure = {
                    MatcherAssert.assertThat(
                        it,
                        IsInstanceOf.instanceOf(ErrorTypes.RemoteErrors.Network::class.java)
                    )
                }
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
    }
}