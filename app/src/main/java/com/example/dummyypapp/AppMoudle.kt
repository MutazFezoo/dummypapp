package com.example.dummyypapp


import com.example.dummyypapp.service.UserService
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://dummyapi.io/data/v1/"
   // private const val BASE_URL = BuildConfig.BASE_URL


    val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
    val cache = Cache(DummyApplication.getAppContext().cacheDir, cacheSize)

    private var offlineInterceptor: Interceptor = Interceptor { chain ->
        var request: Request = chain.request()
        if (!ConfigApp.internetAvailable(DummyApplication.getAppContext())) {
            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        chain.proceed(request)
    }


    private var onlineInterceptor: Interceptor = Interceptor { chain ->
        val response: Response = chain.proceed(chain.request())
        val maxAge = 320
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }
    private val offlineClient = OkHttpClient.Builder()
        .connectTimeout(10000, TimeUnit.MILLISECONDS)
        .writeTimeout(10000, TimeUnit.MILLISECONDS)
        .readTimeout(10000, TimeUnit.MILLISECONDS)
        //.addInterceptor{ it.proceed(it.request().newBuilder().addHeader("app-id", BuildConfig.APP_ID).build())}
        .addInterceptor{ it.proceed(it.request().newBuilder().addHeader("app-id", "652456ff1a3996c04f3b560c").build())}
        .addInterceptor(offlineInterceptor)
        .addNetworkInterceptor(onlineInterceptor)
        .cache(cache)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(offlineClient)
            .build()


    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)




}