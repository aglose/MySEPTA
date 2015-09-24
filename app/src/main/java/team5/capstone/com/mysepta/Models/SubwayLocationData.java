package team5.capstone.com.mysepta.Models;

import android.content.Context;

import java.util.HashMap;

import team5.capstone.com.mysepta.R;

/**
 * Created by tiestodoe on 9/24/15.
 */
public class SubwayLocationData {
    private HashMap<Integer, String> subwayStopIdLocationMap;

    private String[] bslStrings;
    private String[] mflStrings;

    public SubwayLocationData(Context context) {
        bslStrings = context.getResources().getStringArray(R.array.broad_street_line);
        mflStrings = context.getResources().getStringArray(R.array.market_frankford_line);
        buildList();
    }

    public String[] getBslStrings() {
        return bslStrings;
    }

    public String[] getMflStrings() {
        return mflStrings;
    }

    //TODO: NIGHT OWL
    private void buildList(){
        //Intialize Map
        subwayStopIdLocationMap = new HashMap<>();

        /*BEGIN BSL TO FERN ROCK*/
        subwayStopIdLocationMap.put(32144, bslStrings[0]);
        subwayStopIdLocationMap.put(32151, bslStrings[1]);
        subwayStopIdLocationMap.put(32134, bslStrings[2]);
        subwayStopIdLocationMap.put(32148, bslStrings[3]);
        subwayStopIdLocationMap.put(32145, bslStrings[4]);
        subwayStopIdLocationMap.put(32141, bslStrings[5]);
        subwayStopIdLocationMap.put(32138, bslStrings[6]);
        subwayStopIdLocationMap.put(32152, bslStrings[7]);
        subwayStopIdLocationMap.put(32146, bslStrings[8]);
        subwayStopIdLocationMap.put(20965, bslStrings[9]);
        subwayStopIdLocationMap.put(32147, bslStrings[10]);
        subwayStopIdLocationMap.put(32153, bslStrings[11]);
        subwayStopIdLocationMap.put(32155, bslStrings[12]);
        subwayStopIdLocationMap.put(32139, bslStrings[13]);
        subwayStopIdLocationMap.put(32150, bslStrings[14]);
        subwayStopIdLocationMap.put(32156, bslStrings[15]);
        subwayStopIdLocationMap.put(32135, bslStrings[16]);
        subwayStopIdLocationMap.put(32142, bslStrings[17]);
        subwayStopIdLocationMap.put(32136, bslStrings[18]);
        subwayStopIdLocationMap.put(32143, bslStrings[19]);
        subwayStopIdLocationMap.put(32149, bslStrings[20]);
        subwayStopIdLocationMap.put(32137, bslStrings[21]);
        subwayStopIdLocationMap.put(32140, bslStrings[22]);
        subwayStopIdLocationMap.put(32154, bslStrings[23]);
        /*END BSL TO FERN ROCK*/

//        nightOwlBslToFernRock = new HashMap<>();

        /*BEGIN BSL TO SOUTH PHILLY*/
        subwayStopIdLocationMap.put(21531, bslStrings[0]);
        subwayStopIdLocationMap.put(142, bslStrings[1]);
        subwayStopIdLocationMap.put(152, bslStrings[2]);
        subwayStopIdLocationMap.put(1277, bslStrings[3]);
        subwayStopIdLocationMap.put(2440, bslStrings[4]);
        subwayStopIdLocationMap.put(1281, bslStrings[5]);
        subwayStopIdLocationMap.put(1284, bslStrings[6]);
        subwayStopIdLocationMap.put(140, bslStrings[7]);
        subwayStopIdLocationMap.put(1278, bslStrings[8]);
        subwayStopIdLocationMap.put(20965, bslStrings[9]);
        subwayStopIdLocationMap.put(20966, bslStrings[10]);
        subwayStopIdLocationMap.put(1274, bslStrings[11]);
        subwayStopIdLocationMap.put(1272, bslStrings[12]);
        subwayStopIdLocationMap.put(1283, bslStrings[13]);
        subwayStopIdLocationMap.put(2439, bslStrings[14]);
        subwayStopIdLocationMap.put(82, bslStrings[15]);
        subwayStopIdLocationMap.put(20967, bslStrings[16]);
        subwayStopIdLocationMap.put(1280, bslStrings[17]);
        subwayStopIdLocationMap.put(1286, bslStrings[18]);
        subwayStopIdLocationMap.put(1279, bslStrings[19]);
        subwayStopIdLocationMap.put(1276, bslStrings[20]);
        subwayStopIdLocationMap.put(1285, bslStrings[21]);
        subwayStopIdLocationMap.put(1282, bslStrings[22]);
        subwayStopIdLocationMap.put(1273, bslStrings[23]);
        /*END BSL TO SOUTH PHILLY*/

//        nightOwlBslToSouthPhilly = new HashMap<>();
//        mfltoFrankford = new HashMap<>();

        /*BEGIN MFL TO FRANKFORD*/
        subwayStopIdLocationMap.put(2456, mflStrings[0]);
        subwayStopIdLocationMap.put(2455, mflStrings[1]);
        subwayStopIdLocationMap.put(1392, mflStrings[2]);
        subwayStopIdLocationMap.put(428, mflStrings[3]);
        subwayStopIdLocationMap.put(21532, mflStrings[4]);
        subwayStopIdLocationMap.put(2453, mflStrings[5]);
        subwayStopIdLocationMap.put(2452, mflStrings[6]);
        subwayStopIdLocationMap.put(2451, mflStrings[7]);
        subwayStopIdLocationMap.put(2450, mflStrings[8]);
        subwayStopIdLocationMap.put(2449, mflStrings[9]);
        subwayStopIdLocationMap.put(2448, mflStrings[10]);
        subwayStopIdLocationMap.put(2447, mflStrings[11]);
        subwayStopIdLocationMap.put(416, mflStrings[12]); //SKIP 13 BECAUSE EXTRA 69th Street line
        subwayStopIdLocationMap.put(2457, mflStrings[14]);
        subwayStopIdLocationMap.put(60, mflStrings[15]);
        subwayStopIdLocationMap.put(217, mflStrings[16]);
        subwayStopIdLocationMap.put(2460, mflStrings[17]);
        subwayStopIdLocationMap.put(2464, mflStrings[18]);
        subwayStopIdLocationMap.put(838, mflStrings[19]);
        subwayStopIdLocationMap.put(61, mflStrings[20]);
        subwayStopIdLocationMap.put(353, mflStrings[21]);
        subwayStopIdLocationMap.put(2462, mflStrings[22]);
        subwayStopIdLocationMap.put(2446, mflStrings[23]);
        subwayStopIdLocationMap.put(797, mflStrings[24]);
        subwayStopIdLocationMap.put(2459, mflStrings[25]);
        subwayStopIdLocationMap.put(2463, mflStrings[26]);
        subwayStopIdLocationMap.put(2461, mflStrings[27]);
        /*END MFL TO FRANKFORD*/

        /*BEING MFL TO 69TH*/
        subwayStopIdLocationMap.put(32173, mflStrings[0]);
        subwayStopIdLocationMap.put(32174, mflStrings[1]);
        subwayStopIdLocationMap.put(32175, mflStrings[2]);
        subwayStopIdLocationMap.put(32170, mflStrings[3]);
        subwayStopIdLocationMap.put(32176, mflStrings[4]);
        subwayStopIdLocationMap.put(32177, mflStrings[5]);
        subwayStopIdLocationMap.put(32178, mflStrings[6]);
        subwayStopIdLocationMap.put(32179, mflStrings[7]);
        subwayStopIdLocationMap.put(32180, mflStrings[8]);
        subwayStopIdLocationMap.put(32181, mflStrings[9]);
        subwayStopIdLocationMap.put(32171, mflStrings[10]);
        subwayStopIdLocationMap.put(32182, mflStrings[11]);
//        subwayStopIdLocationMap.put(416, mflStrings[12]); ALREADY ENTERED/PUT
        subwayStopIdLocationMap.put(20845, mflStrings[13]);
        subwayStopIdLocationMap.put(32172, mflStrings[14]);
        subwayStopIdLocationMap.put(32163, mflStrings[15]);
        subwayStopIdLocationMap.put(32159, mflStrings[16]);
        subwayStopIdLocationMap.put(32167, mflStrings[17]);
        subwayStopIdLocationMap.put(32160, mflStrings[18]);
        subwayStopIdLocationMap.put(32161, mflStrings[19]);
        subwayStopIdLocationMap.put(32158, mflStrings[20]);
        subwayStopIdLocationMap.put(32168, mflStrings[21]);
        subwayStopIdLocationMap.put(32165, mflStrings[22]);
        subwayStopIdLocationMap.put(32184, mflStrings[23]);
        subwayStopIdLocationMap.put(32164, mflStrings[24]);
        subwayStopIdLocationMap.put(32169, mflStrings[25]);
        subwayStopIdLocationMap.put(32162, mflStrings[26]);
        subwayStopIdLocationMap.put(32166, mflStrings[27]);
        /*END MFL TO 69TH*/

//        nightOwlMfltoFrankford = new HashMap<>();
//        nightOwlMflto69th = new HashMap<>();
    }
}
