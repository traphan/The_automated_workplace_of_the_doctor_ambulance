package traphan.ren95.the_automated_workplace_of_the_doctor_ambulance;

/**
 * Created by ren95 on 05.03.2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class SimptomActivity extends AppCompatActivity {
    DatabaseHelper simptomSqlHelper;
    ArrayList<String> arrayCheckedSimptoms;
    ArrayList<String> modifiList1;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simptom);
        EditText ed=(EditText)findViewById(R.id.editText);
        final String titel=getIntent().getExtras().getString("diagnoze");
        final int id_zabolevanie=getIntent().getExtras().getInt("id_zabolevanie");
        setTitle(titel);
        final ListView simptomListView=(ListView)findViewById(R.id.listsimptom);
        ArrayList<String> simptomList=new ArrayList<>();
        simptomSqlHelper=new DatabaseHelper(getApplicationContext());
        final ArrayList<Integer>id_simptom=new ArrayList<>();
        simptomList=SearchDataBases.getSimptomList(simptomSqlHelper,id_zabolevanie,id_simptom);
        final ArrayAdapter<String>simptomAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,simptomList);
        simptomListView.setAdapter(simptomAdapter);
        final ArrayList<String> finalSimptomList = simptomList;
        simptomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textVie=(TextView)itemClicked;
                String sim=textVie.getText().toString();
                Intent simptomIntent=new Intent(SimptomActivity.this,ResultActivity.class);
                simptomIntent.putExtra("simptom",finalSimptomList.get(position));
                simptomIntent.putExtra("diagnoze",titel);
                simptomIntent.putExtra("id_smptom",id_simptom.get(position));
                startActivity(simptomIntent);
            }
        });

        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0) {
                    modifiList1 = new ArrayList<String>();
                    for (int i = 0; i < finalSimptomList.size(); i++) {
                        if (finalSimptomList.get(i).toLowerCase().contains(s)) {
                            modifiList1.add(finalSimptomList.get(i));
                        }
                    }
                    if (modifiList1.size() != 0) {
                        ArrayAdapter<String>adapterSimptomnew = new ArrayAdapter<String>(SimptomActivity.this, android.R.layout.simple_list_item_1, modifiList1);
                        simptomListView.setAdapter(adapterSimptomnew);
                    }
                }   else {
                    simptomListView.setAdapter(simptomAdapter);
                }
            }
        });
       /* ItemList=AddListAdapter(simptomList);
        simptomListView.setAdapter(new AdapterLixtView(this,ItemList));*/
      /*simptomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray sparseBooleanArray=simptomListView.getCheckedItemPositions();
                 arrayCheckedSimptoms=new ArrayList<String>();
                for(int i=0;i<sparseBooleanArray.size();i++){
                    if(sparseBooleanArray.get(i)==true){
                        arrayCheckedSimptoms.add(simptomListView.getItemAtPosition(i).toString());
                    }
                }
            }
        });*/

    }

  /*  public ArrayList<ItemListView> AddListAdapter(ArrayList<String> dagnoze ) {
        ArrayList<ItemListView> ItemListDiagnoze = new ArrayList<>();
        for (int i = 0; i < dagnoze.size(); i++) {
            boolean check=false;
            if(dagnoze.get(i).isEmpty()){
                check=true;
            }
            ItemListDiagnoze.add(new ItemListView(dagnoze.get(i),check));
        }
        return ItemListDiagnoze;
    }

    public void getChecedSimptoms(final ListView simptomListView){
        arrayCheckedSimptoms=new ArrayList<>();
        SparseBooleanArray sparseBooleanArray=simptomListView.getCheckedItemPositions();
        arrayCheckedSimptoms=new ArrayList<String>();
        for(int i=0;i<simptomListView.getChildCount();i++){
            if(sparseBooleanArray.get(i)==true){
                arrayCheckedSimptoms.add(simptomListView.getItemAtPosition(i).toString());
            }
        }
    }*/

}