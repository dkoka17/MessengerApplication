package ge.dkokaoemna.messenger.LogedInActivities.FindFriends

import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.LogedInActivities.Chats.IChatsObjPresenter
import java.util.*

class FindFriendInteractor(val presenter: IFindFriendsObjPresenter)  {
    //TODO აქაა წამოღების ლოგიკები ჩასასმემელი

    fun getUsers(){

        val other: List<User> = Collections.emptyList()

        presenter.listFetched(other)
    }

}