package ge.dkokaoemna.messenger.LogedInActivities.Chats

import ge.dkokaoemna.messenger.Firebase.models.Chat

class ChatPresenter(var view: IChatsObjView?): IChatsObjPresenter  {

    private val interactor = ChatInteractor(this)

    fun getACtiveChats() {
        interactor.getACtiveChats()
    }

    override fun listFetched(ChatObjs: List<Chat>) {
        view?.showChatObjList(ChatObjs)
    }
}