package traphan.ren95.the_automated_workplace_of_the_doctor_ambulance;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class UserInformationActivity extends AppCompatActivity {
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_STRING_NAME = "stringKeyName";
    public static final String APP_PREFERENCES_STRING_KEY_SUNAME = "stringKeySuname";
    public static final String APP_PREFERENCES_STRING_KEY_POSITION = "stringKeyPOSITION";
    public static final String APP_PREFERENCES_STRING_KEY_EMAIL = "stringKeyEmail";
    EditText editTextEmail;
    EditText editTextName;
    EditText editTextSuname;
    EditText editTextposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        mSettings = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSuname = (EditText) findViewById(R.id.editTextSuname);
        editTextposition = (EditText) findViewById(R.id.editTextPosition);
        if(mSettings.contains(APP_PREFERENCES_STRING_KEY_SUNAME)||mSettings.contains(APP_PREFERENCES_STRING_NAME)||
                mSettings.contains(APP_PREFERENCES_STRING_KEY_EMAIL)||mSettings.contains(APP_PREFERENCES_STRING_KEY_POSITION)){
            editTextposition.setText(mSettings.getString(APP_PREFERENCES_STRING_KEY_POSITION,""));
            editTextSuname.setText(mSettings.getString(APP_PREFERENCES_STRING_KEY_SUNAME,""));
            editTextEmail.setText(mSettings.getString(APP_PREFERENCES_STRING_KEY_EMAIL,""));
            editTextName.setText(mSettings.getString(APP_PREFERENCES_STRING_NAME,""));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Запоминаем данные
        savePreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePreferences();
    }

    private void savePreferences() {
        if (!editTextEmail.getText().toString().isEmpty() || !editTextName.getText().toString().isEmpty() || !editTextSuname.getText().toString().isEmpty()
                || !editTextposition.getText().toString().isEmpty()) {
            mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.clear();
            editor.putString(APP_PREFERENCES_STRING_KEY_EMAIL,editTextEmail.getText().toString());
            editor.putString(APP_PREFERENCES_STRING_KEY_POSITION,editTextposition.getText().toString());
            editor.putString(APP_PREFERENCES_STRING_KEY_SUNAME,editTextSuname.getText().toString());
            editor.putString(APP_PREFERENCES_STRING_NAME,editTextName.getText().toString());
            editor.apply();
        }
    }
}
