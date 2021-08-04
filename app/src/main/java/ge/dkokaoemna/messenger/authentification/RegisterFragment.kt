package ge.dkokaoemna.messenger.authentification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import ge.dkokaoemna.messenger.R

class RegisterFragment(var logInActivity: LogInActivity) : Fragment() {

    lateinit var myView: View
    lateinit var signUpButtonRegister: Button
    lateinit var userNameRegister: EditText
    lateinit var passwordRegister: EditText
    lateinit var job: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.register_fragment, container, false)
        signUpButtonRegister = myView.findViewById(R.id.signUpRegisterButton)
        userNameRegister = myView.findViewById(R.id.userNameRegister)
        passwordRegister = myView.findViewById(R.id.passwordRegister)
        job = myView.findViewById(R.id.job)
        signUpButtonRegister.setOnClickListener {
            val userNameText = userNameRegister.editableText.toString()
            val passwordText = passwordRegister.editableText.toString()
            val jobText = job.editableText.toString()
            userNameRegister.setText("")
            passwordRegister.setText("")
            job.setText("")
            logInActivity.createUser(userNameText, passwordText, jobText)
        }
        return myView
    }

}