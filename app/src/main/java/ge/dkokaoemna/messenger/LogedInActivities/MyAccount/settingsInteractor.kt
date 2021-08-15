package ge.dkokaoemna.messenger.LogedInActivities.MyAccount

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.Firebase.models.UserName
import ge.dkokaoemna.messenger.LogedInActivities.FindFriends.FindFriendInteractor
import ge.dkokaoemna.messenger.LogedInActivities.FindFriends.IFindFriendsObjPresenter
import java.net.URI
import java.util.*


class settingsInteractor(val presenter: IsettingsObjPresenter)  {

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase

    init {

        auth = Firebase.auth
        database = Firebase.database
    }

    fun getAccount(){

        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }
        var user: User = User("", "0","", "", "",ArrayList())
        database.getReference("Users").get().addOnSuccessListener {
            user = it.child(email!!).getValue(User::class.java) as User
            presenter.userFetched(user)
        }
    }


    fun updateAccount(user: User){

        val userNameText = user.name
        val userNameJob = user.job
        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }

        database.getReference("Users").get().addOnSuccessListener {
            var foundUser = false
            for (obj in it.children) {
                val user1: UserName = obj.getValue(UserName::class.java) as UserName
                if (user1.nickname == userNameText) {
                    foundUser = true
                    break
                }
            }
            if (!foundUser) {
                database.getReference("Users").child(email!!).setValue(user)
                presenter.userFetched(user)
                user.nickname = userNameText
            }else{
                getAccount()
            }
        }
    }

    fun uploadImage(uri: Uri){

        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/" + UUID.randomUUID().toString());
        var uploadTask = imageRef.putFile(uri!!)
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
                presenter.imageUploaded(downloadUri.toString())
            }
        }

    }

}