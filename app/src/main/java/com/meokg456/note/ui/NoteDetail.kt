package com.meokg456.note.ui

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.meokg456.note.ForegroundServices
import com.meokg456.note.broadcastreceivers.AlarmReceiver
import com.meokg456.note.R
import com.meokg456.note.databinding.ActivityNoteDetailBinding
import com.meokg456.note.model.Note
import com.meokg456.note.viewmodel.NoteDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

const val NOTE_TITLE = "com.meokg456.note.TITLE"
const val NOTE_CONTENT = "com.meokg456.note.CONTENT"
const val EDIT_MODE = "com.meokg456.note.ui.EDIT_MODE"
const val NOTE = "com.meokg456.note.ui.NOTE"
const val REQUEST_IMAGE_CAPTURE = 1
const val REQUEST_CAMERA = 2

@AndroidEntryPoint
class NoteDetail : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailBinding
    private val noteDetailViewModel : NoteDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.note_toolbar))
        noteDetailViewModel.isEditing = intent.getBooleanExtra(EDIT_MODE, false)
        if(noteDetailViewModel.isEditing) {
            noteDetailViewModel.note = intent.getSerializableExtra(NOTE) as Note
        } else {
            noteDetailViewModel.note.title = intent.getStringExtra(NOTE_TITLE) ?: ""
            noteDetailViewModel.note.content = intent.getStringExtra(NOTE_CONTENT) ?: ""
        }
        binding.viewModel = noteDetailViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_actions, menu)
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {

        when (requestCode) {
            REQUEST_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    Log.d("Request permission", "Granted")
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Log.d("Request permission", "Denied")
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.save_note -> {
            if(noteDetailViewModel.isEditing) {
                noteDetailViewModel.note.modifiedAt = Date()
            } else {
                noteDetailViewModel.note.createAt = Calendar.getInstance().time
            }
            noteDetailViewModel.save()

            finish()
            true
        }
        R.id.set_alarm -> {
            val cal = Calendar.getInstance()
            Toast.makeText(this, cal.time.toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "1 phút sau")
                putExtra(AlarmClock.EXTRA_HOUR, cal.get(Calendar.HOUR_OF_DAY))
                putExtra(AlarmClock.EXTRA_MINUTES, cal.get(Calendar.MINUTE) + 1)
            }
            val shareIntent = Intent.createChooser(intent, "Set alarm")
            startActivity(shareIntent)
            true
        }
        R.id.take_picture -> {
            Toast.makeText(this, "Take a picture", Toast.LENGTH_SHORT).show()
            val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
            val shareIntent = Intent.createChooser(intent, "Set alarm")
            startActivityForResult(shareIntent, REQUEST_IMAGE_CAPTURE)
            true
        }
        R.id.insert_contact -> {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                type = ContactsContract.Contacts.CONTENT_TYPE
                putExtra(ContactsContract.Intents.Insert.NAME, "Dung ${UUID.randomUUID()}")
                putExtra(ContactsContract.Intents.Insert.EMAIL, "meokg${UUID.randomUUID()}")
            }
            val shareIntent = Intent.createChooser(intent, "Set alarm")
            startActivityForResult(shareIntent, REQUEST_IMAGE_CAPTURE)
            true
        }
        R.id.camera_permission -> {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {

                }
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected. In this UI,
                    // include a "cancel" or "no thanks" button that allows the user to
                    // continue using your app without granting the permission.
                    Toast.makeText(this, "We need camera permission", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // You can directly ask for the permission.
                    requestPermissions(
                        arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CAMERA
                    )
                }
            }
            true
        }
        R.id.count_demo -> {
            val intent = Intent(this, CountDemo::class.java)
            startActivity(intent)
            true
        }
        R.id.backgroundThreadSave -> {
            if(noteDetailViewModel.isEditing) {
                noteDetailViewModel.note.modifiedAt = Date()
            } else {
                noteDetailViewModel.note.createAt = Calendar.getInstance().time
            }
            noteDetailViewModel.saveWithBackgroundThread()
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(
                    EDIT_MODE, noteDetailViewModel.isEditing
                )
                putExtra(
                    NOTE, noteDetailViewModel.note
                )
            })
            finish()
            true
        }

        R.id.alarmManagerInexact -> {
            val alarmMgr = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(applicationContext, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
            }

            alarmMgr.setExact(
                AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() + 10 * 1000,
                alarmIntent
            )
            true
        }
        R.id.startForeground -> {
            val intent = Intent(applicationContext, ForegroundServices::class.java) // Build the intent for the service
            applicationContext.startForegroundService(intent)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}