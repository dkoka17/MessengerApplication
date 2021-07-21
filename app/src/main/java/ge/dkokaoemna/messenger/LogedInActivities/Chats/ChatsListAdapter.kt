package ge.dkokaoemna.messenger.LogedInActivities.Chats

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.R


class ChatsListAdapter(var list: List<Chat>) : RecyclerView.Adapter<ChatViewHolder>()   {
    var myList = list

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }
}

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}