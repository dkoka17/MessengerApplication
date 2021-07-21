package ge.dkokaoemna.messenger.LogedInActivities.Chats

import ge.dkokaoemna.messenger.Firebase.models.Chat

interface IChatsObjView {
    fun showChatObjList(ChatObjs: List<Chat>)
}