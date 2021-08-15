package ge.dkokaoemna.messenger.LogedInActivities.MyAccount

import android.net.Uri
import ge.dkokaoemna.messenger.Firebase.models.User

interface IsettingsObjVIew {
    fun showAccount(user: User)

    fun imageUploaded(url: String)
}