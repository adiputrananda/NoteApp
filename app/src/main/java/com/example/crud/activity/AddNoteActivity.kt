package com.example.crud.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.crud.R
import com.example.crud.database.MyDatabase
import com.example.crud.databinding.ActivityAddNoteBinding
import com.example.crud.model.NoteModel
import java.util.concurrent.Executors


class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var database: MyDatabase

    var name = ""
    var description = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        binding.activity = this
        database = MyDatabase.getDatabase(this)

        if (name.isEmpty() && description.isEmpty() ) {
            if (validation()) {
                saveData(view = null)
            }
        }
    }
    private fun validation(): Boolean {
        if (binding.addUserName.text.toString().trim().isEmpty()) {
            binding.addUserNameTextInputLayout.error = "Username Tidak Boleh Kosong"
            binding.addUserName.requestFocus()
            return false
        } else {
            binding.addUserNameTextInputLayout.isErrorEnabled = false
        }

        if (binding.addNoteDescription.text.toString().trim().isEmpty()) {
            binding.addNoteDescriptionTextInputLayout.error = "Description Tidak Boleh Kosong"
            binding.addNoteDescription.requestFocus()
            return false
        } else {
            binding.addNoteDescriptionTextInputLayout.isErrorEnabled = false
        }

        return true
    }

    fun saveData(view: View?) {
        Log.d("tes data", "$name $description")
        if (name.isNotEmpty() && description.isNotEmpty()) {
            val noteData = NoteModel(name, description)
            Executors.newSingleThreadExecutor().execute {
                database.noteDao().insertData(noteData)
            }
            Toast.makeText(this, "data berhasil di simpan", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
