package ge.dkokaoemna.messenger.LogedInActivities.FindFriends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatViewHolder
import ge.dkokaoemna.messenger.R
import java.util.*

class FriendsListAdapter(var act: FriendsActivity, var list: List<User>, private val onItemClicked: (Chat) -> Unit) : RecyclerView.Adapter<FriendsViewHolder>(){

    lateinit var curUser : User

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.add_friend_list_item, parent, false)
        return FriendsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        var item = list[position]

        holder.userNickname.text = item.nickname
        holder.userJob.text = item.job
        Glide.with(act)
            .load(item.imgUrl)
            .circleCrop()
            .into(holder.userAvatar)

        val chats : List<Chat> = curUser.chats
        var chat : Chat = Chat()
        for (chatItem in chats) {
            if (chatItem.friendName == item.name.toLowerCase(Locale.ROOT)) {
                chat = chatItem
                break
            }
        }

        holder.itemView.setOnClickListener{
            onItemClicked(chat)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userNickname = itemView.findViewById(R.id.userNickname) as TextView
    val userJob = itemView.findViewById(R.id.userJob) as TextView
    val userAvatar = itemView.findViewById(R.id.userAvatar) as ImageView
}
