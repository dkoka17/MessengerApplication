package ge.dkokaoemna.messenger.LogedInActivities.FindFriends

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
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
import ge.dkokaoemna.messenger.LogedInActivities.MyAccount.settingsPresenter
import ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend.chatWithFriendActivity
import ge.dkokaoemna.messenger.R
import java.io.Serializable
import java.text.FieldPosition
import java.util.*
import kotlin.collections.ArrayList

class FriendsActivity : AppCompatActivity(), IFindFriendsObjView {

    private lateinit var recView: RecyclerView
    private var adapter: FriendsListAdapter = FriendsListAdapter(this, Collections.emptyList(), ::onItemClicked)
    private lateinit var searchBox: SearchView
    private lateinit var curUser : User
    private lateinit var userList : List<User>
    private lateinit var presenter: FindFriendPresenter



    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_friends_layout)

        presenter = FindFriendPresenter(this)
        presenter.getUsers()
        val backButton : ImageView = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
        val friendsItems : RecyclerView = findViewById(R.id.friendsRecycler)
        friendsItems.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recView = findViewById(R.id.friendsRecycler)
        recView.adapter = adapter




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

                    presenter.searchInUsers(newText!!.toLowerCase(Locale.ROOT),userList,curUser)

                }
                handler.postDelayed(runnable!!, 500);
                return false
            }

        })

    }

    fun onItemClicked(chatObj: Chat, createNew: Boolean, friendName: String, position: Int){
        if(createNew){
            presenter.createChat(chatObj, friendName, position, curUser)
        }else{
            val intent = Intent(this, chatWithFriendActivity::class.java)
            intent.putExtra("chat", chatObj as Serializable)
            intent.putExtra("position", position)
            startActivity(intent)
        }

    }



    override fun showFriendsObjList(UserObjs: List<User>, curUser: User) {
        adapter.list = UserObjs
        this.userList = UserObjs
        this.curUser = curUser
        adapter.curUser = curUser

        adapter.notifyDataSetChanged()
    }

    override fun newChatCreated(position: Int, chatObj: Chat) {

        val intent = Intent(this, chatWithFriendActivity::class.java)
        intent.putExtra("chat", chatObj as Serializable)
        intent.putExtra("position", position)
        startActivity(intent)
    }
}


