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

    fun searchInUsers(searchText: String, users: List<User>, curUser: User){
        interactor.searchInUsers(searchText, users, curUser)
    }

    override fun listFetched(userObjs: List<User>, curUser: User) {
        view?.showFriendsObjList(userObjs,curUser)
    }



    fun createChat(chatObj: Chat, friendName: String, position: Int, curUser:User) {
        interactor.createNewChat(chatObj, friendName, position,curUser)
    }

    override fun newChatCreated(position: Int, chatObj: Chat) {
        view?.newChatCreated(position,chatObj)
    }
}