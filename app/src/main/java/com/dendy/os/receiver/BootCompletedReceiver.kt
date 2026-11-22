package com.dendy.os.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dendy.os.MainActivity

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.i("DendyOS", "Boot receiver triggered with action: $action")

        if (Intent.ACTION_BOOT_COMPLETED == action ||
            "android.intent.action.QUICKBOOT_POWERON" == action ||
            "com.htc.intent.action.QUICKBOOT_POWERON" == action
        ) {
            val launchIntent = Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            context.startActivity(launchIntent)
        }
    }
}

