package ge.dkokaoemna.messenger.LogedInActivities.FindFriends

import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.User

interface IFindFriendsObjView {
    fun showFriendsObjList(UserObjs: List<User>, curUser: User)
    fun newChatCreated(position: Int, chatObj: Chat)
}