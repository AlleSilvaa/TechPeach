package com.cp.myapplication.model

import android.os.Parcel
import android.os.Parcelable

data class Itinerario(
    val lugar: String?,
    val nota: String?,
    val avaliacao: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(lugar)
        parcel.writeString(nota)
        parcel.writeString(avaliacao)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Itinerario> {
        override fun createFromParcel(parcel: Parcel): Itinerario {
            return Itinerario(parcel)
        }

        override fun newArray(size: Int): Array<Itinerario?> {
            return arrayOfNulls(size)
        }
    }
}
