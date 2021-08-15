package ge.dkokaoemna.messenger.LogedInActivities.MyAccount

import ge.dkokaoemna.messenger.Firebase.models.User

interface IsettingsObjPresenter {
    abstract fun userFetched(user: User)
    abstract fun imageUploaded(url: String)
}