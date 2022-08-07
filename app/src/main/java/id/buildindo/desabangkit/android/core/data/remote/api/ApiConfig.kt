package id.buildindo.desabangkit.android.core.data.remote.api

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import id.buildindo.desabangkit.android.ui.pages.SplashScreenActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private val chuckerCollector = ChuckerCollector(
        context = SplashScreenActivity.appContext,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    // Create the Interceptor
    private val chuckerInterceptor = ChuckerInterceptor.Builder(SplashScreenActivity.appContext)
        .collector(chuckerCollector)
        .maxContentLength(250_000L)
        .alwaysReadResponseBody(true)
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(chuckerInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://api-desabangkit.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    val apiInstance: ApiService = retrofit.create(ApiService::class.java)
}

