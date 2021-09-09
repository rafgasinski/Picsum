package com.example.picsum.di

import com.example.picsum.data.remote.PicsumApi
import com.example.picsum.domain.repository.PicsumRepository
import com.example.picsum.domain.repository.PicsumRepositoryImpl
import com.example.picsum.domain.usecase.GetImagesUseCase
import com.example.picsum.presentation.image_list.ImageListViewModel
import com.example.picsum.utils.PreferencesManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun getRemoteModule(baseUrl: String) = module {
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(PicsumApi::class.java) }

    factory {
        OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("limit", "20")
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
            .build()

            return@Interceptor chain.proceed(request)
        }).build()
    }
}

fun getDomainModule() = module {
    single<PicsumRepository> { PicsumRepositoryImpl(get(), get()) }

    factory { GetImagesUseCase(get()) }
}

fun getPresentationModule() = module {
    viewModel { ImageListViewModel(get()) }
}

fun getUtilsModule() = module {
    single { PreferencesManager(androidContext()) }
}