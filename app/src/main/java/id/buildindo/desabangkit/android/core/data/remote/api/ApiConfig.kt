package id.buildindo.desabangkit.android.core.data.remote.api

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import id.buildindo.desabangkit.android.MyApplication
import id.buildindo.desabangkit.android.ui.pages.SplashScreenActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    val chuckerCollector = ChuckerCollector(
        context = MyApplication.appContext,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )
}

