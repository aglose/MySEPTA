package team5.capstone.com.mysepta.AsyncTasks;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import team5.capstone.com.mysepta.Models.Coordinates;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Retrieve train map.
 * Created by tiestodoe on 9/7/15.
 */
public class RetrieveTrainMapTask extends AsyncTask<InputStream, Void, Void>{
    private GoogleMap googleMap;
    private ArrayList<PolylineOptions> listLines = null;
    private final int[] trainColors = {Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.RED, Color.YELLOW, Color.rgb(255, 0, 0), Color.rgb(255, 0, 255), Color.rgb(255, 255, 222), Color.rgb(255, 0, 13), Color.rgb(33,0,255)};

    /**
     * Constructor
     * @param googleMap google map to generate
     */
    public RetrieveTrainMapTask(GoogleMap googleMap){
        this.googleMap = googleMap;
    }

    /**
     * Draw train lines
     * @param result no value
     */
    protected void onPostExecute(Void result) {
        for (PolylineOptions line : listLines) {
            googleMap.addPolyline(line);
        }
    }

    /**
     * Asynchronously retrieve train coordinates.
     * @param stream stream of data
     * @return nothing
     */
    @Override
    protected Void doInBackground(InputStream... stream) {

        try {
            listLines = new ArrayList<>();
            InputStream inputStream = stream[0];
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray placemarkArray = jsonObject.getJSONObject("kml").getJSONObject("Document").getJSONObject("Folder").getJSONArray("Placemark");

            Coordinates coordinate = null;

            int random = 0;
            for (int i = 0; i < placemarkArray.length(); i++) {
                ArrayList<LatLng> coordinatesList = new ArrayList<>();
                JSONObject place = (JSONObject) placemarkArray.get(i);
                Object lineString = place.getJSONObject("MultiGeometry").get("LineString");

                String coordinatesString = "";

                if (lineString instanceof JSONArray) {
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int j = 0; j < ((JSONArray) lineString).length(); j++) {
                        JSONObject line = (JSONObject) ((JSONArray) lineString).get(j);
                        stringBuilder.append(line.getString("coordinates"));
                    }
                    coordinatesString = stringBuilder.toString();
                } else {
                    coordinatesString = ((JSONObject) lineString).getString("coordinates");
                }

                String[] splitRawCoordinate = coordinatesString.split(" ");

                for (String coordinatePairPlus : splitRawCoordinate) {
                    String[] coordinateParts = coordinatePairPlus.split(",");
                    if (coordinateParts.length != 3) {
                        Log.d("Debug", "an error has occurred processing the raw coordinates");
                    } else {
                        coordinate = new Coordinates(Double.parseDouble(coordinateParts[1]),
                                Double.parseDouble(coordinateParts[0]));
                    }

                    coordinatesList.add(coordinate.getLatLng());

                }

                PolylineOptions lineOptions = new PolylineOptions().addAll(coordinatesList)
                        .color(trainColors[random++])
                        .width(10.0f)
                        .visible(true);
                listLines.add(lineOptions);
                random %= 11;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
