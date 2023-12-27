package isel.gomuku.userTests

import isel.gomuku.repository.user.UserMem
import isel.gomuku.screens.users.UsersViewModel
import isel.gomuku.services.http.user.FakeUserService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class UserAuthViewModelTest {
    @Test
    fun test_viewModel_login(){
        runTest{
            val viewModel = UsersViewModel(FakeUserService(UserMem()))
            val name = "Test"
            viewModel.inputName = name
            viewModel.inputPassword = " Test"
            viewModel.login()
            advanceTimeBy(1000)
            assertTrue(viewModel.isLoading)
            advanceTimeBy(3000)
            val user = viewModel.user
            requireNotNull(user)
            assertEquals(name,user.name)
        }
    }
    @Test
    fun test_viewModel_login_and_loads_and_logs_out(){
        runTest {
            val viewModel = UsersViewModel(FakeUserService(UserMem()))
            viewModel.inputName = "Test"
            viewModel.inputPassword = " Test"
            viewModel.login()
            advanceTimeBy(1000)
            assertTrue(viewModel.isLoading)
            advanceTimeBy(3000)
            assertNotNull(viewModel.user)
            assertFalse(viewModel.isLoading)
            viewModel.logout()
            advanceTimeBy(1000)
            assertTrue(viewModel.isLoading)
            advanceTimeBy(3000)
            assertNull(viewModel.user)
            assertFalse(viewModel.isLoading)
        }
    }
}