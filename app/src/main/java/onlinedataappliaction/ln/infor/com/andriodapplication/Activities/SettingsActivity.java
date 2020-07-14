package onlinedataappliaction.ln.infor.com.andriodapplication.Activities;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import onlinedataappliaction.ln.infor.com.andriodapplication.R;
import onlinedataappliaction.ln.infor.com.andriodapplication.sharedpreferences.SharedValues;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String COLORCODES = "colorcodes";
    public static final String FBC = "flaotingactionbutton";
    RadioButton yellowButton;
    RadioButton greenButton;
    RadioButton blueButton;
    RadioGroup radioGroup;
    int backgroundColor;
    public RadioButton radioButton;
    BottomSheetBehavior bottomSheetBehavior;
    Button bottomNavButton;
    TextView textBottom;
    CheckBox checkBox;
    int radioPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Intent intent = new Intent();
        View bottomSheet = findViewById(R.id.settingsActivity_bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        textBottom = (TextView) findViewById(R.id.text_bottomSheet);
        bottomNavButton = (Button) findViewById(R.id.button_bottomSheet);
        yellowButton = (RadioButton) findViewById(R.id.yellow);
        greenButton = (RadioButton) findViewById(R.id.green);
        blueButton = (RadioButton) findViewById(R.id.blue);
        checkBox = (CheckBox) findViewById(R.id.settingsActivity_checkedTextview);
        radioGroup = findViewById(R.id.radioGroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                int selectedId = radioGroup.getCheckedRadioButtonId();


                radioButton = (RadioButton) findViewById(selectedId);


                switch (checkedId) {
                    case R.id.yellow:
                        if (radioButton.getText().equals("yellow")) {
                            SharedValues.getInstance(SettingsActivity.this).setColor(0);
                            SharedValues.getInstance(SettingsActivity.this).setPosition(0);
                            radioButton.setChecked(true);
                        }
                        SharedValues.getInstance(SettingsActivity.this).getColor();
                        SharedValues.getInstance(SettingsActivity.this).getPosition();
                        intent.putExtra(COLORCODES, "changeChart");

                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    case R.id.green:
                        if (radioButton.getText().equals("green")) {
                            SharedValues.getInstance(SettingsActivity.this).setColor(1);

                        }
                        SharedValues.getInstance(SettingsActivity.this).getColor();
                        intent.putExtra(COLORCODES, "changeChart");
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    case R.id.blue:
                        if (radioButton.getText().equals("blue")) {
                            SharedValues.getInstance(SettingsActivity.this).setColor(2);

                        }
                        SharedValues.getInstance(SettingsActivity.this).getColor();
                        intent.putExtra(COLORCODES, "changeChart");
                        setResult(RESULT_OK, intent);
                        finish();
                        break;

                }


            }
        });

        bottomNavButton.setOnClickListener(this);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    SharedValues.getInstance(SettingsActivity.this).setButton(true);
                    intent.putExtra(FBC, "showButton");

                } else {
                    SharedValues.getInstance(SettingsActivity.this).setButton(false);
                    intent.putExtra(FBC, "hideButton");
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_bottomSheet:
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bottomNavButton.setText("collapse sheet");
                    textBottom.setText("The above radio buttons are used to set the background color for the recycler view.");
                } else if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bottomNavButton.setText("Expand sheet");
                    textBottom.setText("Sample Bottom Sheet");
                }
                break;
        }
    }
}
