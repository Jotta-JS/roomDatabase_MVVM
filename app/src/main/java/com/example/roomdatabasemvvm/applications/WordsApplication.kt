package com.example.roomdatabasemvvm.applications

import android.app.Application
import com.example.roomdatabasemvvm.database.WordRoomDatabase
import com.example.roomdatabasemvvm.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    //Para a Application funcionar, devemos declará-la no adnroid manifest com android:name = ".application....."
    val database by lazy { WordRoomDatabase.getDatabase(scope = applicationScope, context = this)}
    val repository by lazy { WordRepository(database.wordDao()) }

}