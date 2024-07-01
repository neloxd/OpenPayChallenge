package com.jesusvilla.core.network.connection

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jesusvilla.core.BuildConfig
import com.jesusvilla.core.network.connection.NetworkConfiguration.TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @BaseURL
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    @GsonInjection
    fun provideGson() = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    @BodyInterceptor
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    @BuilderQualifier
    fun provideBuilderClient(
        @BodyInterceptor httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        if(BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }

        return builder
    }

    @Provides
    @Singleton
    @AuthenticationQualifier
    fun provideAuthenticationInterceptor() = AuthenticationInterceptor()

    @Provides
    @Singleton
    @RetrofitApi
    fun provideRetrofitAuth(
        @ApplicationContext context: Context,
        @BaseURL baseUrl: String,
        @GsonInjection gson: Gson,
        @BuilderQualifier builder: OkHttpClient.Builder,
        @AuthenticationQualifier authenticationInterceptor: AuthenticationInterceptor
    ): Retrofit {
        builder.addInterceptor(OpenpayInterceptor())
        builder.addInterceptor(ChuckerInterceptor(context))
        //builder.addNetworkInterceptor(authenticationInterceptor)
        val client: OkHttpClient = builder.build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @RetrofitDynamic
    fun provideRetrofitDynamic(
        @ApplicationContext context: Context,
        @BaseURL baseUrl: String,
        @GsonInjection gson: Gson,
        @BuilderQualifier builder: OkHttpClient.Builder,
    ): Retrofit {
        builder.addInterceptor(OpenpayInterceptor())
        builder.addInterceptor(ChuckerInterceptor(context))
        //builder.addNetworkInterceptor(authenticationInterceptor)
        builder.addInterceptor(HostSelectorInterceptor())
        builder.followRedirects(true)
        builder.followSslRedirects(true)
        val client: OkHttpClient = builder.build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}