package com.imeofon.roomdatabase.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val enterDeparture: String,
    val enterDate: String,
    val enterTime: String,
    val enterDestination: String,
    val enterDate2: String,
    val enterTime2: String,
    val enterTripType: String,
        ): Parcelable