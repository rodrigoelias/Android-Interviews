package com.rodrigoelias.testwise.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy

@Database(entities = [(Pokemon::class)], version = 1)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun pokeDao(): PokeDao
}

@Dao
interface PokeDao {
    @Query("SELECT * FROM pokemon")
    fun getPokemons(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmAll(pokemon: List<Pokemon>)
}