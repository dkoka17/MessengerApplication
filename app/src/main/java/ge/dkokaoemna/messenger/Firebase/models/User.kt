package ge.dkokaoemna.messenger.Firebase.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val firstName: String? = null, val lastName: String? = null, val isActive: Boolean? = null)