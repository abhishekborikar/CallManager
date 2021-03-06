package abhishek.redvelvet.com.callmanager.model.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import abhishek.redvelvet.com.callmanager.view_model.fragments.inner_fragment.ContactFragment;
import abhishek.redvelvet.com.callmanager.view_model.fragments.inner_fragment.RecentFragment;

/**
 * Created by abhishek on 5/8/18.
 */

public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:{
                fragment = new ContactFragment();
                break;
            }
            case 1:{
                fragment = new RecentFragment();
                break;
            }
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
