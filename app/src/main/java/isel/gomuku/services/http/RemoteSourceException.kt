package isel.gomuku.services.http

import okhttp3.ResponseBody

data class RemoteSourceException(val body: ResponseBody?,val status: Int) : Exception() {
}