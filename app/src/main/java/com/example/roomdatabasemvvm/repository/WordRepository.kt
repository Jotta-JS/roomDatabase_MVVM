package com.example.roomdatabasemvvm.repository

import androidx.annotation.WorkerThread
import com.example.roomdatabasemvvm.database.daos.WordDao
import com.example.roomdatabasemvvm.database.models.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizeWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}