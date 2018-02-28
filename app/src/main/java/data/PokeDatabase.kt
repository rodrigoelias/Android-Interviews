package data

import android.arch.persistence.room.*

/**
 * Created by rodrigoelias on 28/02/2018.
 */
@Database(entities = arrayOf(Pokemon::class), version = 1)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun pokeDao(): PokeDao
}

@Dao
interface PokeDao{

    @Query("SELECT * FROM pokemon")
    fun getPokemons(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmAll(pokemon: List<Pokemon>)
}