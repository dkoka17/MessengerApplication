package ge.dkokaoemna.messenger.LogedInActivities.FindFriends

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.Firebase.models.UserName
import ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend.chatWithFriendActivity
import ge.dkokaoemna.messenger.R
import java.io.Serializable
import java.text.FieldPosition
import java.util.*
import kotlin.collections.ArrayList

class FriendsActivity : AppCompatActivity() {

    private lateinit var recView: RecyclerView
    private var adapter: FriendsListAdapter = FriendsListAdapter(this, Collections.emptyList(), ::onItemClicked)
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var searchBox: SearchView
    private lateinit var curUser : User



    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_friends_layout)

        database = Firebase.database
        auth = Firebase.auth

        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }

        val friendsItems : RecyclerView = findViewById(R.id.friendsRecycler)
        friendsItems.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        recView = findViewById(R.id.friendsRecycler)
        recView.adapter = adapter

        var other: ArrayList<User> = ArrayList()
        adapter.list = other
        adapter.notifyDataSetChanged()

        database.getReference("Users").get().addOnSuccessListener {
            curUser = it.child(email!!).getValue(User::class.java) as User
            adapter.curUser = curUser
            for (obj in it.children) {
                var user: User = obj.getValue(User::class.java) as User
                if (user.name.toLowerCase(Locale.ROOT) != email) {
                    other.add(user)
                }
            }
            adapter.list = other
            adapter.notifyDataSetChanged()
        }


        searchBox = findViewById(R.id.search_bar_friends)
        searchBox.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            private var handler: Handler = Handler()
            private var runnable: Runnable? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                return onQueryTextChange(query)
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (runnable != null)
                    handler.removeCallbacks(runnable!!)
                runnable = Runnable {
                    var newList: ArrayList<User> = ArrayList()
                    adapter.list = newList
                    adapter.notifyDataSetChanged()

                    var searchText = newText!!.toLowerCase(Locale.ROOT)
                    if (searchText.isNotEmpty() && searchText.length > 2) {
                        for (curUser in other) {
                            if (curUser.name.toLowerCase(Locale.ROOT).contains(searchText)) {
                                newList.add(curUser)
                            }
                        }
                        adapter.list = newList
                    }
                    else {
                        adapter.list = other
                    }
                    adapter.notifyDataSetChanged()
                }
                handler.postDelayed(runnable!!, 500);
                return false
            }

        })

    }

    fun onItemClicked(chatObj: Chat, createNew: Boolean, friendName: String, position: Int){
        if(createNew){
            createNewChat(chatObj,friendName,position)
        }else{
            val intent = Intent(this, chatWithFriendActivity::class.java)
            intent.putExtra("chat", chatObj as Serializable)
            intent.putExtra("position", position)
            startActivity(intent)
        }

    }

    fun createNewChat(chatObj: Chat, friendName: String, position: Int){

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


            val intent = Intent(this, chatWithFriendActivity::class.java)
            intent.putExtra("chat", chatObj as Serializable)
            intent.putExtra("position", position)
            startActivity(intent)
        }

    }
}


