package onlinedataappliaction.ln.infor.com.andriodapplication.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedValues {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static SharedValues instance;

    public SharedValues(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
    }

    public synchronized static SharedValues getInstance(Context context) {
        if (instance == null) {
            instance = new SharedValues(context);
        }
        return instance;
    }

    public void setColor(int color) {
        editor = sharedPreferences.edit();
        editor.putInt("colorCode", color);
        editor.commit();
    }

    public int getColor() {
        return sharedPreferences.getInt("colorCode", 0);
    }



    public void setPosition(int selectedPosition){
        editor=sharedPreferences.edit();
        editor.putInt("position",selectedPosition);
        editor.commit();
    }
    public int getPosition(){
        return sharedPreferences.getInt("position",0);
    }



    public void setButton(boolean b) {
        editor = sharedPreferences.edit();
        editor.putBoolean("fbc", b);
        editor.commit();
    }

    public boolean getButton() {
        return sharedPreferences.getBoolean("fbc", false);
    }


}
