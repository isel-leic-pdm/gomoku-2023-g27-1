package isel.gomuku.repository.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import isel.gomuku.repository.user.model.LoggedUser
import kotlinx.coroutines.flow.first

class UserDataStore(private val dataStore: DataStore<Preferences>) : UserRepository {

    companion object {
        val IdPreferenceKey = stringPreferencesKey("id")
        val NicknamePreferenceKey = stringPreferencesKey("nickname")
        val TokenPreferenceKey = stringPreferencesKey("token")
    }
    override suspend fun getUser(): LoggedUser? {
        val prefs = dataStore.data.first()
        val userName = prefs[NicknamePreferenceKey]
        val id = prefs[IdPreferenceKey]
        val token = prefs[TokenPreferenceKey]

        if (userName.isNullOrEmpty() || id.isNullOrEmpty() || token.isNullOrEmpty())
            return null

        return LoggedUser(id.toInt(),userName,token)
    }

    override suspend fun setUser(loggedUser: LoggedUser) {
        dataStore.edit {
            it[IdPreferenceKey] = loggedUser.id.toString()
            it[NicknamePreferenceKey] = loggedUser.name
            it[TokenPreferenceKey] = loggedUser.token
        }
    }

    override suspend fun deleteUser() {
        dataStore.edit {
            it.clear()
        }
    }
}