package com.example.crud.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.crud.R
import com.example.crud.adapter.NoteAdapter
import com.example.crud.database.MyDatabase
import com.example.crud.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //  TODO 19 Lateinit memilki arti bahwa sebuah variabel akan di inisialisasi nanti di dalam onCreate
    //   Kita deklarasikan variabel binding dan variabel database
    //   private artinya variabel tersebut hanya akan di akses di class MainActivity saja.
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: MyDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO 20 Kita inisialisasi binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        //TODO 21 Kita inisialisasi database
        database = MyDatabase.getDatabase(this)
        //TODO 22 Kita akan menampilkan data yang disimpan ke dalam adapter berupa list data
        database.noteDao().getAll().observe(this) {
            //adapter dari  binding.adapter = NoteAdapter(it) diambil dari name variabel di activity_main nya
            binding.adapter = NoteAdapter(it) {
                //TODO 39 Kita kirimkan data (parsing data ) yang akan di edit ke  EditNoteActivity menggunakan intent
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("id", it.id)
                    putExtra("name", it.name)
                    putExtra("description", it.description)
                }
                startActivity(intent)
            }
        }
    }
    //TODO 51 Buatlah logic switch ketika switch ada aksi klik
    fun switchDarkMode(isChecked: Boolean) {
        if (binding.switchDarkMode.isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
    //TODO 24 Ketika Kita klik button tambah Data maka akan melakukan intent ( perpindahan ) dari class MainActivity
    // ke class AddNoteActivity
    fun addData(view: View) {
        // Kita intent kan ke halaman AddNoteActivity
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }
}