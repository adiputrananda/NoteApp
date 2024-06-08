package com.example.crud.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.example.crud.R
import com.example.crud.database.MyDatabase
import com.example.crud.databinding.ActivityEditNoteBinding
import com.example.crud.model.NoteModel
import com.example.crud.utils.BitmapOperator
import java.io.File
import java.util.concurrent.Executors

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var database: MyDatabase

    var idNote = 0
    var name = ""
    var description = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_note)
        idNote = intent.getIntExtra("id", 0)
        name = intent.getStringExtra("name") ?: ""
        description = intent.getStringExtra("description") ?: ""
        binding.activity = this
        database = MyDatabase.getDatabase(this)

        if (name.isEmpty() && description.isEmpty()) {
            if (validation()) {
                editData(view = null)
            }
        }
    }

    private fun validation(): Boolean {
        if (binding.editUserName.text.toString().trim().isEmpty()) {
            binding.editUserNameTextInputLayout.error = "Username Tidak Boleh Kosong"
            binding.editUserName.requestFocus()
            return false
        } else {
            binding.editUserNameTextInputLayout.isErrorEnabled = false
        }

        if (binding.editNoteDescription.text.toString().trim().isEmpty()) {
            binding.editNoteDescriptionTextInputLayout.error = "Description Tidak Boleh Kosong"
            binding.editNoteDescription.requestFocus()
            return false
        } else {
            binding.editNoteDescriptionTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    //TODO 43 Ketika Kita klik button Edit Data maka data akan terupdate
    fun editData(view: View?) {
        //TODO 44 Membuat kondisi ketika inputan tidak kosong
        if (name.isNotEmpty() && description.isNotEmpty()) {
            //TODO 45 mendeklarasikan variabel note
            val noteData = NoteModel(name, description).apply {
                id = idNote
            }
            //database tidak bisa diakses langsung di main thread utama
            //TODO 46  Kita perlu Executors agar dapat dieksekusi di tempat yang berbeda diluar EditNoteActivity
            Executors.newSingleThreadExecutor().execute {
                //TODO 47 membuat proses menyimpan data note
                database.noteDao().update(noteData)
                //Ketika data berhasil terupdate akan pindah ke halaman MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            Toast.makeText(this, "data berhasil di update", Toast.LENGTH_SHORT).show()
        }
    }
}