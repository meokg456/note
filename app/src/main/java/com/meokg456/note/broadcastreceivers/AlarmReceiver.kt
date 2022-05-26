package com.meokg456.note.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.meokg456.note.workers.FetchNoteTotalWorker

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(
            context,
            "Alarm",
            Toast.LENGTH_SHORT
        ).show()
//        for(i in 1..10) {
//            Thread.sleep(1000)
//            Toast.makeText(
//                context,
//                "$i",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
    }
}