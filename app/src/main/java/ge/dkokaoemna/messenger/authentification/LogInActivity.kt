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
import com.google.firebase.storage.ktx.storage
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.Firebase.models.UserName
import ge.dkokaoemna.messenger.LogedInActivities.LogInView
import ge.dkokaoemna.messenger.R
import java.util.*


class LogInActivity : AppCompatActivity()  {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewPager: ViewPager2
    private var imgUrl: String = "https://firebasestorage.googleapis.com/v0/b/messenger-f7214.appspot.com/o/images%2Favatar-image-placeholder.png?alt=media&token=ac890611-f945-47c0-bce3-c79184098e8d"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_layout)
        auth = Firebase.auth

        // Fragments
        createLogInViews()
        addCircleAvatar()

    }

    fun addCircleAvatar() {

        val img : ImageView = findViewById(R.id.avatarImage)
        updateImage(imgUrl)
        img.setOnClickListener{
            selectImageFromGalery()
        }

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

    private val pickImage = 100
    private var imageUri: Uri? = null

    fun selectImageFromGalery(){
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data

            val storageRef = Firebase.storage.reference
            val imageRef = storageRef.child("images/" + UUID.randomUUID().toString());
            var uploadTask = imageRef.putFile(imageUri!!)
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    imgUrl = downloadUri.toString()
                    updateImage(imgUrl)
                } else {
                    updateImage(imgUrl)
                }
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

                        // TODO delete next 3 line, leave for time
                        var sm = Sms("asf3", "saf3")
                        var arrList1 = ArrayList<Sms>()
                        var ch = Chat("123", arrList1)
                        // END

                        var arrList2 = ArrayList<Chat>()
                        var user = User(nickname, nickname, job,imgUrl, arrList2)

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