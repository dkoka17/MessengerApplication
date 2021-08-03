package ge.dkokaoemna.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ge.dkokaoemna.messenger.authentification.LogInActivity

class RegisterFragment(var logInActivity: LogInActivity) : Fragment() {

    lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.register_fragment, container, false)
        return myView
    }

}