package com.rodrigoelias.testwise.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

// Local entity (to be persisted into the SQLlite database throught Room)
@Entity(tableName = "pokemon")
data class Pokemon(@PrimaryKey @ColumnInfo(name = "id") val Id: Int,
                   @ColumnInfo(name = "name") val name: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }

    // Format: [ "Name (#Number)" ]
    override fun toString(): String {
        return "${name.capitalize()} (#${Id})"
    }
}
