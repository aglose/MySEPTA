package team5.capstone.com.mysepta.Services;

import retrofit.RestAdapter;
import team5.capstone.com.mysepta.Interfaces.RailLocationInterface;
import team5.capstone.com.mysepta.Interfaces.SubwayLocationInterface;

/**
 * Created by Andrew on 9/20/2015.
 */
public class SubwayLocationService {
    public static SubwayLocationInterface getSubwayLocationInterface() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www3.septa.org").build();

        return restAdapter.create(SubwayLocationInterface.class);
    }
}
