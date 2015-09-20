package team5.capstone.com.mysepta.Interfaces;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Andrew on 9/20/2015.
 */
public interface SubwayLocationInterface {
    @GET("/sms/{location}/")
    Callback<ArrayList<String>> callback(@Path("location") String location);
}
