package ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend

import android.net.Uri
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.LogedInActivities.FindFriends.FindFriendInteractor
import ge.dkokaoemna.messenger.LogedInActivities.FindFriends.IFindFriendsObjPresenter
import ge.dkokaoemna.messenger.LogedInActivities.FindFriends.IFindFriendsObjView


class cahtWithFriendPresenter(var view: IchatWithFriendObjView?): IchatWithFriendObjPresenter {

    private val interactor = chatWithFriendInteractor(this)

    fun getTextMessages(){
        interactor.getTextMessages()
    }

    fun sendMessage(txt: String, chat: Chat, position: Int){
        interactor.sendMessage(txt, chat, position)
    }

    fun sendVoice(uri: String, chat: Chat, position: Int){
        interactor.sendVoice(uri, chat, position)
    }

    override fun listFetched(smsObjs: List<Sms>) {

        view?.showChatObjList(smsObjs)
    }

}