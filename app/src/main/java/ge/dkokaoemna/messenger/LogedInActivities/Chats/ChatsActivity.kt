package ge.dkokaoemna.messenger.LogedInActivities.Chats

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.R
import java.util.Collections.emptyList

class ChatsActivity : AppCompatActivity() {

    private lateinit var recView: RecyclerView
    private  var adapter: ChatsListAdapter = ChatsListAdapter(emptyList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chats_layout)


        val chatItems : RecyclerView = findViewById(R.id.chatsRecycler)
        chatItems.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        adapter = ChatsListAdapter(emptyList())

        recView = findViewById(R.id.chatsRecycler)
        recView.adapter = adapter

        val other: List<Chat> = emptyList()
        adapter.list = other
        adapter.notifyDataSetChanged()
    }
}

