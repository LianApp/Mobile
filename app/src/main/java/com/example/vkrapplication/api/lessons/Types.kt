package com.example.vkrapplication.api.lessons

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

public data class Lesson(
    val id: Int,
    @SerializedName("course_id")
    val courseId: Int,
    val title: String,
    @SerializedName("presentation_url")
    val presentationUrl: String,
    @SerializedName("lecture_url")
    val lectureUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(courseId)
        parcel.writeString(title)
        parcel.writeString(presentationUrl)
        parcel.writeString(lectureUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lesson> {
        override fun createFromParcel(parcel: Parcel): Lesson {
            return Lesson(parcel)
        }

        override fun newArray(size: Int): Array<Lesson?> {
            return arrayOfNulls(size)
        }
    }
}
public data class CreateLesson(
    @SerializedName("course_id")
    val courseId: Int,
    val title: String,
    @SerializedName("presentation_url")
    val presentationUrl: String,
    @SerializedName("lecture_url")
    val lectureUrl: String
)