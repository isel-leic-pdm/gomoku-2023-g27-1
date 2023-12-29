package isel.gomuku.screens.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class RedirectException(override val message: String ="You must login once more" ) : Exception(),
    Parcelable {
}