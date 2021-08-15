package ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend

import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms

interface IchatWithFriendObjView {
    fun showChatObjList(ChatObjs: List<Sms>)
}