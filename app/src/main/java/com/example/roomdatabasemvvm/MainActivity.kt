package com.example.roomdatabasemvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasemvvm.applications.WordsApplication
import com.example.roomdatabasemvvm.database.models.Word
import com.example.roomdatabasemvvm.databinding.ActivityMainBinding
import com.example.roomdatabasemvvm.ui.adapters.WordListAdapter
import com.example.roomdatabasemvvm.ui.view.NewWordActivity
import com.example.roomdatabasemvvm.ui.viewmodels.WordViewModel
import com.example.roomdatabasemvvm.ui.viewmodels.WordViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WordListAdapter
    private lateinit var binding: ActivityMainBinding

    private val wordViewModel : WordViewModel by viewModels{
        WordViewModelFactory((application as WordsApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.recyclerView = binding.recyclerview
        this.adapter = WordListAdapter()
        this.recyclerView.adapter = this.adapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data: Intent? = result.data
                result.data?.let {
                    it.getStringExtra("REPLY")?.let { it1 -> Word(it1) }?.let { it2 ->
                        wordViewModel.insert(it2)
                    }
                }
            }

        }

        this.binding.fab.setOnClickListener {
            resultLauncher.launch(Intent(this, NewWordActivity::class.java))

        }

    }

    override fun onStart() {
        super.onStart()

        wordViewModel.allWords.observe(this, Observer { words ->
            words?.let { adapter.submitList(it) }
        })

    }
}