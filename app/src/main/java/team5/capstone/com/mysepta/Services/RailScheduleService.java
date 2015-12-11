package team5.capstone.com.mysepta.Services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import team5.capstone.com.mysepta.Interfaces.RailScheduleInterface;

/**
 * Created by Kevin on 10/21/15.
 */
public class RailScheduleService {

    /**
     * create and get rail schedule interface
     * @return RailScheduleInterface
     */
    public static RailScheduleInterface getRailScheduleInterface() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www3.septa.org")
                .setConverter(new GsonConverter(gson))
//                .setErrorHandler(new ServiceErrorHandler())
                .build();

        return restAdapter.create(RailScheduleInterface.class);
    }


}
