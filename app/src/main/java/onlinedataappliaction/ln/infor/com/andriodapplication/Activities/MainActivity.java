package onlinedataappliaction.ln.infor.com.andriodapplication.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import onlinedataappliaction.ln.infor.com.andriodapplication.R;
import onlinedataappliaction.ln.infor.com.andriodapplication.adapters.OnRecyclerItemClickListener;
import onlinedataappliaction.ln.infor.com.andriodapplication.adapters.RecyclerViewAdapter;
import onlinedataappliaction.ln.infor.com.andriodapplication.datamodels.Model;
import onlinedataappliaction.ln.infor.com.andriodapplication.sharedpreferences.SharedValues;

import static onlinedataappliaction.ln.infor.com.andriodapplication.Activities.SettingsActivity.COLORCODES;
import static onlinedataappliaction.ln.infor.com.andriodapplication.Activities.SettingsActivity.FBC;


public class MainActivity extends AppCompatActivity /*implements OnRecyclerItemClickListener*/ implements View.OnClickListener {
    RecyclerView recyclerView;
    RelativeLayout mainLayout;
    public static final int COLORCODE = 307;
    FloatingActionButton floatingActionButton;
    ArrayList<Model> modelArrayList = new ArrayList<>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //RecyclerViewAdapter adapter=new RecyclerViewAdapter(modelArrayList);
        // adapter.setOnItemClickListener(this);
    }
    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.mainActivity_recyclerView_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(modelArrayList, this, new OnRecyclerItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        intent= getIntent();

        floatingActionButton=(FloatingActionButton) findViewById(R.id.mainActivity_floatingButton);
        floatingActionButton.setVisibility(View.GONE);
        mainLayout = (RelativeLayout) findViewById(R.id.mainActivity_relativeLayout);
        floatingActionButton.setOnClickListener(this);


        modelArrayList.add(new Model("List Item 1", R.drawable.roses, 0,"It shows tab view"));
        modelArrayList.add(new Model("List Item 2", R.drawable.lotus, 0,"It shows tab view"));
        modelArrayList.add(new Model("List Item 3", 0, 1,"It shows tab view"));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_name:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
               startActivityForResult(settingsIntent,COLORCODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COLORCODE:

                if(resultCode==RESULT_OK){
                    if(data!=null){
                        if(data.hasExtra(COLORCODES)){
                        if(SharedValues.getInstance(this).getColor()==0 ){
                            mainLayout.setBackgroundColor(getResources().getColor(R.color.yellow));


                        }else if(SharedValues.getInstance(this).getColor()==1){
                            mainLayout.setBackgroundColor(getResources().getColor(R.color.green));
                        }else{
                            mainLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        }
                        }else if(data.hasExtra(FBC)){
                            if(SharedValues.getInstance(this).getButton()){
                                floatingActionButton.setVisibility(View.VISIBLE);

                            }else{
                                floatingActionButton.setVisibility(View.GONE);

                            }
                        }
                    }
                }

                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);

       /* MenuItem locationItem = menu.add(0, R.id.menu_location, 0, "");
        locationItem.setIcon(R.drawable.ic_person_add_black_24dp);

        // Need to use MenuItemCompat methods to call any action item related methods
        MenuItemCompat.setShowAsAction(locationItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);*/
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mainActivity_floatingButton:
                sendEmail();
                break;
        }
    }

    private void sendEmail() {

        Intent  intent=new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_SUBJECT,"New Mail");
        intent.putExtra(Intent.EXTRA_TITLE,"Floating Action Button Clicked");
        intent.putExtra(Intent.EXTRA_TEXT,modelArrayList.get(0).getImage());
        intent.setType("*/");
        startActivity(Intent.createChooser(intent, "Send"));

    }
/* @Override
    public void OnItemClick(int position) {
        Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
        startActivity(intent);
    }*/



}
