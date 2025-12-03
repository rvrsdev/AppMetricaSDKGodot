# AppMetricaSDKGodot
AppMetrica SDK плагин для Godot 4.5 

Содержит базовые функции <ins>**config**</ins>(), <ins>**init**</ins>(), <ins>**reportEvent**</ins>() и <ins>**reportAppOpen**</ins>().

При создании пользовался [этим туториалом](https://kovardin.ru/articles/godot/mytacker-and-app-metrica/#%D0%BF%D0%BB%D0%B0%D0%B3%D0%B8%D0%BD-appmetrica) (в нём версия плагина до версии Godot 4.2 и старой версии метрики, но тут есть более гибкая настройка кастомных функций. Так что думаю, кому то пригодится).

Используется файловая система для v1 плагинов **android/plugins** и **<ins>gdap</ins> файл**. Если использовать [файловую систему для v2 Godot плагинов](https://docs.godotengine.org/en/4.4/tutorials/platform/android/android_plugin.html#packaging-a-v2-android-plugin), то зависимости метрики не смогут подгрузиться в apk (долго пытался понять почему, но так и не понял).

Для настройки проекта советую пользоваться [этой страницой с документации](https://docs.godotengine.org/en/stable/tutorials/xr/deploying_to_android.html#deploying-to-android).

Также прикрепляю базовую настройку инициализации

<pre>var metrica: Object
func _ready():
	if Engine.has_singleton("AppMetricaSDK"):
		metrica = Engine.get_singleton("AppMetricaSDK")

		metrica.config(apiKey)
		metrica.init()
		metrica.reportAppOpen()
		metrica.reportEvent("Initialized")
		print("AppMetrica is on line")
	else:
		printerr("AppMetrica isnt online")</pre>
    
