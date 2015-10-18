package team5.capstone.com.mysepta;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import team5.capstone.com.mysepta.Adapters.SubwayScheduleViewAdapter;
import team5.capstone.com.mysepta.DatabaseHelpers.SubwayScheduleCreatorDbHelper;
import team5.capstone.com.mysepta.Managers.SubwayScheduleManager;
import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;

public class SubwayActivity extends AppCompatActivity {
    private static final String TAG = "SubwayActivity";

    private static final String STOP_ID_KEY = "StopID";
    private static final String LOCATION_KEY = "Location";
    private static final String DIRECTION_KEY = "Direction";
    private static final String LINE_KEY = "Line";
    private static final String SQL_SELECT = "SELECT ";
    private static final String SQL_FROM = " FROM ";
    private static final String SQL_SEMI_COLON = ";";
    private static final String SQL_QUOTE = "\"";

    private static String direction;
    private static String location;
    private static int stopID;
    private static String line;

    private TextView text;
    private Toolbar toolbar;
    private RecyclerView recyclerScheduleView;
    private SubwayScheduleViewAdapter subwayScheduleViewAdapter;
    private SQLiteDatabase database;
    private SubwayScheduleCreatorDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway);

        initSetup();
        retrieveDatabaseInfo();
    }

    private void initSetup(){
        Bundle bundle = this.getIntent().getExtras();
        stopID = bundle.getInt(STOP_ID_KEY);
        location = bundle.getString(LOCATION_KEY);
        direction = bundle.getString(DIRECTION_KEY);
        line = bundle.getString(LINE_KEY);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //ENABLE BACK BUTTON
        getSupportActionBar().setTitle(location);
    }

    private void retrieveDatabaseInfo() {
        StringBuilder completeSQL = new StringBuilder();
        completeSQL.append(SQL_SELECT);
        completeSQL.append(SQL_QUOTE);
        completeSQL.append(location);
        completeSQL.append(SQL_QUOTE);
        completeSQL.append(SQL_FROM);
        completeSQL.append(chooseTable(completeSQL.toString()));
        completeSQL.append(SQL_SEMI_COLON);

        Log.d(TAG, completeSQL.toString());

        dbHelper = new SubwayScheduleCreatorDbHelper(this);

        open();

        Cursor c = database.rawQuery(completeSQL.toString(), null);
        ArrayList<SubwayScheduleItemModel> arrivals = new ArrayList<>();
        int i = 0;
        if (c.getCount() > 0)
        {
            c.moveToFirst();
            do {
                try {
                    String arrival = c.getString(c.getColumnIndex(location));
                    SimpleDateFormat oldFormat = new SimpleDateFormat("hh:mma");
                    Date time = oldFormat.parse(arrival);
                    oldFormat.applyPattern("hh:mm a");
                    String arrivalNewFormat = oldFormat.format(time);
                    if(arrivalNewFormat.startsWith("0")){
                        arrivalNewFormat = arrivalNewFormat.substring(1, arrivalNewFormat.length());
                    }

                    SubwayScheduleItemModel newArrival = new SubwayScheduleItemModel();
                    newArrival.setFormattedTimeStr(arrivalNewFormat);

                    arrivals.add(newArrival);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                i++;
            } while (c.moveToNext());
            c.close();
        }

        close();

        setUpRecyclerView(arrivals);
    }

    public void open() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    private void setUpRecyclerView(ArrayList<SubwayScheduleItemModel> arrivals) {
        recyclerScheduleView = (RecyclerView) findViewById(R.id.subwayScheduleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerScheduleView.setLayoutManager(layoutManager);

        subwayScheduleViewAdapter = new SubwayScheduleViewAdapter(arrivals);

        recyclerScheduleView.setAdapter(subwayScheduleViewAdapter);
    }

    private String chooseTable(String completeSQL) {
        String tableName = "";
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if(line.equalsIgnoreCase("MFL")){
            switch(dayOfWeek){
                case Calendar.SATURDAY:
                    if(direction.equalsIgnoreCase("EAST")){
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_MFL_SAT_EASTBOUND;
                    }else{
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_MFL_SAT_WESTBOUND;
                    }
                    break;
                case Calendar.SUNDAY:
                    if(direction.equalsIgnoreCase("EAST")){
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_MFL_SAT_EASTBOUND;
                    }else{
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_MFL_SAT_WESTBOUND;
                    }
                    break;
                default:
                    if(direction.equalsIgnoreCase("EAST")){
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_MFL_WEEKDAY_EASTBOUND;
                    }else{
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_MFL_WEEKDAY_WESTBOUND;
                    }
                    break;
            }

        }else if(line.equalsIgnoreCase("BSL")){
            switch(dayOfWeek){
                case Calendar.SATURDAY:
                    if(direction.equalsIgnoreCase("NORTH")){
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_BSL_SAT_NORTHBOUND;
                    }else{
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_BSL_SAT_SOUTHBOUND;
                    }
                    break;
                case Calendar.SUNDAY:
                    if(direction.equalsIgnoreCase("NORTH")){
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_BSL_SUN_NORTHBOUND;
                    }else{
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_BSL_SUN_SOUTHBOUND;
                    }
                    break;
                default:
                    if(direction.equalsIgnoreCase("NORTH")){
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_BSL_WEEKDAY_NORTHBOUND;
                    }else{
                        tableName = SubwayScheduleCreatorDbHelper.TABLE_BSL_WEEKDAY_SOUTHBOUND;
                    }
                    break;
            }
        }
        return tableName;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subway, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
