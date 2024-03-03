package com.example.laba4mobile2

import android.os.Parcel
import android.os.Parcelable

data class Settings(
    var numberOfPlayers: Int,
    var numberOfSkips: Int,
    var numberOfSpins: Int
): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(numberOfPlayers)
        parcel.writeInt(numberOfSkips)
        parcel.writeInt(numberOfSpins)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Settings> {
        override fun createFromParcel(parcel: Parcel): Settings {
            return Settings(parcel)
        }

        override fun newArray(size: Int): Array<Settings?> {
            return arrayOfNulls(size)
        }
    }
}
