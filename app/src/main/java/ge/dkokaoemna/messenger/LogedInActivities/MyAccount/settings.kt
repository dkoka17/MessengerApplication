package ge.dkokaoemna.messenger.LogedInActivities.MyAccount

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.Firebase.models.UserName
import ge.dkokaoemna.messenger.R
import ge.dkokaoemna.messenger.authentification.LogInActivity
import java.util.*
import kotlin.collections.ArrayList

class settings : Fragment() {

    lateinit var myView: View
    lateinit var updateButton: Button
    lateinit var signOutButton: Button
    lateinit var userName: EditText
    lateinit var job: EditText
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.settings_fragment, container, false)
        val img : ImageView = myView.findViewById(R.id.avatarImageSettings);
        addCircleAvatar(img)
        updateButton = myView.findViewById(R.id.update)
        signOutButton = myView.findViewById(R.id.signOut)
        userName = myView.findViewById(R.id.userNameSettings)
        job = myView.findViewById(R.id.jobSettings)

        auth = Firebase.auth
        database = Firebase.database
        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }
        var user: User = User("", "", "", ArrayList())
        database.getReference("Users").get().addOnSuccessListener {
            user = it.child(email!!).getValue(User::class.java) as User
            userName.setText(user.nickname)
            job.setText(user.job)
        }

        signOutButton.setOnClickListener {
            auth.signOut()
            moveToSignIn()
        }

        updateButton.setOnClickListener {
            val userNameText = userName.text.toString()
            val userNameJob = job.text.toString()
            user.nickname = userNameText
            user.job = userNameJob
            database.getReference("Users").child(email!!).setValue(user)
        }
        return myView
    }

    fun addCircleAvatar(img: ImageView) {
        Glide.with(this)
            .load("https://i.postimg.cc/HL98YZDW/avatar-image-placeholder.png")
            .circleCrop()
            .into(img)
    }

    fun moveToSignIn(){
        val intent = Intent(activity, LogInActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}