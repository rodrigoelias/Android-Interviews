package data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "pokemon")
data class Pokemon(@PrimaryKey @ColumnInfo(name = "id") val Id: Int,
                   @ColumnInfo(name = "name") val name: String)
