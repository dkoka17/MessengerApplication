package ge.dkokaoemna.messenger.Firebase.models

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class User(val name: String? = null, val nickname: String? = null, val job: String? = null, val chats: ArrayList<Chat>)