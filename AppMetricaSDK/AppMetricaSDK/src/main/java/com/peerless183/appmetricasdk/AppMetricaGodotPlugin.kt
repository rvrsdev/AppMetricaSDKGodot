package com.peerless183.appmetricasdk

import io.appmetrica.analytics.AppMetrica
import android.content.Context
import io.appmetrica.analytics.AppMetricaConfig
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot

class AppMetricaGodotPlugin(godot: Godot?) : GodotPlugin(godot) {
    lateinit var config: AppMetricaConfig.Builder

    override fun getPluginName(): String {
        return "AppMetricaSDK"
    }

    @UsedByGodot
    fun config(apiKey: String) {
        config = AppMetricaConfig.newConfigBuilder(apiKey)
    }


    @UsedByGodot
    fun init() {
        if (godot == null) return

        AppMetrica.activate(activity as Context, config.build())
        android.util.Log.d("AppMetrica", "AppMetrica initialized")
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

}
