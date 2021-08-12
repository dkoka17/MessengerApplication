package ge.dkokaoemna.messenger.LogedInActivities.Chats

import android.annotation.SuppressLint
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
import ge.dkokaoemna.messenger.R
import org.w3c.dom.Text
import java.util.*


class ChatsListAdapter(var frag: Fragment, var list: List<Chat>, private val onItemClicked: (Chat, Int) -> Unit) : RecyclerView.Adapter<ChatViewHolder>()   {

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        var item = list[position]

        val database = Firebase.database
        database.getReference("Users").get().addOnSuccessListener {
            var user = it.child(item.friendName).getValue(User::class.java) as User


            holder.friendNickname.text = user.nickname

            if(item.smses.size > 1) {

                val diff: Long = Calendar.getInstance().timeInMillis - item.smses[item.smses.size - 1].time.toLong()
                val seconds = diff / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24

                if (minutes < 60) {
                    holder.date.text = "$minutes min"
                }
                else if (hours < 24) {
                    holder.date.text = "$hours hour"
                }
                else {
                    holder.date.text = "$days day"
                }

                holder.lastSms.text = item.smses[item.smses.size - 1].text
            }
            Glide.with(frag)
                .load(user.imgUrl)
                .circleCrop()
                .into(holder.friendAvatar)
        }

        holder.itemView.setOnClickListener{
            onItemClicked(item,position)
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
    val lastSms = itemView.findViewById(R.id.lastSms) as TextView
    val friendAvatar = itemView.findViewById(R.id.friendAvatar) as ImageView
    val date = itemView.findViewById(R.id.lastSmsDate) as TextView
}