package team5.capstone.com.mysepta.Services;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import team5.capstone.com.mysepta.Interfaces.BusStopsInterface;
import team5.capstone.com.mysepta.Interfaces.BusTimesInterface;
import team5.capstone.com.mysepta.Models.BusModelHolder;
import team5.capstone.com.mysepta.Models.BusTimeModel;

/**
 * Created by Andrew on 2/3/2016.
 */
public class BusTimesService {
    private static final String TAG = "BusTimesService";

    public static BusTimesInterface getBusTimesInterface() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www3.septa.org")
                .setConverter(new GsonConverter(gson))
                .setErrorHandler(new ServiceErrorHandler())
                .build();

        return restAdapter.create(BusTimesInterface.class);
    }


    static class ServiceErrorHandler implements ErrorHandler {
        public static final String TAG = "ServiceErrorHandler";

        @Override
        public Throwable handleError(RetrofitError cause) {

            Log.d(TAG, "cause? " + cause.getMessage());

            Log.d(TAG, "url? " + cause.getUrl());

            Response r = cause.getResponse();
            if (r != null) {

                return new Exception("a service call exception");
            }
            return cause;
        }
    }
}
