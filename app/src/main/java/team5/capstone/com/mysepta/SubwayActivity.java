package team5.capstone.com.mysepta;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import java.util.ArrayList;

import team5.capstone.com.mysepta.Adapters.SubwayScheduleViewAdapter;
import team5.capstone.com.mysepta.Managers.SubwayScheduleManager;
import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;

public class SubwayActivity extends AppCompatActivity {
    private static final String TAG = "SubwayActivity";
    private static final String STOP_ID_KEY = "StopID";
    private TextView text;
    private Toolbar toolbar;
    private RecyclerView recyclerScheduleView;
    private SubwayScheduleViewAdapter subwayScheduleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway);

        initSetup(); //WILL CHANGE THIS LATEr

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(""); //REMOVE TITLE
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //ENABLE BACK BUTTON


    }

    private void setUpRecyclerView(ArrayList<SubwayScheduleItemModel> arrivals) {
        recyclerScheduleView = (RecyclerView) findViewById(R.id.subwayScheduleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); //Notice LinearLayoutManager
        recyclerScheduleView.setLayoutManager(layoutManager);

        subwayScheduleViewAdapter = new SubwayScheduleViewAdapter(arrivals);

        recyclerScheduleView.setAdapter(subwayScheduleViewAdapter);
    }

    private void initSetup(){
        Bundle bundle = this.getIntent().getExtras();
        int stopID = bundle.getInt(STOP_ID_KEY);

        text = (TextView) findViewById(R.id.textViewTest);
        text.setText(String.valueOf(stopID) + " " + bundle.getString("direction") + " " + bundle.getString("location"));
        try {
            getResponseText("http://www3.septa.org/sms/"+String.valueOf(stopID));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Handler handleSMS = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            ArrayList<SubwayScheduleItemModel> arrivals =  SubwayScheduleManager.parseResponseString((String) msg.obj);
            setUpRecyclerView(arrivals);
            text.setText((String) msg.obj);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
    });


    private void getResponseText(final String stringUrl) throws IOException
    {
        Thread getSmsResponse = new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder response  = new StringBuilder();

                URL url = null;
                try {
                    url = new URL(stringUrl);

                    HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
                    if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
                        String strLine = null;
                        while ((strLine = input.readLine()) != null)
                        {
                            response.append(strLine);
                        }
                        input.close();
                        String d =response.toString();
                        Message message = new Message();
                        message.obj = d;
                        handleSMS.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        getSmsResponse.start();
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
