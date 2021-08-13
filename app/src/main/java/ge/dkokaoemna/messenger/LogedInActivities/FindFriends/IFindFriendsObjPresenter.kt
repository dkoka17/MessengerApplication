package ge.dkokaoemna.messenger.LogedInActivities.FindFriends

import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.User

interface IFindFriendsObjPresenter {

    abstract fun listFetched(userObjs: List<User>)
}