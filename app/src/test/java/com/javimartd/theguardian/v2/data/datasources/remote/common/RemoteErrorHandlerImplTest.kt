package com.javimartd.theguardian.v2.data.datasources.remote.common

import com.javimartd.theguardian.v2.data.common.ErrorTypes
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
internal class RemoteErrorHandlerImplTest {

    private val sut = RemoteErrorHandlerImpl()

    @Test
    fun `GIVEN IOException WHEN get error THEN return network remote error`() =
        runTest {

            //given
            val exception = IOException()

            // when
            val actual = sut.getError(exception)

            // then
            TestCase.assertEquals(actual, ErrorTypes.RemoteErrors.Network)
        }

    @Test
    fun `GIVEN UnAuthorized exception WHEN get error THEN return unAuthorized remote error`() =
        runTest {

            //given
            val exception = HttpException(
                Response.error<ResponseBody>(
                    401,
                    "some content".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )

            // when
            val actual = sut.getError(exception)

            // then
            TestCase.assertEquals(actual, ErrorTypes.RemoteErrors.UnAuthorized)
        }

    @Test
    fun `GIVEN random exception WHEN get error THEN return unknown remote error`() =
        runTest {

            //given
            val exception = IllegalAccessException()

            // when
            val actual = sut.getError(exception)

            // then
            TestCase.assertEquals(actual, ErrorTypes.RemoteErrors.Unknown)
        }
}