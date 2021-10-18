package com.mehyo.theproject.di

import com.mehyo.theproject.constant.Constants
import com.mehyo.theproject.network.TodoAPI
import com.mehyo.theproject.repo.DataStoreRepository
import com.mehyo.theproject.repo.NetworkRepository
import com.mehyo.theproject.ui.vm.DataStoreViewModel
import com.mehyo.theproject.ui.vm.ListViewModel
import com.mehyo.theproject.use_case.GetTodosUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//all network modules
val networkModule = module {
    //creating network api using retrofit variable
    fun createNetworkApi(retrofit: Retrofit) = retrofit.create(TodoAPI::class.java)

    //Building retrofit variable
    fun retrofitBuilder() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()

    single { createNetworkApi(retrofit = get()) }
    single { retrofitBuilder() }
}
//all repository modules
val repositoryModule = module {
    single { NetworkRepository(api = get()) }
    single { DataStoreRepository(context = get()) }
}
//all useCase modules
val useCaseModule = module {
    single { GetTodosUseCase(networkRepository= get()) }
}
//all viewModel modules
val viewModelModule = module {
    viewModel { ListViewModel(getTodosUseCase = get()) }
    viewModel { DataStoreViewModel(dataStoreRepository = get()) }
}



