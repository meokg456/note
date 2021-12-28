package com.meokg456.note

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.meokg456.note.core.Note
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

const val NOTE_TITLE = "com.meokg456.note.TITLE"
const val NOTE_CONTENT = "com.meokg456.note.CONTENT"
const val REQUEST_IMAGE_CAPTURE = 1
const val REQUEST_CAMERA = 2

class NoteDetail : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)
        setSupportActionBar(findViewById(R.id.note_toolbar))
        val title = intent.getStringExtra(NOTE_TITLE) ?: ""
        val content = intent.getStringExtra(NOTE_CONTENT) ?: ""
        titleEditText = findViewById(R.id.editTitle)
        titleEditText.setText(title)
        contentEditText = findViewById(R.id.editContent)
        contentEditText.setText(content)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_actions, menu)
        return true
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.save_note -> {
            // User chose the "Settings" item, show the app settings UI...
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
                        REQUEST_CAMERA)
                }
            }
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}