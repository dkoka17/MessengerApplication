package ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend

import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.Firebase.models.User


interface IchatWithFriendObjPresenter {

    abstract fun listFetched(userObjs: List<Sms>)

}