package ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatViewHolder
import ge.dkokaoemna.messenger.R

class smsListAdapter (var list: List<Sms>) : RecyclerView.Adapter<SmsViewHolder>()   {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.sms_list_item, parent, false)
        return SmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int) {
        var item = list[position]
        holder.sms.text = item.text
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class SmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var sms = itemView.findViewById(R.id.sms) as TextView
}