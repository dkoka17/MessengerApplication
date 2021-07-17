package ge.dkokaoemna.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser != null){
            Toast.makeText(baseContext, "Authentication Already.",
                    Toast.LENGTH_SHORT).show()
        }else{
        }

        val btn_click_me = findViewById(R.id.button) as Button
        btn_click_me.setOnClickListener {
            createUser();
        }
    }

    fun createUser(){
        var email= "dkok@gmail.com"
        var password = "123Qwesa"
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this@MainActivity, "createUserWithEmail:success", Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                    }
                }

    }
}