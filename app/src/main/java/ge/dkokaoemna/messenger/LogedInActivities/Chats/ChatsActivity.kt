package ge.dkokaoemna.messenger.LogedInActivities.Chats

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
import ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend.chatWithFriendActivity
import ge.dkokaoemna.messenger.R
import java.io.Serializable
import java.util.*
import java.util.Collections.emptyList


class ChatsActivity : Fragment(), IChatsObjView {

    private lateinit var recView: RecyclerView
    private  var adapter: ChatsListAdapter = ChatsListAdapter(emptyList(), ::onItemClicked)
    private lateinit var presenter: ChatPresnter

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.chats_layout, container, false)

        database = Firebase.database
        auth = Firebase.auth

        val chatItems : RecyclerView = root.findViewById(R.id.chatsRecycler)
        chatItems.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        recView = root.findViewById(R.id.chatsRecycler)
        recView.adapter = adapter

        val other: List<Chat> = emptyList()
        adapter.list = other
        adapter.notifyDataSetChanged()

        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }

        database.getReference("Users").child(email!!).addValueEventListener(object :
                ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var user: User = dataSnapshot.getValue(User::class.java) as User

                //TODO user გამოვიყენოთ შესაბამის ველების შესაასვებად, რაიმე თ შეიცვალა, მესისჯიჯ მოვიდა და ა.შ. ავტომატურად განახლება

                adapter.list = user.chats
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(this@ChatsActivity, "error", Toast.LENGTH_SHORT).show()
            }
        })

        presenter = ChatPresnter(this)

        return root
    }


    override fun showChatObjList(ChatObjs: List<Chat>) {
        //TODO("Not yet implemented")
    }

    fun onItemClicked(chatObj: Chat){

        val intent = Intent(activity, chatWithFriendActivity::class.java)
        intent.putExtra("chat", chatObj as Serializable)
        startActivity(intent)

    }
}

