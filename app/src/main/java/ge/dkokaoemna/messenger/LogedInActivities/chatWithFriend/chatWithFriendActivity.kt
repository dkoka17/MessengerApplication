package ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatPresenter
import ge.dkokaoemna.messenger.R
import java.util.*

class chatWithFriendActivity : Activity(){

    private lateinit var recView: RecyclerView
    private  var adapter: smsListAdapter = smsListAdapter(Collections.emptyList())
    private lateinit var presenter: ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_with_friend_layout)

        val chat = intent.extras?.get("chat") as Chat


        val smsItems : RecyclerView = findViewById(R.id.smsRecycler)
        smsItems.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        recView = findViewById(R.id.smsRecycler)
        recView.adapter = adapter

        val other: List<Sms> = chat.smses
        adapter.list = other
        adapter.notifyDataSetChanged()

    }
}