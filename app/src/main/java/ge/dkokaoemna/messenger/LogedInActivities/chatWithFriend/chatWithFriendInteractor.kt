package ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend

import android.net.Uri
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.LogedInActivities.MyAccount.IsettingsObjPresenter
import ge.dkokaoemna.messenger.R
import java.util.*


class chatWithFriendInteractor(val presenter: IchatWithFriendObjPresenter) {

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase

    init {

        auth = Firebase.auth
        database = Firebase.database
    }


    fun getTextMessages(){


    }

    fun sendVoice(uri: String, chat: Chat, position: Int){

        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }

        database.getReference("Users").child(email!!).child("chats").child(position.toString()).child("smses").child(chat.size).setValue(Sms(email, Calendar.getInstance().timeInMillis.toString(), "voice",uri))
        database.getReference("Users").child(email!!).child("chats").child(position.toString()).child("size").setValue((chat.size.toInt() + 1).toString())
        chat.size =  (chat.size.toInt() + 1).toString()

        database.getReference(chat.friendChatPath).get().addOnSuccessListener {
            var friendChat = it.getValue(Chat::class.java) as Chat

            database.getReference(chat.friendChatPath).child("smses").child(friendChat.size).setValue(Sms(email,Calendar.getInstance().timeInMillis.toString(), "voice",uri))
            database.getReference(chat.friendChatPath).child("size").setValue((friendChat.size.toInt() + 1).toString())

        }

    }

    fun sendMessage(txt: String, chat: Chat, position: Int){

        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }

        database.getReference("Users").child(email!!).child("chats").child(position.toString()).child("smses").child(chat.size).setValue(Sms(email, Calendar.getInstance().timeInMillis.toString(), "text",txt))
        database.getReference("Users").child(email!!).child("chats").child(position.toString()).child("size").setValue((chat.size.toInt() + 1).toString())
        chat.size =  (chat.size.toInt() + 1).toString()

        database.getReference(chat.friendChatPath).get().addOnSuccessListener {
            var friendChat = it.getValue(Chat::class.java) as Chat

            database.getReference(chat.friendChatPath).child("smses").child(friendChat.size).setValue(Sms(email, Calendar.getInstance().timeInMillis.toString(), "text",txt))
            database.getReference(chat.friendChatPath).child("size").setValue((friendChat.size.toInt() + 1).toString())

        }
    }
}