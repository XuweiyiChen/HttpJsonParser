package com.xuweic.dotify

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo (
    val username: String,
    val firstName: String,
    val lastName: String,
    val hasNose: Boolean,
    val platform: String,
    val profilePicURL: String
): Parcelable

/**
{
"username": "whomustnotbenamed",
"firstName": "Tom",
"lastName": "Riddle",
"hasNose": false,
"platform": 9.75,
"profilePicURL": "https://picsum.photos/seed/voldemort/256"
}
 **/