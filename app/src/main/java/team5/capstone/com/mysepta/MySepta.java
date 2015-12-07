package team5.capstone.com.mysepta;

import android.app.Application;
import android.support.multidex.MultiDex;
import io.smooch.core.Smooch;

/**
 * Created by Andrew on 12/5/2015.
 */
public class MySepta extends Application{
    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        Smooch.init(this, "3rujej3f71sovyck6zunwg6jp");
    }
}