package team5.capstone.com.mysepta;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;
import io.smooch.core.Smooch;

/**
 * Created by Andrew on 12/5/2015.
 */
public class MySeptaApplication extends Application{
    private static final boolean DEVELOPER_MODE = true;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();

        Smooch.init(this, "3rujej3f71sovyck6zunwg6jp");

        if(!DEVELOPER_MODE){
            Fabric.with(this, new Crashlytics());
            TwitterAuthConfig authConfig =  new TwitterAuthConfig("consumerKey", "consumerSecret");
            Fabric.with(this, new TwitterCore(authConfig), new TweetComposer(), new Crashlytics());
        }


    }
}