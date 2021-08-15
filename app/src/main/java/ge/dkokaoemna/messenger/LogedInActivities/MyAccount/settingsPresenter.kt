package ge.dkokaoemna.messenger.LogedInActivities.MyAccount

import android.net.Uri
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.LogedInActivities.FindFriends.FindFriendInteractor
import ge.dkokaoemna.messenger.LogedInActivities.FindFriends.IFindFriendsObjPresenter
import ge.dkokaoemna.messenger.LogedInActivities.FindFriends.IFindFriendsObjView
import java.net.URI

class settingsPresenter(var view: IsettingsObjVIew ): IsettingsObjPresenter  {

    private val interactor = settingsInteractor(this)

    fun getUser() {
        interactor.getAccount()

    }

    fun updateUser(user: User) {
        interactor.updateAccount(user)
    }

    fun uploadImage(uri: Uri) {
        interactor.uploadImage(uri)
    }

    override fun userFetched(user: User) {
        view?.showAccount(user)
    }

    override fun imageUploaded(url: String) {
        view?.imageUploaded(url)
    }
}