package com.example.crud.model

import androidx.lifecycle.LiveData
import androidx.room.*


//TODO 16 Dao adalah kelas yang berisi fungsi untuk mengakses data pada database.
@Dao
interface NoteDao {
    @Insert
    fun insertData(noteData: NoteModel)

    @Query("SELECT * FROM NoteModel")
    fun getAll(): LiveData<List<NoteModel>>

    @Update
    fun update(noteData: NoteModel)

    @Delete
    fun delete(noteData: NoteModel)
}