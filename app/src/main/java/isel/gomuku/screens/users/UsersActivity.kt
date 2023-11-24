package isel.gomuku.screens.users

import androidx.activity.viewModels
import isel.gomuku.screens.component.BaseComponentActivity

class UsersActivity() : BaseComponentActivity<UsersViewModel>() {

    override val viewModel: UsersViewModel by viewModels()
}