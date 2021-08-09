package ge.dkokaoemna.messenger.LogedInActivities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatsActivity
import ge.dkokaoemna.messenger.LogedInActivities.MyAccount.settings
import ge.dkokaoemna.messenger.R


class LogInView: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in_layout)

        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnavigationbar)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false

        supportFragmentManager.beginTransaction().replace(R.id.framecontainer, ChatsActivity()).commit()


        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var temp: Fragment? = null

            when (item.itemId) {
                R.id.mHome -> {
                    temp = ChatsActivity()
                }
                R.id.mSetting -> {
                    temp = settings()
                }
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,temp!!).commit();
            true
        }

    }
}