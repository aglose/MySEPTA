package team5.capstone.com.mysepta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import team5.capstone.com.mysepta.CallbackProxies.SubwayLocationProxy;

public class SubwayActivity extends AppCompatActivity {
    private static final String TAG = "SubwayActivity";
    private static final String STOP_ID_KEY = "StopID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway);

        Bundle bundle = this.getIntent().getExtras();
        int stopID = bundle.getInt(STOP_ID_KEY);

        final TextView text = (TextView) findViewById(R.id.textViewTest);
        text.setText(String.valueOf(stopID)+" "+bundle.getString("direction")+" "+bundle.getString("location"));

        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                String responseString = (String) o;
                text.setText(responseString);
                Log.d(TAG, responseString);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("Debug", "fail");
            }
        };

        SubwayLocationProxy subwayLocationProxy = new SubwayLocationProxy();
        subwayLocationProxy.getNextSubwayView(callback, String.valueOf(stopID));
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
