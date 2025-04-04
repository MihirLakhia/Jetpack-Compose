package com.generic.kotlin.data_management.utilities

import android.content.Context

class FileHelper {
    companion object {
        fun getTextFromResources(context: Context, resourceId: Int): String {
            return context.resources.openRawResource(resourceId).use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
        }

        fun getTextFromAssets(context: Context, fileName: String): String {
            return context.assets.open(fileName).use { it ->
                it.bufferedReader().use {
                    it.readText()
                }
            }
        }
    }
}