package com.meokg456.note.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.meokg456.note.databinding.ActivityMainBinding
import com.meokg456.note.viewmodel.NotesViewModel
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.WorkManager
import com.meokg456.note.R
import com.meokg456.note.Settings
import com.meokg456.note.User
import com.meokg456.note.appconfig.settingsDataStore
import com.meokg456.note.model.Note
import com.meokg456.note.ui.lifecycleawarecomponent.ActivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var activityObserver: ActivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityObserver = ActivityObserver()
        lifecycle.addObserver(activityObserver)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
        val darkMode = booleanPreferencesKey("dark_mode")
        val darkModeFlow: Flow<Boolean> = dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[darkMode] ?: false
            }

        lifecycleScope.launchWhenCreated {
            darkModeFlow.collectLatest {
                Log.d("Dark mode", if(it) "true" else "false")
            }
        }
        lifecycleScope.launchWhenCreated {
            settingsDataStore.data.collectLatest {
                Log.d("User Proto", it.user.name + " " + it.user.age)
            }
        }
        binding.addNote.setOnClickListener{
            val intent = Intent(this, NoteDetail::class.java)
            startActivity(intent)
            lifecycleScope.launch {
                dataStore.edit { settings ->
                    val current = settings[darkMode] ?: false
                    settings[darkMode] = !current
                }
                settingsDataStore.updateData {
                    it.toBuilder().setUser(User.newBuilder().setName("Dung").setAge(10)).build()
                }
            }
        }

    }


    private fun initBottomNavigation() {

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