package ge.dkokaoemna.messenger.authentification

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter1(var upFragment: Fragment, var downFragment: Fragment, activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0)
            return upFragment
        else
            return downFragment
    }
}