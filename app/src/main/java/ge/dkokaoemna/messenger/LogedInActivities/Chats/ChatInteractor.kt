package ge.dkokaoemna.messenger.LogedInActivities.Chats

import ge.dkokaoemna.messenger.Firebase.models.Chat
import java.util.*

class ChatInteractor(val presenter: IChatsObjPresenter)  {



    //TODO აქაა წამოღების ლოგიკები ჩასასმემელი

    fun getACtiveChats(){



        val other: List<Chat> = Collections.emptyList()

        presenter.listFetched(other)
    }

}
