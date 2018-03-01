package com.rodrigoelias.testwise.data

import android.arch.persistence.room.*

@Database(entities = [(Pokemon::class)], version = 1)
abstract class PokeDatabase : RoomDatabase() {
    companion object {
//        val appDatabase = Room.databaseBuilder(,
//                PokeDatabase::class.java, "poke-database").build()
//        val userDao=appDatabase.pokeDao()
    }
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