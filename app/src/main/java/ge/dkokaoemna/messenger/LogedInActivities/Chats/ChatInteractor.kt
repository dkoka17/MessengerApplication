package ge.dkokaoemna.messenger.LogedInActivities.Chats

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.User
import java.util.*

class ChatInteractor(val presenter: IChatsObjPresenter)  {
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase

    init {
        auth = Firebase.auth
        database = Firebase.database
    }


    //TODO აქაა წამოღების ლოგიკები ჩასასმემელი

    fun getACtiveChats(){


        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }

        database.getReference("Users").child(email!!).addValueEventListener(object :
                ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var user: User = dataSnapshot.getValue(User::class.java) as User
                presenter.listFetched(user.chats)

            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(this@ChatsActivity, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun searchInChats(searchText: String, chats: List<Chat>){

        var newList: ArrayList<Chat> = ArrayList()

        if (searchText.isNotEmpty() && searchText.length > 2) {
            for (curChat in chats) {
                if (curChat.friendName.toLowerCase(Locale.ROOT).contains(searchText)) {
                    newList.add(curChat)
                }
            }
            presenter.listFetched(newList)
        } else {
            getACtiveChats()
        }

    }


}
