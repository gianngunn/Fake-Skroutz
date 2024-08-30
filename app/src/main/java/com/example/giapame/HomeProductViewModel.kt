package com.example.giapame
import android.os.Parcel
import android.os.Parcelable
data class HomeProductViewModel(
    val image: Int,
    val productTitle: Int,
    val productPrice: String,
    val productManufactures: String,
    val productDescription: Int,
    val productColor: String,
    val productAvailability: Int,
    val productSize: String,
    val productCategory: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeInt(productTitle)
        parcel.writeString(productPrice)
        parcel.writeString(productManufactures)
        parcel.writeInt(productDescription)
        parcel.writeString(productColor)
        parcel.writeInt(productAvailability)
        parcel.writeString(productSize)
        parcel.writeString(productCategory)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeProductViewModel> {
        override fun createFromParcel(parcel: Parcel): HomeProductViewModel {
            return HomeProductViewModel(parcel)
        }

        override fun newArray(size: Int): Array<HomeProductViewModel?> {
            return arrayOfNulls(size)
        }
    }
}


