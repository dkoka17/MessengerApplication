package ge.dkokaoemna.messenger.LogedInActivities.FindFriends

import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.User

interface IFindFriendsObjPresenter {

    abstract fun listFetched(userObjs: List<User>, curUser: User)

    abstract fun createChat(chatObj: Chat, friendName: String, position: Int, curUser:User)
}