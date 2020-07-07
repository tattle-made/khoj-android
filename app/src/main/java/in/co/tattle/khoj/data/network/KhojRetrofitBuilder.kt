package `in`.co.tattle.khoj.data.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class KhojRetrofitBuilder private constructor(private val context: Context) {

    val BASE_URL = "http://3.7.78.13:3000/"

    val khojApiService: KhojApiService = getRetrofit().create(KhojApiService::class.java)

    private fun getRetrofit(): Retrofit {
        val oktHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(context))
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(oktHttpClient.build())
            .build()
    }

    companion object {
        // Singleton instantiation
        @Volatile
        private var instance: KhojRetrofitBuilder? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: KhojRetrofitBuilder(context).also { instance = it }
            }
    }

}