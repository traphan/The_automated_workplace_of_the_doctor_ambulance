package traphan.ren95.the_automated_workplace_of_the_doctor_ambulance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper sqlHelper;
    int idItem;


    ArrayList<String> modifiList;
     ArrayList<String>contentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listMedical=(ListView)findViewById(R.id.medicalList);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        contentList=new ArrayList<>();
        final EditText editText=(EditText)findViewById(R.id.editText3);

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
                    for (int i = 0; i < contentList.size(); i++) {
                        if (contentList.get(i).toLowerCase().contains(s)) {
                            modifiList.add(contentList.get(i));
                        }
                    }
                    if (modifiList.size() != 0) {
                        ArrayAdapter<String> adapterDiagnosesnew = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, modifiList);
                        listMedical.setAdapter(adapterDiagnosesnew);
                    }
                } else {
                    ArrayAdapter<String> adapterOld=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,contentList);
                    listMedical.setAdapter(adapterOld);
                }
            }
        });
        sqlHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        sqlHelper.create_db();
        final String [] spinerAdapterArray={"Разделы","Код заболевания","Симптомы заболевания","Названия заболеваний"};
        ArrayAdapter<String> spinerAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,spinerAdapterArray);
        spinner.setAdapter(spinerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    contentList.clear();
                    final ArrayList<Integer> sectionId=new ArrayList<>();
                    contentList=SearchDataBases.getSectionList(sqlHelper,sectionId);
                    sqlHelper.close();
                    ArrayAdapter<String> adapterTitel=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,contentList);
                    listMedical.setAdapter(adapterTitel);
                    listMedical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                            Intent medicalIntent=new Intent(MainActivity.this,MedicalSearch.class);
                            medicalIntent.putExtra("id_section",sectionId.get(position));
                            medicalIntent.putExtra("name_section",contentList.get(position));
                            startActivity(medicalIntent);
                        }
                    });
                }else {
                    if(position==1){
                        contentList.clear();
                        final ArrayList<Integer> keyId=new ArrayList<>();
                        SearchDataBases.getAllKeyList(sqlHelper,contentList,keyId);
                        sqlHelper.close();
                        ArrayAdapter<String> adapterTitel=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,contentList);
                        listMedical.setAdapter(adapterTitel);
                        listMedical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                                Intent medicalIntent=new Intent(MainActivity.this,SimptomActivity.class);
                                medicalIntent.putExtra("id_zabolevanie",keyId.get(position));
                                medicalIntent.putExtra("diagnoze",contentList.get(position));
                                startActivity(medicalIntent);
                            }
                        });
                    }else {
                        if(position==2){
                            contentList.clear();
                            final ArrayList<Integer> simptomId=new ArrayList<>();
                            SearchDataBases.getAllSimptom(sqlHelper,contentList,simptomId);
                            sqlHelper.close();
                            ArrayAdapter<String> adapterTitel=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,contentList);
                            listMedical.setAdapter(adapterTitel);
                            listMedical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                                Intent medicalIntent=new Intent(MainActivity.this,MedicalSearch.class);
                                medicalIntent.putExtra("namesimptom",contentList.get(position));
                                startActivity(medicalIntent);
                                }
                            });
                        }else {
                            if(position==3){
                                contentList.clear();
                                final ArrayList<Integer> zabolevanieId=new ArrayList<>();
                               SearchDataBases.getAllZabolevanieList(sqlHelper,contentList,zabolevanieId);
                                sqlHelper.close();
                                ArrayAdapter<String> adapterTitel=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,contentList);
                                listMedical.setAdapter(adapterTitel);
                                listMedical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                                Intent medicalIntent=new Intent(MainActivity.this,SimptomActivity.class);
                                medicalIntent.putExtra("id_zabolevanie",zabolevanieId.get(position));
                                medicalIntent.putExtra("diagnoze",contentList.get(position));
                                startActivity(medicalIntent);
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

  @Override
       public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.main, menu);
       return true;
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Операции для выбранного пункта меню
        switch (item.getItemId())
        {
            case R.id.item1:
                Intent optionIntent=new Intent(MainActivity.this,UpgradeActivity.class);
                startActivity(optionIntent);
                return true;
            case R.id.item2:
                Intent settingsIntent=new Intent(MainActivity.this,UserInformationActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

