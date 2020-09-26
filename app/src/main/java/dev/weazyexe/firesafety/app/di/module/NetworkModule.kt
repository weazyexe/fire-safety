package dev.weazyexe.firesafety.app.di.module

import dagger.Module
import dagger.Provides
import dev.weazyexe.firesafety.app.data.network.term.TermsApi
import dev.weazyexe.firesafety.utils.BASE_URL
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideTermsApi(retrofit: Retrofit): TermsApi = retrofit.create(TermsApi::class.java)
}