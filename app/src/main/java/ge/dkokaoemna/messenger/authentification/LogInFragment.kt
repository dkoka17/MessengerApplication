package ge.dkokaoemna.messenger.authentification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import ge.dkokaoemna.messenger.R

class LogInFragment(var logInActivity: LogInActivity) : Fragment() {

    lateinit var myView: View
    lateinit var signUpButton: Button
    lateinit var signInButton: Button
    lateinit var userName: EditText
    lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.sing_in_fragment, container, false)
        signInButton= myView.findViewById(R.id.signInButton)
        signUpButton = myView.findViewById(R.id.signUpButton)
        userName = myView.findViewById(R.id.userName)
        password = myView.findViewById(R.id.password)
        signInButton.setOnClickListener {
            val userNameText = userName.editableText.toString() + "@gmail.com"
            val passwordText = password.editableText.toString()
            userName.setText("")
            password.setText("")
            logInActivity.logIN(userNameText, passwordText)
        }
        signUpButton.setOnClickListener {
            logInActivity.renderRegisterPage()
        }
        return myView
    }

}