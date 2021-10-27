package com.pinch.codeassignment.igdb.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pinch.codeassignment.igdb.data.db.GamesDb
import com.pinch.codeassignment.igdb.data.network.IgNetApi
import com.pinch.codeassignment.igdb.data.repository.IgRepository
import com.pinch.codeassignment.igdb.domain.repository.IgRepositoryImpl
import com.pinch.codeassignment.igdb.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().addInterceptor { interceptor ->
        val originalRequest = interceptor.request()
        val requestBuilder = originalRequest.newBuilder()
            .addHeader(Constants.AUTHORIZATION_KEY, Constants.AUTHORIZATION)
            .addHeader(Constants.CLIENT_ID_KEY, Constants.CLIENT_ID)
            .addHeader(Constants.ACCEPT_KEY,Constants.ACCEPT)
            .build()
        interceptor.proceed(requestBuilder)
    }.build()


    @Singleton
    @Provides
    fun provideIgNetApi(retrofit: Retrofit): IgNetApi = retrofit.create(IgNetApi::class.java)


    @Singleton
    @Provides
    fun provideRepository(igNetApi: IgNetApi): IgRepository = IgRepositoryImpl(igNetApi)

    @Singleton
    @Provides
    fun provideGamesDb(@ApplicationContext context: Context): RoomDatabase =
        Room.databaseBuilder(context, GamesDb::class.java, Constants.DATABASE_NAME).build()


    @Named(Constants.IO_DISPATCHER)
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Named(Constants.MAIN_DISPATCHER)
    @Singleton
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}