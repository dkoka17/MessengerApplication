package ge.dkokaoemna.messenger.LogedInActivities.Chats

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
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
import ge.dkokaoemna.messenger.LogedInActivities.FindFriends.FriendsActivity
import ge.dkokaoemna.messenger.LogedInActivities.LogInView
import ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend.chatWithFriendActivity
import ge.dkokaoemna.messenger.R
import java.io.Serializable
import java.util.*
import java.util.Collections.emptyList
import kotlin.collections.ArrayList

class ChatsActivity() : Fragment(), IChatsObjView {

    private lateinit var recView: RecyclerView
    private var adapter: ChatsListAdapter = ChatsListAdapter(this, emptyList(), ::onItemClicked)
    private lateinit var presenter: ChatPresenter

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var searchBox: SearchView

    private var listener: OnClickListenerInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.chats_layout, container, false)

        this.listener = activity as OnClickListenerInterface

        database = Firebase.database
        auth = Firebase.auth

        val chatItems : RecyclerView = root.findViewById(R.id.chatsRecycler)
        chatItems.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        recView = root.findViewById(R.id.chatsRecycler)
        recView.adapter = adapter
        recView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                listener?.onMyButtonClick(recyclerView, dy)
            }
        })

        var other: ArrayList<Chat> = ArrayList()
        adapter.list = other
        adapter.notifyDataSetChanged()

        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }

        database.getReference("Users").child(email!!).addValueEventListener(object :
                ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var user: User = dataSnapshot.getValue(User::class.java) as User

                //TODO user გამოვიყენოთ შესაბამის ველების შესაასვებად, რაიმე თ შეიცვალა, მესისჯიჯ მოვიდა და ა.შ. ავტომატურად განახლება

                other = user.chats
                adapter.list = user.chats
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(this@ChatsActivity, "error", Toast.LENGTH_SHORT).show()
            }
        })


        searchBox = root.findViewById(R.id.search_bar_chats)
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
                    var newList: ArrayList<Chat> = ArrayList()
                    adapter.list = newList
                    adapter.notifyDataSetChanged()

                    var searchText = newText!!.toLowerCase(Locale.ROOT)
                    if (searchText.isNotEmpty() && searchText.length > 2) {
                        for (curChat in other) {
                            if (curChat.friendName.toLowerCase(Locale.ROOT).contains(searchText)) {
                                newList.add(curChat)
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

        presenter = ChatPresenter(this)

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

interface OnClickListenerInterface {
    fun onMyButtonClick(recyclerView : RecyclerView, dy: Int)
}