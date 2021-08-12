package ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.R

class smsListAdapter(var list: List<Sms>, var myName: String) : RecyclerView.Adapter<SmsViewHolder>()   {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    override fun getItemViewType(position: Int): Int {

        var item = list[position]
        if (item.creatorName == myName) {
            return VIEW_TYPE_MESSAGE_SENT;
        }else{
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder {
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.sms_list_me_item, parent, false)
            return SmsViewHolder(view,viewType)
        } else {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.sms_list_receive_item, parent, false)
            return SmsViewHolder(view,viewType)
        }

    }

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int) {
        var item = list[position]

        if (item.creatorName == myName) {
            holder.sms.text = item.text
            holder.time.text = "10:00"
        }else{
            holder.sms.text = item.text
            holder.time.text = "12:00"
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class SmsViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

    lateinit var sms : TextView
    lateinit var time : TextView


    init {
        if (viewType == 2){
            sms = itemView.findViewById(R.id.text_gchat_message_other) as TextView
            time = itemView.findViewById(R.id.text_gchat_timestamp_other) as TextView
        }else{
            sms = itemView.findViewById(R.id.text_gchat_message_me) as TextView
            time = itemView.findViewById(R.id.text_gchat_timestamp_me) as TextView

        }
    }

}

