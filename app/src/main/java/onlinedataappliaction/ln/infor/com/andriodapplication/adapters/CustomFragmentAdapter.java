package onlinedataappliaction.ln.infor.com.andriodapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.ArrayList;

import onlinedataappliaction.ln.infor.com.andriodapplication.Activities.OneFragment;

public class CustomFragmentAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();

    public CustomFragmentAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentArrayList.add(fragment);
        titles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
