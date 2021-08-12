package ge.dkokaoemna.messenger.authentification

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dkokaoemna.messenger.Firebase.models.*
import ge.dkokaoemna.messenger.LogedInActivities.LogInView
import ge.dkokaoemna.messenger.R
import java.util.*


class LogInActivity : AppCompatActivity()  {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewPager: ViewPager2
    private var imgUrl: String = "file:///android_asset/avatar_image_placeholder.png"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_layout)
        auth = Firebase.auth
        // Fragments
        createLogInViews()
        addCircleAvatar()

    }

    fun addCircleAvatar() {

        updateImage(imgUrl)

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

    fun logIN(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this@LogInActivity, "Loge In:success", Toast.LENGTH_SHORT).show()

                        moveToChat()
                    } else {
                        Toast.makeText(baseContext, "Log In failed.",
                                Toast.LENGTH_SHORT).show()

                    }
                }

    }

    fun updateImage(url: String){
        val img : ImageView = findViewById(R.id.avatarImage)
        Glide.with(this)
                .load(url)
                .circleCrop()
                .into(img)
    }


    fun createUser(nickname: String, password: String, job: String){

        if ("" == nickname || "" == password || "" == job) {
            Toast.makeText(baseContext, "Don't leave any field empty",
                    Toast.LENGTH_SHORT).show()
            return
        }

        val email = "$nickname@gmail.com"
        val database = Firebase.database

        var foundUser = false

        database.getReference("Users").get().addOnSuccessListener {
            for (obj in it.children) {
                var user: UserName = obj.getValue(UserName::class.java) as UserName
                if (user.nickname == nickname) {
                    Toast.makeText(baseContext, "User with this nickname already exists",
                            Toast.LENGTH_SHORT).show()
                    foundUser = true
                    break
                }
            }
        }.addOnFailureListener{
            Toast.makeText(baseContext, "User with this nickname already exists",
                    Toast.LENGTH_SHORT).show()
        }

        if (foundUser)
            return

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this@LogInActivity, "createUser:success", Toast.LENGTH_SHORT).show()

                        val database = Firebase.database
                        val ref = database.getReference("Users")


                        var arrList2 = ArrayList<Chat>()
                        var user = User(nickname, "0", nickname, job, imgUrl, arrList2)

                        auth = Firebase.auth
                        val currentUser = auth.currentUser
                        var key = nickname
                        ref.child(key.toLowerCase(Locale.ROOT)).setValue(user)

                        moveToChat()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "createUser failed.",
                                Toast.LENGTH_SHORT).show()

                    }
                }


    }


    fun moveToChat(){
        val intent = Intent(this, LogInView::class.java)
        startActivity(intent)
        finish()
    }
}