package ge.dkokaoemna.messenger.LogedInActivities.Chats

import ge.dkokaoemna.messenger.Firebase.models.Chat

class ChatPresnter(var view: IChatsObjView?): IChatsObjPresenter  {
    override fun listFetched(ChatObjs: List<Chat>) {
        view?.showChatObjList(ChatObjs)
    }
}