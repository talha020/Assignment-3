package onlinedataappliaction.ln.infor.com.andriodapplication.Activities;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import onlinedataappliaction.ln.infor.com.andriodapplication.R;
import onlinedataappliaction.ln.infor.com.andriodapplication.adapters.CustomFragmentAdapter;

public class DetailsActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    ViewPager tabPagers;
    TabLayout tabLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabPagers = (ViewPager) findViewById(R.id.detailsActivity_viewPager);
        tabLayout = (TabLayout) findViewById(R.id.detailsActivity_tabLayout);


        setUpPager(tabPagers);


        tabLayout.setupWithViewPager(tabPagers);
        setTabIcons();

        tabLayout.addOnTabSelectedListener(this);
        tabPagers.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Pdf Reader");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.camera, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

      TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Image Switcher");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.videocam, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("View Pager");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour= (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab,null);
       // tabFour.setCompoundDrawablesWithIntrinsicBounds(R.drawable.contacts,0,0,0);
        tabFour.setText("Url's");
        tabLayout.getTabAt(3).setCustomView(tabFour);

    }


    private void setUpPager(ViewPager viewPager) {
        CustomFragmentAdapter customFragmentAdapter = new CustomFragmentAdapter(getSupportFragmentManager());
        customFragmentAdapter.addFragment(new OneFragment(), "one");
        customFragmentAdapter.addFragment(new TwoFragment(), "two");
        customFragmentAdapter.addFragment(new ThreeFragment(), "three");
        customFragmentAdapter.addFragment(new EmailFragment(),"four");
        tabPagers.setAdapter(customFragmentAdapter);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabPagers.setCurrentItem(tab.getPosition());


    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabLayout.getTabAt(position).select();

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
