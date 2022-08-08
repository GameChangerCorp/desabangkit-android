package id.buildindo.desabangkit.android.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.buildindo.desabangkit.android.MyApplication
import id.buildindo.desabangkit.android.core.data.remote.api.ApiConfig
import id.buildindo.desabangkit.android.core.data.remote.api.ApiService
import id.buildindo.desabangkit.android.ui.pages.SplashScreenActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object{
        lateinit var context: Context
    }

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    fun provideChuckerIntreceptor(@ApplicationContext context: Context) : ChuckerInterceptor{
        return ChuckerInterceptor.Builder(context)
            .collector(ApiConfig().chuckerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    fun provideOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient) : ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api-desabangkit.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }


}