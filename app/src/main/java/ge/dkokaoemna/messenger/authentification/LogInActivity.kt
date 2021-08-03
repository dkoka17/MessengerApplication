package ge.dkokaoemna.messenger.authentification

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.LogInFragment
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatsActivity
import ge.dkokaoemna.messenger.R
import ge.dkokaoemna.messenger.RegisterFragment
import ge.dkokaoemna.messenger.ViewPagerAdapter
import java.io.File
import java.util.*

class LogInActivity : AppCompatActivity()  {

    private lateinit var auth: FirebaseAuth;
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_layout)
        auth = Firebase.auth
        logOut()
        // logIN()

        // Fragments
        createLogInViews()
        addCircleAvatar()
    }

    fun addCircleAvatar() {
        val img : ImageView = findViewById(R.id.avatarImage)
        Glide.with(this)
            .load("https://i.postimg.cc/HL98YZDW/avatar-image-placeholder.png")
            .circleCrop()
            .into(img)
        createLogInViews()
    }

    fun createLogInViews() {
        viewPager = findViewById(R.id.viewPager)
        var logInFragment = LogInFragment(this)
        var registerFragment = RegisterFragment(this)
        val pageAdapter = ViewPagerAdapter(logInFragment, registerFragment, this)
        viewPager.adapter = pageAdapter
        viewPager.setCurrentItem(0, true)
    }

    fun renderRegisterPage() {
        viewPager.setCurrentItem(1, true)
    }

    fun logIN(){
        var email= "12@gmail.com"
        var password = "123Qwesa"

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this@LogInActivity, "Loge In:success", Toast.LENGTH_SHORT).show()

                        moveToChat()
                    } else {
                        Toast.makeText(baseContext, "Loge In failed.",
                                Toast.LENGTH_SHORT).show()

                    }
                }

    }

    //TODO: for developing, delete later
    fun logOut(){
        auth.signOut()
    }

    fun createUser(){
        var email= "dkasf12423ok@gmail.com"
        // TODO: შევაყვანინოთ მხოლოდ nickname და "@gmail.com" ჩვენით დავამატოთ, რადგან ბაზაში რეგისტრაცია მეილით შეიძლება მხოლოდ. nickname-email ეგრე გადმოეცი ფუნქციას
        var nickname = "dato"
        var password = "123Qwesa"
        var job = "deve"

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this@LogInActivity, "createUser:success", Toast.LENGTH_SHORT).show()

                        val database = Firebase.database
                        val ref = database.getReference("Users")

                        // TODO delete next 3 line, leave for time
                        var sm = Sms("asf3","saf3")
                        var arrList1 = ArrayList<Sms>()
                        var ch = Chat("123", arrList1)
                        // END

                        var arrList2 = ArrayList<Chat>()
                        var user = User(nickname,nickname,job,arrList2)

                        auth = Firebase.auth
                        val currentUser = auth.currentUser
                        var key = currentUser?.uid!!
                        ref.child(key).setValue(user)


                        moveToChat()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "createUser failed.",
                                Toast.LENGTH_SHORT).show()

                    }
                }


    }


    fun moveToChat(){
        val intent = Intent(this, ChatsActivity::class.java)
        startActivity(intent)
        finish()
    }
}