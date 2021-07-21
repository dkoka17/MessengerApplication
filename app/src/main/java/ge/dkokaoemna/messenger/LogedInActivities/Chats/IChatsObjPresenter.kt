package ge.dkokaoemna.messenger.LogedInActivities.Chats

import ge.dkokaoemna.messenger.Firebase.models.Chat

interface IChatsObjPresenter {

    abstract fun listFetched(ChatObjs: List<Chat>)
}