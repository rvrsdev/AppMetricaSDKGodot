package com.peerless183.appmetricasdk

import io.appmetrica.analytics.AppMetrica
import android.content.Context
import io.appmetrica.analytics.AppMetricaConfig
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot


class AppMetricaGodotPlugin(godot: Godot?) : GodotPlugin(godot) {
    private var isInitialized = false
    lateinit var config: AppMetricaConfig.Builder

    override fun getPluginName(): String {
        return "AppMetricaSDK"
    }

    @UsedByGodot
    fun init(apiKey: String) {
        val currentActivity = activity ?: return
        if (godot == null) return

        config = AppMetricaConfig.newConfigBuilder(apiKey).withSessionsAutoTrackingEnabled(true)
            .withLocationTracking(false)

        AppMetrica.activate(currentActivity.applicationContext, config.build())
        AppMetrica.setAdvIdentifiersTracking(true)
        AppMetrica.resumeSession(currentActivity)
        isInitialized = true
    }

    @UsedByGodot
    fun reportAppOpen() {
        if (!isInitialized) return

        activity?.let {AppMetrica.reportAppOpen(it)}
    }

    @UsedByGodot
    fun reportEvent(name: String) {
        if (!isInitialized) return

        AppMetrica.reportEvent(name)
    }

    @UsedByGodot
    fun reportError(name: String, message: String) {
        if (!isInitialized) return


        val exception = Exception(message)
        AppMetrica.reportError(name, message, exception)
    }

    @UsedByGodot
    fun reportEventWithParams(name: String, par1: Any, par2: Any, par3: Any) {
        if (!isInitialized) return

        val params = mapOf<String, Any>(
            "par1" to par1,
            "par2" to par2,
            "par3" to par3
        )
        AppMetrica.reportEvent(name, params)
    }
}