# AppMetricaSDKGodot
AppMetrica SDK plugin for Godot 4.5 and AppMetrica 7.14.0

Contains basic functions <ins>**init**</ins>(), <ins>**reportEvent**</ins>(), <ins>**reportAppOpen**</ins>(), <ins>**reportError**</ins>(), and an additional one for sending events with parameters <ins>**reportEventWithParams**</ins>().

I use file system for v1 plugins **android/plugins** and **gdap file**. If you use [file system for v2 Godot plugins](https://docs.godotengine.org/en/4.4/tutorials/platform/android/android_plugin.html#packaging-a-v2-android-plugin), metrics dependencies will not be able to load into the apk (probably yandex fault).

Important to note that the name of gdap file must match the name of the plugin returned by the <ins>**getPluginName**</ins>() function.

To configure the project, I recommend using [this documentation page](https://docs.godotengine.org/en/stable/tutorials/xr/deploying_to_android.html#deploying-to-android).

Also there's [tutorial with more deeply set up of the plugin](https://kovardin.ru/articles/godot/mytacker-and-app-metrica/#%D0%BF%D0%BB%D0%B0%D0%B3%D0%B8%D0%BD-appmetrica), but it's for Godot 4.2 and only in russian.

Example of basic initialization settings:

<pre>var metrica: Object
func _ready():
	if Engine.has_singleton("AppMetricaSDK"):
		metrica = Engine.get_singleton("AppMetricaSDK")

		metrica.init(apiKey)
		metrica.reportAppOpen()
		metrica.reportEvent("Initialized")
		metrica.reportEventWithParams("TestEvent", 1, 2, 3)
		print("AppMetrica is on line")
	else:
		metrica.reportError("Initializing error", "AppMetrica isnt online")
		printerr("AppMetrica isnt online")</pre>

## Preview of the script:
<pre>package com.peerless183.appmetricasdk

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
}</pre>
