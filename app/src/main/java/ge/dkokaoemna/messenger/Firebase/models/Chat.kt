package ge.dkokaoemna.messenger.Firebase.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.io.Serializable
import java.util.*


@IgnoreExtraProperties
data class Chat (
    @PropertyName("friendName") val friendName: String = "",
    @PropertyName("smses") val smses: ArrayList<Sms> = ArrayList<Sms>()
) : Serializable



@IgnoreExtraProperties
data class Sms (
    @PropertyName("creatorName") val creatorName: String = "",
    @PropertyName("text") val text: String = ""
) : Serializable
