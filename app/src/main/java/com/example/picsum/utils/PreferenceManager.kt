package com.example.picsum.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesManager(context: Context): SharedPreferences.OnSharedPreferenceChangeListener {

    private val sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    init {
        sharedPrefs.registerOnSharedPreferenceChangeListener(this)
    }

    val currentPage: Int
        get() {
            val page = sharedPrefs.getInt(KEY_CURRENT_PAGE, 1)
            when {
                page > 50 -> {
                    sharedPrefs.edit {
                        putInt(KEY_CURRENT_PAGE, 1)
                        apply()
                    }
                    return 1
                }

                else -> {
                    sharedPrefs.edit {
                        putInt(KEY_CURRENT_PAGE, page+1)
                        apply()
                    }
                    return page
                }
            }
        }

    companion object {
        const val KEY_CURRENT_PAGE = "KEY_CURRENT_PAGE"
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {

    }
}