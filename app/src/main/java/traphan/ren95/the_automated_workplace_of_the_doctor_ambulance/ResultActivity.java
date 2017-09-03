package traphan.ren95.the_automated_workplace_of_the_doctor_ambulance;

/**
 * Created by ren95 on 05.03.2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    public static final String nameSearchActivity="Результат";
    DatabaseHelper resultSqlHelper;
    String from,where,text,title;
    Context mainContext;
    public SharedPreferences settings;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle(nameSearchActivity);
        mainContext=this;
        Button sendMessegessButton=(Button)findViewById(R.id.button);
        int id_simptom=getIntent().getIntExtra("id_smptom",0);
        String diagnoze=getIntent().getStringExtra("diagnoze");
        final String simptom=getIntent().getStringExtra("simptom");
        TextView keyTv=(TextView)findViewById(R.id.textViewKey);
        TextView diagnoseTv=(TextView)findViewById(R.id.textViewDiagnos);
        TextView simptomsTv=(TextView)findViewById(R.id.textViewSimptoms);
        TextView voluemTv=(TextView)findViewById(R.id.textViewVoluem);
        TextView takticsTv=(TextView)findViewById(R.id.textViewTaktik);
        TextView razdelTv=(TextView)findViewById(R.id.textViewRazdel);
        resultSqlHelper=new DatabaseHelper(getApplicationContext());
        final ArrayList<String>vouluemList=new ArrayList<>();
        final ArrayList<String>takticList=new ArrayList<>();
        final ArrayList<String>key=new ArrayList<>();
        final ArrayList<String>zabolevanie=new ArrayList<>();
        final ArrayList<String>section=new ArrayList<>();
        ArrayList<Integer> idZabolevanie=new ArrayList<>();
        ArrayList<Integer> idSection=new ArrayList<>();
        SearchDataBases.getResultLists(resultSqlHelper,id_simptom,vouluemList,takticList);
        SearchDataBases.getSimptomtoId(resultSqlHelper,id_simptom,idZabolevanie);
        SearchDataBases.getZabolevaieToId(resultSqlHelper,idZabolevanie.get(0),zabolevanie,key,idSection);
        SearchDataBases.getRazdelToId(resultSqlHelper,idSection.get(0),section);
        razdelTv.setText(section.get(0));
        diagnoseTv.setText(zabolevanie.get(0));
        keyTv.setText(key.get(0));
        simptomsTv.setText(simptom);
        takticsTv.setText(takticList.get(0));
        voluemTv.setText(vouluemList.get(0));
        sendMessegessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title="отчет по заболеванию";
                text=section.get(0)+"\n"+zabolevanie.get(0)+"\n"+key.get(0)+"\n"+simptom+"\n"+takticList.get(0)+"\n"+vouluemList.get(0);
                settings=getSharedPreferences(UserInformationActivity.APP_PREFERENCES,Context.MODE_PRIVATE);
                where=settings.getString(UserInformationActivity.APP_PREFERENCES_STRING_KEY_EMAIL,"");
                sender_mail_async sender_mail_async=new sender_mail_async();
                sender_mail_async.execute();
            }
        });
    }

    private void showAlertDialog(){
        alertDialog=new AlertDialog.Builder(mainContext);
        String title="Не указан email";
        String messeges="Перейти к пользовательским настройкам для указания email?";
        String stringButton1="Перейти";
        String stringButton2="Отмена";
        alertDialog.setTitle(title);
        alertDialog.setMessage(messeges);
        alertDialog.setPositiveButton(stringButton1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(ResultActivity.this,UserInformationActivity.class);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton(stringButton2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setCancelable(true);
    }

    private class sender_mail_async extends AsyncTask<Object, String, Boolean> {
        ProgressDialog WaitingDialog;


        @Override
        protected void onPreExecute() {
            WaitingDialog = ProgressDialog.show(ResultActivity.this, "Отправка email", "Отправка email...", true);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                WaitingDialog.dismiss();
                Toast.makeText(mainContext, "Email отправлен!!!", Toast.LENGTH_LONG).show();
                ((Activity) mainContext).finish();
            }else {
                WaitingDialog.dismiss();
                showAlertDialog();
                alertDialog.show();
            }
        }
        @Override
        protected Boolean doInBackground(Object... params) {
            boolean flag=false;
            try {
                from = "traphan7182@gmail.com";
                if(!where.equals("")) {
                    MailSenderClass sender = new MailSenderClass("vkrprovider@gmail.com", "slesher1995");
                    sender.sendMail(title, text, from, where, "");
                }else {
                   flag=true;
                }
            } catch (Exception e) {
                Toast.makeText(mainContext, "Не удалось отправить email!", Toast.LENGTH_SHORT).show();
            }
          return flag;
        }
    }
}