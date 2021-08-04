package ge.dkokaoemna.messenger.Firebase.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.io.Serializable
import java.util.*

@IgnoreExtraProperties
data class UserName  (
    @PropertyName("nickname") val nickname: String = ""
) : Serializable

