package com.javimartd.theguardian.v2.ui.di

import com.javimartd.theguardian.v2.BuildConfig
import com.javimartd.theguardian.v2.data.Repository
import com.javimartd.theguardian.v2.data.RepositoryImpl
import com.javimartd.theguardian.v2.data.datasources.ErrorHandler
import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSource
import com.javimartd.theguardian.v2.data.datasources.local.LocalDataSourceImpl
import com.javimartd.theguardian.v2.data.datasources.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
class NewsViewModelModule {

    private val baseUrl = "https://content.guardianapis.com"

    @Provides
    @ViewModelScoped
    fun providesRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): Repository {
        return RepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }

    @Provides
    @ViewModelScoped
    fun providesRemoteDataSource(
        apiService: ApiService,
        errorHandler: ErrorHandler
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            apiService,
            errorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun providesLocalDataSource(): LocalDataSource {
        return LocalDataSourceImpl()
    }

    @Provides
    @ViewModelScoped
    fun providesErrorHandler(): ErrorHandler {
        return RemoteErrorHandlerImpl()
    }

    @Provides
    @ViewModelScoped
    fun providesApiKeyInterceptor(): Interceptor {
        return ApiKeyInterceptor()
    }

    @Provides
    @ViewModelScoped
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = if (BuildConfig.DEBUG)
                                HttpLoggingInterceptor.Level.BODY
                            else
                                HttpLoggingInterceptor.Level.NONE
                        }
                    )
                    .addInterceptor(ApiKeyInterceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ViewModelScoped
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}