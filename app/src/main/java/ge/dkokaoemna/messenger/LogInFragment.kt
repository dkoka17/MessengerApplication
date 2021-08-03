package ge.dkokaoemna.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ge.dkokaoemna.messenger.authentification.LogInActivity

class LogInFragment(var logInActivity: LogInActivity) : Fragment() {

    lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.sing_in_fragment, container, false)
        val signUpButton : Button = myView.findViewById(R.id.signUpButton)
        signUpButton.setOnClickListener {
            logInActivity.renderRegisterPage()
        }
        return myView
    }

}