package ge.dkokaoemna.messenger.Firebase.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.io.Serializable
import java.util.*


@IgnoreExtraProperties
data class Chat (
    @PropertyName("friendNIckName") val friendNIckName: String = "",
    @PropertyName("smses") val smses: ArrayList<Sms> = ArrayList<Sms>()
) : Serializable



@IgnoreExtraProperties
data class Sms (
    @PropertyName("creatorNickName") val creatorNickName: String = "",
    @PropertyName("text") val text: String = ""
) : Serializable
