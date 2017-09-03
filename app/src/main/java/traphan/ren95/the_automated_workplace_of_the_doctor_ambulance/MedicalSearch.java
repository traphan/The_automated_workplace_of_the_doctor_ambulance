package traphan.ren95.the_automated_workplace_of_the_doctor_ambulance;

/**
 * Created by ren95 on 05.03.2017.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MedicalSearch extends AppCompatActivity {
    DatabaseHelper sqlMedical;
    AlertDialog.Builder medicalDial;
    Context mContext;
    ArrayList<String> modifiList;
    ArrayList<String> medicalSearchContentList;
    ArrayAdapter<String>adapterDiagnoses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_search);
        final EditText editText = (EditText) findViewById(R.id.editText2);
        String titel = getIntent().getExtras().getString("name_section");
        int count = getIntent().getExtras().getInt("id_section");
        final String simptomName = getIntent().getExtras().getString("namesimptom");
        setTitle(titel);
        sqlMedical = new DatabaseHelper(getApplicationContext());
        final ListView diagnosesList = (ListView) findViewById(R.id.diagnoseList);
        medicalSearchContentList=new ArrayList<>();
        if (simptomName == null) {
            medicalSearchContentList.clear();
            final ArrayList<Integer> zabolevanieId = new ArrayList<>();
            medicalSearchContentList = SearchDataBases.getMedicalSerchList(sqlMedical, count, zabolevanieId);
            final ArrayAdapter<String> adapterDiagnoses = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medicalSearchContentList);
            diagnosesList.setAdapter(adapterDiagnoses);
            diagnosesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                        long id) {
                    TextView textView = (TextView) itemClicked;
                    String dia = textView.getText().toString();
                    Intent MediacalIntent = new Intent(MedicalSearch.this, SimptomActivity.class);
                    MediacalIntent.putExtra("diagnoze", dia);
                    MediacalIntent.putExtra("id_zabolevanie", zabolevanieId.get(position));
                    startActivity(MediacalIntent);
                }
                  /*  mContext = MedicalSearch.this;
                    String DialogTitel = "Симптомы";
                    String buttonOK = "Просмотреть подробнее";
                    String buttonCanceld = "Отмена";
                    medicalDial = new AlertDialog.Builder(mContext);
                    medicalDial.setTitle(DialogTitel);
                    final int iD = position + 1;
                    Cursor dialCursor = SqlMedical.database.query(finalTableName, new String[]{DatabaseHelper.COULM_SIMPTOMS}, DatabaseHelper.COLUMN_ID +
                            " = ?", new String[]{Integer.toString(iD)}, null, null, null);
                    String simptomsList = new String();
                    if (dialCursor.moveToFirst()) {
                        int diagnosesIndex = dialCursor.getColumnIndex(DatabaseHelper.COULM_SIMPTOMS);
                        do {
                            simptomsList = dialCursor.getString(diagnosesIndex);
                        } while (dialCursor.moveToNext());
                    } else {
                        Log.d("mLog", "0 rows");
                        dialCursor.close();
                    }
                    medicalDial.setMessage(simptomsList);
                    medicalDial.setPositiveButton(buttonOK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent medicalIntent = new Intent(MedicalSearch.this, ResultActivity.class);
                            medicalIntent.putExtra("_id",iD);
                            medicalIntent.putExtra("table", finalTableName);
                            startActivity(medicalIntent);
                        }
                    });
                    medicalDial.setNegativeButton(buttonCanceld, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    medicalDial.show();

                }*/
            });
        }else {
            final ArrayList<Integer> zabolevanieId = new ArrayList<>();
            medicalSearchContentList.clear();
            SearchDataBases.getZabolevaniaToSimptomList(sqlMedical, medicalSearchContentList, zabolevanieId, simptomName);
            final ArrayAdapter<String> adapterDiagnoses = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medicalSearchContentList);
            diagnosesList.setAdapter(adapterDiagnoses);
            diagnosesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int idSimptom = SearchDataBases.simptomIdSearch(sqlMedical, zabolevanieId.get(position), simptomName);
                    Intent MediacalIntent = new Intent(MedicalSearch.this, ResultActivity.class);
                    MediacalIntent.putExtra("id_smptom", idSimptom);
                    MediacalIntent.putExtra("simptom",simptomName);
                    startActivity(MediacalIntent);
                }
            });
        }
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0) {
                        modifiList = new ArrayList<String>();
                        for (int i = 0; i < medicalSearchContentList.size(); i++) {
                            if (medicalSearchContentList.get(i).toLowerCase().contains(s)) {
                                modifiList.add(medicalSearchContentList.get(i));
                            }
                        }
                        if (modifiList.size() != 0) {
                            ArrayAdapter<String> adapterDiagnosesnew = new ArrayAdapter<String>(MedicalSearch.this, android.R.layout.simple_list_item_1, modifiList);
                            diagnosesList.setAdapter(adapterDiagnosesnew);
                        }
                    } else {
                        diagnosesList.setAdapter(adapterDiagnoses);
                    }
                }
            });


    }
    }


