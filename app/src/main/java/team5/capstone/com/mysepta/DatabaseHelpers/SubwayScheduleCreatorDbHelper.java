package team5.capstone.com.mysepta.DatabaseHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Andrew on 10/12/2015.
 */
public class SubwayScheduleCreatorDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "SubwayScheduleReaderDbHelper";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SubwaySchedule.db";

    public static final String ARRIVAL_ID = "ArrivalID";
    public static final String COMMA_SEP = ", ";
    public static final String COLUMN_DATA_TYPE = " varchar(15)";
    public static final String TABLE_END = ");";

    // Table and table entries for BSL Weekday Northbound
    private static String SQL_CREATE_ENTRIES_BSL_WEEKDAY_NORTHBOUND;
    public static final String TABLE_BSL_WEEKDAY_NORTHBOUND = "BSL_WEEKDAY_NORTHBOUND";

    // Table and table entries for BSL Weekday Northbound
    private static String SQL_CREATE_ENTRIES_BSL_WEEKDAY_SOUTHBOUND;
    public static final String TABLE_BSL_WEEKDAY_SOUTHBOUND = "BSL_WEEKDAY_SOUTHBOUND";

    // Table and table entries for BSL Saturday Northbound
    private static String SQL_CREATE_ENTRIES_BSL_SAT_NORTHBOUND;
    public static final String TABLE_BSL_SAT_NORTHBOUND = "BSL_SAT_NORTHBOUND";

    // Table and table entries for BSL Saturday Southbound
    private static String SQL_CREATE_ENTRIES_BSL_SAT_SOUTHBOUND;
    public static final String TABLE_BSL_SAT_SOUTHBOUND = "BSL_SAT_SOUTHBOUND";

    // Table and table entries for BSL Sunday Northbound
    private static String SQL_CREATE_ENTRIES_BSL_SUN_NORTHBOUND;
    public static final String TABLE_BSL_SUN_NORTHBOUND = "BSL_SUN_NORTHBOUND";

    // Table and table entries for BSL Sunday Southbound
    private static String SQL_CREATE_ENTRIES_BSL_SUN_SOUTHBOUND;
    public static final String TABLE_BSL_SUN_SOUTHBOUND = "BSL_SUN_SOUTHBOUND";

    // Table and table entries for MFL Weekday Westbound
    private static String SQL_CREATE_ENTRIES_MFL_WEEKDAY_WESTBOUND;
    public static final String TABLE_MFL_WEEKDAY_WESTBOUND = "MFL_WEEKDAY_WESTBOUND";

    // Table and table entries for MFL Weekday Eastbound
    private static String SQL_CREATE_ENTRIES_MFL_WEEKDAY_EASTBOUND;
    public static final String TABLE_MFL_WEEKDAY_EASTBOUND = "MFL_WEEKDAY_EASTBOUND";

    // Table and table entries for MFL Saturday Westbound
    private static String SQL_CREATE_ENTRIES_MFL_SAT_WESTBOUND;
    public static final String TABLE_MFL_SAT_WESTBOUND = "MFL_SAT_WESTBOUND";

    // Table and table entries for MFL Saturday Eastbound
    private static String SQL_CREATE_ENTRIES_MFL_SAT_EASTBOUND;
    public static final String TABLE_MFL_SAT_EASTBOUND = "MFL_SAT_EASTBOUND";

    // Table and table entries for MFL Sunday Westbound
    private static String SQL_CREATE_ENTRIES_MFL_SUN_WESTBOUND;
    public static final String TABLE_MFL_SUN_WESTBOUND = "MFL_SUN_WESTBOUND";

    // Table and table entries for MFL Weekday Eastbound
    private static String SQL_CREATE_ENTRIES_MFL_SUN_EASTBOUND;
    public static final String TABLE_MFL_SUN_EASTBOUND = "MFL_SUN_EASTBOUND";

    public static final String[] TABLE_NAME_LIST = {TABLE_BSL_WEEKDAY_NORTHBOUND, TABLE_BSL_WEEKDAY_SOUTHBOUND, TABLE_BSL_SAT_NORTHBOUND,
            TABLE_BSL_SAT_SOUTHBOUND, TABLE_BSL_SUN_NORTHBOUND, TABLE_BSL_SUN_SOUTHBOUND, TABLE_MFL_WEEKDAY_WESTBOUND,
            TABLE_MFL_WEEKDAY_EASTBOUND, TABLE_MFL_SAT_WESTBOUND, TABLE_MFL_SAT_EASTBOUND, TABLE_MFL_SUN_WESTBOUND, TABLE_MFL_SUN_EASTBOUND};

    private static final String SQL_DELETE_ENTRIES = "";

    private SQLiteDatabase database;
    private ArrayList<ArrayList<String>> columnNames;

    //Constructor for the creation
    public SubwayScheduleCreatorDbHelper(Context context, ArrayList<ArrayList<String>> columnNames) {
        super(context, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+DATABASE_NAME);
        this.columnNames = columnNames;
//        createEntries();
    }

    //Constructor for retrieval
    public SubwayScheduleCreatorDbHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+DATABASE_NAME);
    }

    private void createEntries() {
        createBSLWeekdayNorth();
        createBSLWeekdaySouth();
        createBSLSatNorth();
        createBSLSatSouth();
        createBSLSunNorth();
        createBSLSunSouth();
        createMFLWeekdayWest();
        createMFLWeekdayEast();
        createMFLSatWest();
        createMFLSatEast();
        createMFLSunWest();
        createMFLSunEast();
    }

    private void createBSLWeekdayNorth(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection0 = columnNames.get(0);
        for(String column: columnNamesSection0){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length() - 2); //deletes last comma

        SQL_CREATE_ENTRIES_BSL_WEEKDAY_NORTHBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_BSL_WEEKDAY_NORTHBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;

        Log.d(TAG, SQL_CREATE_ENTRIES_BSL_WEEKDAY_NORTHBOUND);
    }

    private void createBSLWeekdaySouth(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection1 = columnNames.get(1);
        for(String column: columnNamesSection1){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2); //deletes last comma

        SQL_CREATE_ENTRIES_BSL_WEEKDAY_SOUTHBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_BSL_WEEKDAY_SOUTHBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;

        Log.d(TAG, SQL_CREATE_ENTRIES_BSL_WEEKDAY_SOUTHBOUND);
    }

    private void createBSLSatNorth(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection2 = columnNames.get(2);
        for(String column: columnNamesSection2){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2); //deletes last comma

        SQL_CREATE_ENTRIES_BSL_SAT_NORTHBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_BSL_SAT_NORTHBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;
    }

    private void createBSLSatSouth(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection3 = columnNames.get(3);
        for(String column: columnNamesSection3){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2); //deletes last comma

        SQL_CREATE_ENTRIES_BSL_SAT_SOUTHBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_BSL_SAT_SOUTHBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;
    }

    private void createBSLSunNorth(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection4 = columnNames.get(4);
        for(String column: columnNamesSection4){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2); //deletes last comma

        SQL_CREATE_ENTRIES_BSL_SUN_NORTHBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_BSL_SUN_NORTHBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;
    }

    private void createBSLSunSouth(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection5 = columnNames.get(5);
        for(String column: columnNamesSection5){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2); //deletes last comma

        SQL_CREATE_ENTRIES_BSL_SUN_SOUTHBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_BSL_SUN_SOUTHBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;
    }

    private void createMFLWeekdayWest(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection6 = columnNames.get(6);
        for(String column: columnNamesSection6){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2); //deletes last comma

        SQL_CREATE_ENTRIES_MFL_WEEKDAY_WESTBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_MFL_WEEKDAY_WESTBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;
    }

    private void createMFLWeekdayEast(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection7 = columnNames.get(7);
        for(String column: columnNamesSection7){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2);
        SQL_CREATE_ENTRIES_MFL_WEEKDAY_EASTBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_MFL_WEEKDAY_EASTBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;
    }

    private void createMFLSatWest(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection8 = columnNames.get(8);
        for(String column: columnNamesSection8){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2); //deletes last comma

        SQL_CREATE_ENTRIES_MFL_SAT_WESTBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_MFL_SAT_WESTBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;
    }

    private void createMFLSatEast(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection9 = columnNames.get(9);
        for(String column: columnNamesSection9){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2); //deletes last comma

        SQL_CREATE_ENTRIES_MFL_SAT_EASTBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_MFL_SAT_EASTBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;
    }

    private void createMFLSunWest(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection10 = columnNames.get(10);
        for(String column: columnNamesSection10){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2); //deletes last comma

        SQL_CREATE_ENTRIES_MFL_SUN_WESTBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_MFL_SUN_WESTBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;
    }

    private void createMFLSunEast(){
        StringBuilder buildColumnNameList = new StringBuilder();
        ArrayList<String> columnNamesSection11 = columnNames.get(11);
        for(String column: columnNamesSection11){
            buildColumnNameList.append("\"");
            buildColumnNameList.append(column);
            buildColumnNameList.append("\"");
            buildColumnNameList.append(COLUMN_DATA_TYPE);
            buildColumnNameList.append(COMMA_SEP);
        }
        buildColumnNameList.deleteCharAt(buildColumnNameList.length()-2); //deletes last comma

        SQL_CREATE_ENTRIES_MFL_SUN_EASTBOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_MFL_SUN_EASTBOUND + " (" +
                ARRIVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + buildColumnNameList + TABLE_END;
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_BSL_WEEKDAY_NORTHBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_BSL_WEEKDAY_SOUTHBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_BSL_SAT_NORTHBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_BSL_SAT_SOUTHBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_BSL_SUN_NORTHBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_BSL_SUN_SOUTHBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_MFL_WEEKDAY_WESTBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_MFL_WEEKDAY_EASTBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_MFL_SAT_WESTBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_MFL_SAT_EASTBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_MFL_SUN_WESTBOUND);
        db.execSQL(SQL_CREATE_ENTRIES_MFL_SUN_EASTBOUND);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
