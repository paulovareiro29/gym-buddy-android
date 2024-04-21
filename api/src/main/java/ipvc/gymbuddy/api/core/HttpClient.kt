package ipvc.gymbuddy.api.core

import ipvc.gymbuddy.api.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class HttpClient<T>(service: Class<T>) {
    companion object {
        private const val BASE_URL = BuildConfig.API_URL + "/api/"
    }

    protected var client: T = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
}