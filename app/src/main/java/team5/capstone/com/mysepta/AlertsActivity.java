package team5.capstone.com.mysepta;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import team5.capstone.com.mysepta.Fragment.AlertsFragment;

/**
 *
 */
public class AlertsActivity extends AppCompatActivity {
    private static final String TAG = "AlertsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        AlertsFragment myf = new AlertsFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentAlerts, myf);
        transaction.commit();

    }

}