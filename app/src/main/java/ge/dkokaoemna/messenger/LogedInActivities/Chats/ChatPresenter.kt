package ge.dkokaoemna.messenger.LogedInActivities.Chats

import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.User

class ChatPresenter(var view: IChatsObjView?): IChatsObjPresenter  {

    private val interactor = ChatInteractor(this)

    fun getACtiveChats() {
        interactor.getACtiveChats()
    }

    fun searchInChats(searchText: String, chats: List<Chat>){
        interactor.searchInChats(searchText, chats)
    }

    override fun listFetched(ChatObjs: List<Chat>) {
        view?.showChatObjList(ChatObjs)
    }
}