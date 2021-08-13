package ge.dkokaoemna.messenger.LogedInActivities.FindFriends

import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatInteractor
import ge.dkokaoemna.messenger.LogedInActivities.Chats.IChatsObjPresenter
import ge.dkokaoemna.messenger.LogedInActivities.Chats.IChatsObjView
import java.net.UnknownServiceException

class FindFriendPresenter(var view: IFindFriendsObjView?): IFindFriendsObjPresenter {

    private val interactor = FindFriendInteractor(this)

    fun getUsers() {
        interactor.getUsers()
    }

    override fun listFetched(userObjs: List<User>) {
        view?.showFriendsObjList(userObjs)
    }
}