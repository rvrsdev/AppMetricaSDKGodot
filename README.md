# AppMetricaSDKGodot
AppMetrica SDK плагин для Godot 4.5 и AppMetrica 7.14.0

Содержит базовые функции <ins>**init**</ins>(), <ins>**reportEvent**</ins>() и <ins>**reportAppOpen**</ins>(), а также доп. функцию для отправки события с параметрами <ins>**reportEventWithParams**</ins>().

При создании пользовался [этим туториалом](https://kovardin.ru/articles/godot/mytacker-and-app-metrica/#%D0%BF%D0%BB%D0%B0%D0%B3%D0%B8%D0%BD-appmetrica). В нём версия плагина до версии Godot 4.2 и старой версии метрики, но тут есть более гибкая настройка кастомных функций, так что думаю, кому то пригодится.

Используется файловая система для v1 плагинов **android/plugins** и **gdap файл**. Если использовать [файловую систему для v2 Godot плагинов](https://docs.godotengine.org/en/4.4/tutorials/platform/android/android_plugin.html#packaging-a-v2-android-plugin), то зависимости метрики не смогут подгрузиться в apk (долго пытался понять почему, но так и не понял).

Из важного должен отметить, название gdap файла должно соответствовать имени плагина, возвращаемого функцией <ins>**getPluginName**</ins>().

Для настройки проекта советую пользоваться [этой страницой с документации](https://docs.godotengine.org/en/stable/tutorials/xr/deploying_to_android.html#deploying-to-android).

Также прикрепляю пример базовой настройки инициализации:

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
		printerr("AppMetrica isnt online")</pre>

## Предпросмотр самого скрипта:
<pre>class AppMetricaGodotPlugin(godot: Godot?) : GodotPlugin(godot) {
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
}</pre>
