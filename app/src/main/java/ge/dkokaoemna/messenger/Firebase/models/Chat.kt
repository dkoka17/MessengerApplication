package ge.dkokaoemna.messenger.Firebase.models

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Chat(
        val friendNIckName: String? = null,
        val creators: Array<Sms>)


@IgnoreExtraProperties
data class Sms(val creatorNickName: String? = null, val text: String? = null)
