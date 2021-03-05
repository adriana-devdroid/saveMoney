package com.asantherrera.savemoney365days.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SavingDao {
    @Query("SELECT * FROM Saving")
    fun getAll(): LiveData<List<Saving>>


    @Query("SELECT * FROM Saving WHERE id = :id")
    suspend fun getByID(id: Int): Saving

    @Update
    suspend fun update(saving: Saving)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(saving: Saving)

    @Delete
    suspend fun delete(saving: Saving)

    @Query("DELETE FROM Saving")
    suspend fun deleteAll(): Unit
}