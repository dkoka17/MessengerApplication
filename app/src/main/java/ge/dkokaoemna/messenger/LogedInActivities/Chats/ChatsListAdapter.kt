package ge.dkokaoemna.messenger.LogedInActivities.Chats

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.R


class ChatsListAdapter(var list: List<Chat>, private val onItemClicked: (Chat) -> Unit) : RecyclerView.Adapter<ChatViewHolder>()   {


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {


        var item = list[position]

        holder.friendNickname.text = item.friendNIckName

        holder.itemView.setOnClickListener{
            onItemClicked(item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val friendNickname = itemView.findViewById(R.id.friendNickname) as TextView
}