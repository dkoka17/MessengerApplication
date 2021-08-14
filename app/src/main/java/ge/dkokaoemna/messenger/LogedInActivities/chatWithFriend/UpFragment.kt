package ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ge.dkokaoemna.messenger.R

class UpFragment(var act: chatWithFriendActivity, var nickname : String, var job : String) : Fragment() {
    lateinit var myView: View
    lateinit var nicknameView: TextView
    lateinit var jobView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.scroll_up_friend_fragment, container, false)
        nicknameView = myView.findViewById(R.id.friend_nickname_2)
        jobView = myView.findViewById(R.id.friend_job_2)
        nicknameView.text = nickname
        jobView.text = job
        val backButton : ImageView = myView.findViewById(R.id.back_button_2)
        backButton.setOnClickListener {
            act.goBack()
        }
        return myView
    }
}