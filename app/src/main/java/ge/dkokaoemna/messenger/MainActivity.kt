package ge.dkokaoemna.messenger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatsActivity
import ge.dkokaoemna.messenger.LogedInActivities.LogInView
import ge.dkokaoemna.messenger.authentification.LogInActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        auth.signOut()
        val currentUser = auth.currentUser

        if(currentUser != null){
            moveToChat()
        }else{
            moveToSignIn()
        }

    }

    fun moveToChat(){
        val intent = Intent(this, LogInView::class.java)
        startActivity(intent)
        finish()
    }

    fun moveToSignIn(){
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
        finish()
    }

}