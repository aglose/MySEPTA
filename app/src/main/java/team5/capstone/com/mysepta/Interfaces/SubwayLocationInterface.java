package team5.capstone.com.mysepta.Interfaces;

import java.util.ArrayList;

import io.fabric.sdk.android.services.concurrency.Task;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Andrew on 9/20/2015.
 */
public interface SubwayLocationInterface {
    @GET("/sms/{location}/")
    public void getNextSubwayData(@Path("location")String location, Callback<ArrayList<String>> callback);

}
