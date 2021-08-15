package ge.dkokaoemna.messenger.LogedInActivities.FindFriends

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.LogedInActivities.Chats.IChatsObjPresenter
import ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend.chatWithFriendActivity
import java.io.Serializable
import java.util.*

class FindFriendInteractor(val presenter: IFindFriendsObjPresenter)  {

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase

    init {
        auth = Firebase.auth
        database = Firebase.database

    }


    fun searchInUsers(searchText: String, users: List<User>, curUser: User){

        var newList: ArrayList<User> = ArrayList()

        if (searchText.isNotEmpty() && searchText.length > 2) {
            for (curUser in users) {
                if (curUser.name.toLowerCase(Locale.ROOT).contains(searchText)) {
                    newList.add(curUser)
                }
            }
            presenter.listFetched(newList,curUser)
        } else {
            getUsers()
        }
    }


    fun getUsers(){

        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }


        var other: ArrayList<User> = ArrayList()

        database.getReference("Users").get().addOnSuccessListener {
            var curUser = it.child(email!!).getValue(User::class.java) as User
            for (obj in it.children) {
                var user: User = obj.getValue(User::class.java) as User
                if (user.name.toLowerCase(Locale.ROOT) != email) {
                    other.add(user)
                }
            }
            presenter.listFetched(other,curUser)
        }
    }


    fun createNewChat(chatObj: Chat, friendName: String, position: Int, curUser:User){

        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }

        val database = Firebase.database

        var arrList1 = java.util.ArrayList<Sms>()

        database.getReference("Users").child(friendName).get().addOnSuccessListener {
            var friend = it.getValue(User::class.java) as User

            var friendPath = "Users/"+ friendName + "/chats/" + friend!!.size


            database.getReference("Users").child(email!!).child("chats").child(curUser.size).setValue(Chat(friendName, friendPath,"0",arrList1));
            database.getReference("Users").child(email!!).child("size").setValue((curUser.size.toInt() + 1).toString())

            var myPath = "Users/"+ email!! + "/chats/" + curUser.size

            database.getReference("Users").child(friendName).child("chats").child(friend.size).setValue(Chat(email!!, myPath,"0",arrList1));
            database.getReference("Users").child(friendName).child("size").setValue((friend.size.toInt() + 1).toString())

            presenter.createChat(chatObj,friendName,position,curUser)
        }

    }
}