package isel.gomuku.screens.authors

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import isel.gomuku.R
import isel.gomuku.ui.theme.GomukuTheme

class AuthorsScreenActivity : ComponentActivity() {

    companion object {
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, AuthorsScreenActivity::class.java)
            source.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GomukuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthorsScreen(
                        backHandle = { this.finish() },
                        sendEmail = { sendEmailIntent(it, "subject", "body") }
                    )
                }
            }
        }
    }

    //make content and subject hardcoded?
    fun sendEmailIntent(email: String, subject: String, content: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(email)
                )
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    subject
                )
                putExtra(Intent.EXTRA_TEXT, content)
            }

            //nao experimentado. Talvez envie email automaticamente?
            //startActivity(Intent.createChooser(intent, "sending"))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast
                .makeText(
                    this,
                    R.string.activity_info_no_suitable_app,
                    Toast.LENGTH_LONG
                )
                .show()
        }
    }
}