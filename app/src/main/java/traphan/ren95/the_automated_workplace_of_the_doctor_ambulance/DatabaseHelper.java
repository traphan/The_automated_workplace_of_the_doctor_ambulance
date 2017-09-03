package traphan.ren95.the_automated_workplace_of_the_doctor_ambulance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by ren95 on 21.01.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DB_PATH = "/data/data/traphan.ren95.the_automated_workplace_of_the_doctor_ambulance/databases/";
    public static String DB_NAME = "bdVkr3.db";
    private static final int SCHEMA = 1; // версия базы данных
    public static final String ID_NAME_OF_ALL="_id";
    //таблица разделов
    public static final String SECTION_TABLE="section";
    public static final String SECTION_COULMN_NAME="name";
    //таблица заболеваний
    public static final String ZABOLEVANIE_TABLE="zabolevanie";
    public static final String ZABOLEVANIE_COULMN_ID_SECTION="id_section";
    public static final String ZABOLEVANIE_COULMN_KEY="key";
    public static final String ZABOLEVANIE_COULMN_NAME="name";
    //табдица симптомов
    public static final String SIMPTOM_TABLE="simptom";
    public static final String SIMPTOM_COULMN_ID_ZABOLEVANIE="id_zabolevanie";
    public static final String SIMPTOM_COULMN_SIMPTOM="simptom";
    //таблица решений объемв и тактик
    public static final String RESHENIE_TABLE="reshenie";
    public static final String RESHENIE_COULMN_ID_SIMPTOM="id_simptom";
    public static final String RESHENIE_COULMN_VOULUEM="voluem";
    public static final String RESHENIE_COULMN_TAKTIC="taktic";

    public SQLiteDatabase database;
    private Context myContext;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {

    }
    public void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH + DB_NAME);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH + DB_NAME;
                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);
                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
        }
    }
public void upgradeBd(File newData){
    FileInputStream myInput = null;
    OutputStream myOutput = null;
    try {
        deleteDatabase();
        File file = new File(DB_PATH + DB_NAME);
        if (!file.exists()) {
            this.getReadableDatabase();
            //получаем локальную бд как поток
            myInput=new FileInputStream(newData);
            // Путь к новой бд
            String outFileName = DB_PATH + DB_NAME;
            // Открываем пустую бд
            myOutput = new FileOutputStream(outFileName);
            // побайтово копируем данные
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) != -1) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
    }
    catch(IOException ex){
        ex.printStackTrace();
    }
}
    public void open() throws SQLException {
            String path = DB_PATH + DB_NAME;
            database = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }
    public void deleteDatabase(){
        File file = new File(DB_PATH + DB_NAME);
        if(file.exists()){
            file.delete();
        }
    }
public boolean isOpenDb(){

    if(database.isOpen()){
        return true;
    }else {
        return false;
    }
}
}
/* static final String TABLE = "dentistry";
    static final String TABLE2="otolaryngology";
    static final String Table1="Anesthesiology_and_Intensive_Care";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_KEY = "key";
    public static final String COULM_DIAGNOSES="diagnoses";
    public static final String COULM_SIMPTOMS="simptoms";
    public static final String COULM_VOLUEM="voluem";
    public static final String COULM_ALGORITMS="algoritms";*/