package `in`.co.tattle.khoj.data.network

import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.PreferenceUtils
import android.content.Context
import android.text.TextUtils
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class KhojRetrofitBuilder private constructor(private val context: Context) {

    val BASE_URL =
        "http://a03161e0f542045168aba70c7b733b1c-555991731.ap-south-1.elb.amazonaws.com:1337"

//    val khojApiService: KhojApiService = getRetrofit().create(KhojApiService::class.java)

    fun getKhojApiService(tokenType: String): KhojApiService {
        return getRetrofit(tokenType).create(KhojApiService::class.java)
    }

    private val loggingInterceptor = HttpLoggingInterceptor()

    private fun getRetrofit(tokenType: String): Retrofit {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val oktHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor(loggingInterceptor)

        if (!TextUtils.isEmpty(tokenType)) {
            oktHttpClient.apply {
                addInterceptor(
                    Interceptor { chain ->
                        lateinit var token: String
                        Log.d(
                            "TESTTTT",
                            "token " + PreferenceUtils.getPrefString(
                                context,
                                PreferenceUtils.USER_TOKEN
                            )
                        )
                        if (TextUtils.equals(tokenType, Constants.USER_TOKEN)) {
                            token =
                                PreferenceUtils.getPrefString(context, PreferenceUtils.USER_TOKEN)!!
                        } else if (TextUtils.equals(tokenType, Constants.CREATOR_TOKEN)) {
                            token =
                                PreferenceUtils.getPrefString(
                                    context,
                                    PreferenceUtils.APP_CREATOR_TOKEN
                                )!!
                        }
                        val builder = chain.request().newBuilder()
                        builder.header("Authorization", "Bearer $token")
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
            }
        }

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