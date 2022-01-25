package com.meokg456.note

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.meokg456.note.databinding.ActivityMainBinding
import com.meokg456.note.viewmodel.NotesViewModel
import androidx.navigation.ui.setupActionBarWithNavController
import com.meokg456.note.model.Note
import com.meokg456.note.uistate.NoteUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val notesModel: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNavigation()
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if(intent != null) {
                    val note = intent.getSerializableExtra("note") as Note
                    notesModel.addNote(note)
                }
                // Handle the Intent
            }
        }
        binding.addNote.setOnClickListener{
            val intent = Intent(this, NoteDetail::class.java)
            startForResult.launch(intent)
        }
        notesModel.fetchNotes()
        notesModel.fetchDrafts()
    }


    private fun initBottomNavigation() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigation

        val navController = findNavController(R.id.fragmentContainerView)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.notes, R.id.draft
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


}