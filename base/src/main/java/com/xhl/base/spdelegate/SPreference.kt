package com.xhl.base.spdelegate

import android.content.Context
import com.xhl.base.app.BaseApplication
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
* @作者 xhl
* @desc SharedPreferences委托类 公用defaultName:文件名默认，可以在项目中用一个类管理所有文件名，以便清理
* @创建时间 2018/7/11 13:10
*/

class SPreference<T>(val name: String?, val default: T,val defaultName:String="share_data") : ReadWriteProperty<Any?, T> {

    val prefs by lazy {
        BaseApplication.instance.getSharedPreferences(defaultName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> {
                getLong(name, 0)
            }
            is String -> {
                getString(name, default)
            }
            is Float -> {
                getFloat(name, default)
            }
            is Int -> {
                getInt(name, default)
            }
            is Boolean -> {
                getBoolean(name, default)
            }
            else -> {
                throw IllegalArgumentException("This type can't be saved into Preferences")
            }
        }
        res as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Float -> putFloat(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            else -> {
                throw IllegalArgumentException("This type can't be saved into Preferences")
            }
        }.apply()
    }
}