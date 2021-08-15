package ge.dkokaoemna.messenger.LogedInActivities.MyAccount

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.storage.ktx.storage
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class settings : Fragment(), IsettingsObjVIew {

    lateinit var myView: View
    lateinit var updateButton: Button
    lateinit var signOutButton: Button
    lateinit var userName: EditText
    lateinit var job: EditText
    lateinit var img: ImageView
    lateinit var user: User
    private var imgUrl: String = "file:///android_asset/avatar_image_placeholder.png"
    private lateinit var presenter: settingsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.settings_fragment, container, false)
        updateButton = myView.findViewById(R.id.update)
        signOutButton = myView.findViewById(R.id.signOut)
        userName = myView.findViewById(R.id.userNameSettings)
        job = myView.findViewById(R.id.jobSettings)


        presenter = settingsPresenter(this)
        presenter.getUser()



        signOutButton.setOnClickListener {
            var auth: FirebaseAuth  = Firebase.auth
            auth.signOut()
            moveToSignIn()
        }

        updateButton.setOnClickListener {
            val userNameText = userName.text.toString()
            val userNameJob = job.text.toString()
            user.nickname = userNameText
            user.job = userNameJob
            user.imgUrl = imgUrl

            presenter.updateUser(user)

        }
        return myView
    }

    fun addCircleAvatar(url: String) {
        img = myView.findViewById(R.id.avatarImageSettings)
        Glide.with(this)
            .load(url)
            .circleCrop()
            .into(img)
        img.setOnClickListener{
            selectImageFromGalery()
        }
    }

    fun moveToSignIn(){
        val intent = Intent(activity, LogInActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private val pickImage = 100

    fun selectImageFromGalery(){
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == pickImage) {
            var imageUri = data?.data

            presenter.uploadImage(imageUri!!)
        }
    }

    override fun showAccount(curUser: User) {
        user = curUser
        addCircleAvatar(user.imgUrl)
        userName.setText(user.nickname)
        job.setText(user.job)
    }

    override fun imageUploaded(url: String) {
        imgUrl = url
        user.imgUrl = url
        addCircleAvatar(user.imgUrl)
    }

}