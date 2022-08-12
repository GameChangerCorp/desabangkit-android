package id.buildindo.desabangkit.android.core.data.remote.api

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.RetentionManager
import id.buildindo.desabangkit.android.MyApplication

class ApiConfig {
    val chuckerCollector = ChuckerCollector(
        context = MyApplication.appContext,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )
}

