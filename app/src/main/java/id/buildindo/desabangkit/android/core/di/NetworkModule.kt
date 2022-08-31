package id.buildindo.desabangkit.android.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.buildindo.desabangkit.android.core.data.remote.api.ApiConfig
import id.buildindo.desabangkit.android.core.data.remote.api.ApiDummyService
import id.buildindo.desabangkit.android.core.data.remote.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideChuckInterceptor(@ApplicationContext context: Context) : ChuckerInterceptor{
        return ChuckerInterceptor.Builder(context)
            .collector(ApiConfig().chuckerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    fun provideOkHttpClient(
        chuckInterceptor: ChuckerInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(chuckInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient) : ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-desabangkit.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideApiDummyService(client: OkHttpClient) : ApiDummyService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://desabangkit-backend-dummy.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiDummyService::class.java)
    }
}