package traphan.ren95.the_automated_workplace_of_the_doctor_ambulance;

import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Created by ren95 on 04.02.2017.
 */

public  class SearchDataBases {
//возращает заначения названий разделов
    public static ArrayList<String> getSectionList(DatabaseHelper mainHelper,ArrayList<Integer> sectionId){
        ArrayList<String> sectionName=new ArrayList<>();
        try {
            mainHelper.open();
            Cursor cursor=mainHelper.database.query(DatabaseHelper.SECTION_TABLE,null,null,null,null,null,null);
            if(cursor.moveToFirst()){
                int sectionIndexName=cursor.getColumnIndex(DatabaseHelper.SECTION_COULMN_NAME);
                int sectionIndexId=cursor.getColumnIndex(DatabaseHelper.ID_NAME_OF_ALL);
                do{
                    sectionName.add(cursor.getString(sectionIndexName));
                    sectionId.add(cursor.getInt(sectionIndexId));
                }while (cursor.moveToNext());
            }else {
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mainHelper.close();
        return sectionName;
    }
//возращает списк заболеваний по id секций
    public static ArrayList<String> getMedicalSerchList(DatabaseHelper medicalSearchHelper,int section,ArrayList<Integer>zabolevanieId){
        ArrayList<String> mediacalSearchList=new ArrayList<>();
        try {
                medicalSearchHelper.open();
            Cursor cursor=medicalSearchHelper.database.query(DatabaseHelper.ZABOLEVANIE_TABLE,new String[]{DatabaseHelper.ID_NAME_OF_ALL,DatabaseHelper.ZABOLEVANIE_COULMN_NAME},
                    DatabaseHelper.ZABOLEVANIE_COULMN_ID_SECTION+" = ?",new String[]{String.valueOf(section)},null,null,null);
            if(cursor.moveToFirst()){
                int indexZabolevanieName=cursor.getColumnIndex(DatabaseHelper.ZABOLEVANIE_COULMN_NAME);
                int indexZabolevanieId=cursor.getColumnIndex(DatabaseHelper.ID_NAME_OF_ALL);
                do{
                    mediacalSearchList.add(cursor.getString(indexZabolevanieName));
                    zabolevanieId.add(cursor.getInt(indexZabolevanieId));
                }while (cursor.moveToNext());
            }else {
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
return mediacalSearchList;
    }
    //возращает список симптомов по id заболевания
public static ArrayList<String> getSimptomList(DatabaseHelper simptomListHelper,int zabolevanie_id,ArrayList<Integer>simptomId){
    ArrayList<String>simptomList=new ArrayList<>();
    try {
        simptomListHelper.open();
        Cursor cursor=simptomListHelper.database.query(DatabaseHelper.SIMPTOM_TABLE,new String[]{DatabaseHelper.ID_NAME_OF_ALL,DatabaseHelper.SIMPTOM_COULMN_SIMPTOM},
                DatabaseHelper.SIMPTOM_COULMN_ID_ZABOLEVANIE+" =?",new String[]{String.valueOf(zabolevanie_id)},null,null,null);
        if(cursor.moveToFirst()){
            int indexSimptomId=cursor.getColumnIndex(DatabaseHelper.ID_NAME_OF_ALL);
            int indexSimptomName=cursor.getColumnIndex(DatabaseHelper.SIMPTOM_COULMN_SIMPTOM);
            do {
                simptomList.add(cursor.getString(indexSimptomName));
                simptomId.add(cursor.getInt(indexSimptomId));
            }while (cursor.moveToNext());
        }else {
            cursor.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return simptomList;
}

//возращает занчение всех кодов заболеваний
public static void getAllKeyList(DatabaseHelper keyHelper,ArrayList<String>keyList,ArrayList<Integer>keyIdList){
    try {
        keyHelper.open();
        Cursor cursor=keyHelper.database.query(DatabaseHelper.ZABOLEVANIE_TABLE,new String[]{DatabaseHelper.ID_NAME_OF_ALL,DatabaseHelper.ZABOLEVANIE_COULMN_KEY},
                null,null,null,null,null);
        if(cursor.moveToFirst()){
            int indexKey=cursor.getColumnIndex(DatabaseHelper.ZABOLEVANIE_COULMN_KEY);
            int indexKeyId=cursor.getColumnIndex(DatabaseHelper.ID_NAME_OF_ALL);
            do{
                keyList.add(cursor.getString(indexKey));
                keyIdList.add(cursor.getInt(indexKeyId));
            }while (cursor.moveToNext());
        }else {
            cursor.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
// возращает значения полного спика симптомов
public static void getAllSimptom(DatabaseHelper simptomAllHelper,ArrayList<String>simptomAllList,ArrayList<Integer>simptomAllId){
    LinkedHashSet<String> linkedHashSetSimptomName=new LinkedHashSet<>();
    try {
        simptomAllHelper.open();
        Cursor cursor=simptomAllHelper.database.query(DatabaseHelper.SIMPTOM_TABLE,new String[]{DatabaseHelper.ID_NAME_OF_ALL,DatabaseHelper.SIMPTOM_COULMN_SIMPTOM},
                null,null,null,null,null);
        if(cursor.moveToFirst()){
            int indexSimptom=cursor.getColumnIndex(DatabaseHelper.SIMPTOM_COULMN_SIMPTOM);
            int indexIdSimptom=cursor.getColumnIndex(DatabaseHelper.ID_NAME_OF_ALL);
            do {
                simptomAllId.add(cursor.getInt(indexIdSimptom));
                linkedHashSetSimptomName.add(cursor.getString(indexSimptom));
            }while (cursor.moveToNext());
        }else {
            cursor.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    String []bufArray = linkedHashSetSimptomName.toArray(new String[linkedHashSetSimptomName.size()]);
    Collections.addAll(simptomAllList,bufArray);
}
//возращает значения полного списка заболеваний
public static void getAllZabolevanieList(DatabaseHelper zabolevanieAllHelper,ArrayList<String>zabolevanieList,ArrayList<Integer>zabolevanieIdList){
    try {
        zabolevanieAllHelper.open();
        Cursor cursor=zabolevanieAllHelper.database.query(DatabaseHelper.ZABOLEVANIE_TABLE,new String[]{DatabaseHelper.ID_NAME_OF_ALL,DatabaseHelper.ZABOLEVANIE_COULMN_NAME},
                null,null,null,null,null);
        if(cursor.moveToFirst()){
            int indexAllIdZabolevanie=cursor.getColumnIndex(DatabaseHelper.ID_NAME_OF_ALL);
            int indexAllZabolevanieName=cursor.getColumnIndex(DatabaseHelper.ZABOLEVANIE_COULMN_NAME);
            do {
                zabolevanieIdList.add(cursor.getInt(indexAllIdZabolevanie));
                zabolevanieList.add(cursor.getString(indexAllZabolevanieName));
            }while (cursor.moveToNext());
        }else {
            cursor.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
//возращает id симптома для поиска значений по симптому
public static int simptomIdSearch(DatabaseHelper simptomIdSearchHelper,int idZabolevanie,String simptomName){
    int result=0;
    try {
        simptomIdSearchHelper.open();
        Cursor cursor=simptomIdSearchHelper.database.query(DatabaseHelper.SIMPTOM_TABLE,new String[]{DatabaseHelper.ID_NAME_OF_ALL},
                DatabaseHelper.SIMPTOM_COULMN_ID_ZABOLEVANIE+" = ? AND "+DatabaseHelper.SIMPTOM_COULMN_SIMPTOM+" = ?",
                new String[]{String.valueOf(idZabolevanie),simptomName},null,null,null);
        if(cursor.moveToFirst()){
            int idSimptomIndex=cursor.getColumnIndex(DatabaseHelper.ID_NAME_OF_ALL);
            do
            {
                result=cursor.getInt(idSimptomIndex);
            }while (cursor.moveToNext());
        }else {
            cursor.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
}
private static ArrayList<Integer>getIdZabolevanieToSimptom(DatabaseHelper databaseHelper,String nameSimptom){
    ArrayList<Integer>idZabolevaniaToSimptom=new ArrayList<>();
    try {
        databaseHelper.open();
        Cursor cursor=databaseHelper.database.query(DatabaseHelper.SIMPTOM_TABLE,new String[]{DatabaseHelper.SIMPTOM_COULMN_ID_ZABOLEVANIE},
                DatabaseHelper.SIMPTOM_COULMN_SIMPTOM+" = ? ",new String[]{nameSimptom},null,null,null);
        if(cursor.moveToFirst()){
            int idZabolevaniaIncex=cursor.getColumnIndex(DatabaseHelper.SIMPTOM_COULMN_ID_ZABOLEVANIE);
            do{
                idZabolevaniaToSimptom.add(cursor.getInt(idZabolevaniaIncex));
            }while (cursor.moveToNext());
        }else {
            cursor.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return idZabolevaniaToSimptom;
}
//возращает значения заболеваний связаных с выбранымсимптомом
public static void getZabolevaniaToSimptomList(DatabaseHelper databaseHelperSql,ArrayList<String>zabolevanieList,ArrayList<Integer>idZabolevanieList,String nameSimptom){
   ArrayList<Integer>idZabolevanieToSimptom=SearchDataBases.getIdZabolevanieToSimptom(databaseHelperSql,nameSimptom);
    try {
        databaseHelperSql.open();
        for (int i = 0; i < idZabolevanieToSimptom.size(); i++) {
            Cursor cursor = databaseHelperSql.database.query(DatabaseHelper.ZABOLEVANIE_TABLE, new String[]{DatabaseHelper.ZABOLEVANIE_COULMN_NAME, DatabaseHelper.ID_NAME_OF_ALL},
                    DatabaseHelper.ID_NAME_OF_ALL+ " = ? ",new String[]{String.valueOf(idZabolevanieToSimptom.get(i))},
                    null,null,null);
            if(cursor.moveToFirst()){
                int idZabolevanieIndex=cursor.getColumnIndex(DatabaseHelper.ID_NAME_OF_ALL);
                int nameZabolevanieIndex=cursor.getColumnIndex(DatabaseHelper.ZABOLEVANIE_COULMN_NAME);
                do {
                    zabolevanieList.add(cursor.getString(nameZabolevanieIndex));
                    idZabolevanieList.add(cursor.getInt(idZabolevanieIndex));
                }while (cursor.moveToNext());
            }else {
                cursor.close();
            }
        }
    }catch (SQLException e) {
        e.printStackTrace();
    }
}

    //поиск симптома по id
    public static void getSimptomtoId(DatabaseHelper resultHelper, int idSimptom,ArrayList<Integer> idZabolevanie){
        try {
            resultHelper.open();
            Cursor cursor=resultHelper.database.query(DatabaseHelper.SIMPTOM_TABLE,new String[]{DatabaseHelper.SIMPTOM_COULMN_ID_ZABOLEVANIE},
                    DatabaseHelper.ID_NAME_OF_ALL+" = ?",new String[]{String.valueOf(idSimptom)},
                    null,null,null);
            if(cursor.moveToFirst()){;
                int indexIdZabolevanie=cursor.getColumnIndex(DatabaseHelper.SIMPTOM_COULMN_ID_ZABOLEVANIE);
                  idZabolevanie.add(cursor.getInt(indexIdZabolevanie));

            }else {
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //поиск заболевания по ид
    public static void getZabolevaieToId(DatabaseHelper resultHelper,int zabolevanieId,ArrayList<String> zabolevanie,ArrayList<String> keyname, ArrayList<Integer> idRazdel){
        try {
            resultHelper.open();
            Cursor cursor=resultHelper.database.query(DatabaseHelper.ZABOLEVANIE_TABLE,new String[]{DatabaseHelper.ZABOLEVANIE_COULMN_ID_SECTION,
            DatabaseHelper.ZABOLEVANIE_COULMN_NAME,DatabaseHelper.ZABOLEVANIE_COULMN_KEY},DatabaseHelper.ID_NAME_OF_ALL+" = ?",
                    new String[]{String.valueOf(zabolevanieId)},null,null,null);
            if(cursor.moveToFirst()){
                int indexNameZabolevanie=cursor.getColumnIndex(DatabaseHelper.ZABOLEVANIE_COULMN_NAME);
                int indeKeyZabolevanie=cursor.getColumnIndex(DatabaseHelper.ZABOLEVANIE_COULMN_KEY);
                int indexIdRazdel=cursor.getColumnIndex(DatabaseHelper.ZABOLEVANIE_COULMN_ID_SECTION);
                do {
                    zabolevanie.add(cursor.getString(indexNameZabolevanie));
                    keyname.add(cursor.getString(indeKeyZabolevanie));
                    idRazdel.add(cursor.getInt(indexIdRazdel));
                }while (cursor.moveToNext());
            }else {
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void getRazdelToId(DatabaseHelper resultHelper,int idSection,ArrayList<String> nameSection){
        try {
            resultHelper.open();
            Cursor cursor=resultHelper.database.query(DatabaseHelper.SECTION_TABLE,new String[]{DatabaseHelper.SECTION_COULMN_NAME},
                    DatabaseHelper.ID_NAME_OF_ALL+" = ?",new String[]{String.valueOf(idSection)},null,null,null);
            if(cursor.moveToFirst()){
                int indexSectionName=cursor.getColumnIndex(DatabaseHelper.SECTION_COULMN_NAME);
                do {
                    nameSection.add(cursor.getString(indexSectionName));
                }while (cursor.moveToNext());
            }else {
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //возращет конечный результат
    public static void getResultLists(DatabaseHelper resultHelper,int id_simptom,ArrayList<String> vouluemList,ArrayList<String>takticList) {
        try {
            resultHelper.open();
            Cursor cursor=resultHelper.database.query(DatabaseHelper.RESHENIE_TABLE,new String[]{DatabaseHelper.RESHENIE_COULMN_TAKTIC,DatabaseHelper.RESHENIE_COULMN_VOULUEM},
                    DatabaseHelper.RESHENIE_COULMN_ID_SIMPTOM+" =?",new String[]{String.valueOf(id_simptom)},null,null,null);
            if(cursor.moveToFirst()){
                int indexVouluem=cursor.getColumnIndex(DatabaseHelper.RESHENIE_COULMN_VOULUEM);
                int indexTaktics=cursor.getColumnIndex(DatabaseHelper.RESHENIE_COULMN_TAKTIC);
                do {
                    vouluemList.add(cursor.getString(indexVouluem));
                    takticList.add(cursor.getString(indexTaktics));
                }while (cursor.moveToNext());
            }
            else {
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


