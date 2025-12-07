package com.peerless183.appmetricasdk

import io.appmetrica.analytics.AppMetrica
import android.content.Context
import android.util.Log
import io.appmetrica.analytics.AppMetricaConfig
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot

class AppMetricaGodotPlugin(godot: Godot?) : GodotPlugin(godot) {
    private var isInitialized = false

    override fun getPluginName(): String {
        return "AppMetricaSDK"
    }

    @UsedByGodot
    fun init(apiKey: String) {
        if (godot == null) return

        try {
            val config = AppMetricaConfig.newConfigBuilder(apiKey).build()
            AppMetrica.activate(activity as Context, config)
            isInitialized = true
            Log.d("AppMetrica", "AppMetrica initialized successfully")
        } catch (e: Exception) {
            Log.e("AppMetrica", "Failed to initialize: ${e.message}")
        }
    }


    @UsedByGodot
    fun reportEvent(name: String) {
        AppMetrica.reportEvent(name)
    }

    @UsedByGodot
    fun reportAppOpen(){
        activity?.let {
            AppMetrica.reportAppOpen(it)
        }
    }

    @UsedByGodot
    fun reportEventWithParams(name: String, par1: Any, par2: Any, par3: Any){
        if(!isInitialized) return

        val params = mapOf<String, Any>(
            "par1" to par1,
            "par2" to par2,
            "par3" to par3
        )
        AppMetrica.reportEvent(name, params)
//      AppMetrica.sendEventsBuffer() - Опционально
    }

}
