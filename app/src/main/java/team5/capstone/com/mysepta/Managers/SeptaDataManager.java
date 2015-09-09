package team5.capstone.com.mysepta.Managers;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Andrew on 9/7/2015.
 */
public class SeptaDataManager {
    private static String jsonResponse;
    private String query;

    public static void getAllTrainLocations(){
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
    }

    private static void retrieveInfo(String query) {

    }
}
