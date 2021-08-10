package ge.dkokaoemna.messenger.LogedInActivities.FindFriends

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
import ge.dkokaoemna.messenger.Firebase.models.UserName
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatPresenter
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatsListAdapter
import ge.dkokaoemna.messenger.LogedInActivities.Chats.IChatsObjView
import ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend.chatWithFriendActivity
import ge.dkokaoemna.messenger.R
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class FriendsActivity : AppCompatActivity() {

    private lateinit var recView: RecyclerView
    private var adapter: FriendsListAdapter = FriendsListAdapter(this, Collections.emptyList(), ::onItemClicked)
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth;


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
            var curUser = it.child(email!!).getValue(User::class.java) as User
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

    }


    fun onItemClicked(chatObj: Chat){
        val intent = Intent(this, chatWithFriendActivity::class.java)
        intent.putExtra("chat", chatObj as Serializable)
        startActivity(intent)
    }
}

