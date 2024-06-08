package com.example.crud.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.crud.database.MyDatabase
import com.example.crud.databinding.ActivityDetailBinding
import com.example.crud.model.NoteModel
import java.util.concurrent.Executors

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var database: MyDatabase
    private var name = ""
    private var description = ""
    var idNote = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idNote = intent.getIntExtra("id", 0)
        name = intent.getStringExtra("name") ?: ""
        description = intent.getStringExtra("description") ?: ""

        binding.detailUserNameTextView.text = name
        binding.detailNoteDescriptionTextView.text = description
        database = MyDatabase.getDatabase(this)
        if (name.isEmpty() && description.isEmpty()) {
            binding.detailUserNameTextView.text = "No data found"
            binding.detailNoteDescriptionTextView.visibility = View.GONE
        }
    }
    //TODO 43 Ketika Kita klik button Edit Data maka data akan terupdate
    fun editData(view: View?) {
        val intent = Intent(this, EditNoteActivity::class.java).apply {
            putExtra("id", idNote)
            putExtra("name", name)
            putExtra("description", description)
        }
        startActivity(intent)
    }

    //TODO 48 Ketika Kita klik button Delete Data maka data akan terhapus
    fun deleteData(view: View) {
        val noteData = NoteModel(name, description).apply {
            id = idNote
        }
        //database tidak bisa diakses langsung di main thread utama
        //TODO 49 Kita perlu Executors agar dapat dieksekusi di tempat yang berbeda diluar EditNoteActivity
        Executors.newSingleThreadExecutor().execute {
            //TODO 50 membuat proses menghapus data Note
            database.noteDao().delete(noteData)
            //Ketika data berhasil terdelete akan pindah ke halaman MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        Toast.makeText(this, "data berhasil di hapus", Toast.LENGTH_SHORT).show()
    }
}