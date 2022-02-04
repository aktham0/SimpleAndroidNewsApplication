package com.app.aktham.newsapplication

import android.app.Application
import com.app.aktham.newsapplication.utils.Constants
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.HiltAndroidApp
import kotlin.system.exitProcess

@HiltAndroidApp
class NewsApplication : Application()