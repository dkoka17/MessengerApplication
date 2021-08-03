package ge.dkokaoemna.messenger.Firebase.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.io.Serializable
import java.util.*

@IgnoreExtraProperties
data class User  (
    @PropertyName("name") val name: String = "",
    @PropertyName("nickname") val nickname: String = "",
    @PropertyName("job") val job: String = "",
    @PropertyName("chats") val chats: ArrayList<Chat> = ArrayList<Chat>()
) : Serializable

