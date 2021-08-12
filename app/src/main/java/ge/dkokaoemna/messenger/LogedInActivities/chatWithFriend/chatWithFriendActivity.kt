package ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatPresenter
import ge.dkokaoemna.messenger.R
import java.util.*

class chatWithFriendActivity : Activity(){

    private lateinit var recView: RecyclerView
    private lateinit var adapter: smsListAdapter
    private lateinit var presenter: ChatPresenter

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private var position: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_with_friend_layout)

        database = Firebase.database
        auth = Firebase.auth

        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }
        adapter  = smsListAdapter(Collections.emptyList(),email.toString())

        var chat = intent.extras?.get("chat") as Chat

        position = intent.extras?.get("position") as Int


        val smsItems : RecyclerView = findViewById(R.id.smsRecycler)
        smsItems.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        recView = findViewById(R.id.smsRecycler)
        recView.adapter = adapter



        database.getReference("Users").child(email!!).child("chats").child(position.toString()).addValueEventListener(object :
                ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                chat = dataSnapshot.getValue(Chat::class.java) as Chat

                val other: List<Sms> = chat.smses
                adapter.list = other
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(this@ChatsActivity, "error", Toast.LENGTH_SHORT).show()
            }
        })



        var sendMessage : Button = findViewById(R.id.button_gchat_send)
        sendMessage.setOnClickListener {

            var editText = findViewById(R.id.edit_gchat_message) as EditText
            database.getReference("Users").child(email!!).child("chats").child(position.toString()).child("smses").child(chat.size).setValue( Sms(email, editText.text.toString()))
            database.getReference("Users").child(email!!).child("chats").child(position.toString()).child("size").setValue((chat.size.toInt() + 1).toString())
            chat.size =  (chat.size.toInt() + 1).toString()

            database.getReference(chat.friendChatPath).get().addOnSuccessListener {
                var friendChat = it.getValue(Chat::class.java) as Chat


                database.getReference(chat.friendChatPath).child("smses").child(friendChat.size).setValue( Sms(email, editText.text.toString()))
                database.getReference(chat.friendChatPath).child("size").setValue((friendChat.size.toInt() + 1).toString())

            }


        }

    }

}