package team5.capstone.com.mysepta.Models;

import android.content.Context;

import java.util.HashMap;

import team5.capstone.com.mysepta.R;

/**
 * Created by tiestodoe on 9/24/15.
 */
public class SubwayLocationData {
    private HashMap<String, Integer> subwayStopIdLocationMap_NORTH_AND_EAST;
    private HashMap<String, Integer> subwayStopIdLocationMap_SOUTH_AND_WEST;

    private String[] bslStrings;
    private String[] mflStrings;

    public SubwayLocationData(Context context) {
        bslStrings = context.getResources().getStringArray(R.array.broad_street_line_sorted);
        mflStrings = context.getResources().getStringArray(R.array.market_frankford_line_sorted);
        buildList();
    }

    public int getStopId(String locationKey, String direction){
        if(direction.equalsIgnoreCase("North") || direction.equalsIgnoreCase("East")){
            return subwayStopIdLocationMap_NORTH_AND_EAST.get(locationKey);
        }else if(direction.equalsIgnoreCase("South") || direction.equalsIgnoreCase("West")){
            return subwayStopIdLocationMap_SOUTH_AND_WEST.get(locationKey);
        }else{
            return subwayStopIdLocationMap_NORTH_AND_EAST.get(locationKey);
        }

    }

    //TODO: NIGHT OWL
    private void buildList(){
        //Intialize Maps
        subwayStopIdLocationMap_NORTH_AND_EAST = new HashMap<>();
        subwayStopIdLocationMap_SOUTH_AND_WEST = new HashMap<>();

        /*BEGIN BSL TO FERN ROCK*/
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[0], 32144);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[1], 32151);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[2], 32134);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[3], 32148);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[4], 32145);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[5], 32141);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[6], 32138);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[7], 32152);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[8], 32146);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[9], 20965);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[10], 32147);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[11], 32153);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[12], 32155);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[13], 32139);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[14], 32150);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[15], 32156);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[16], 32135);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[17], 32142);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[18], 32136);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[19], 32143);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[20], 32149);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[21], 32137);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[22], 32140);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(bslStrings[23], 32154);
        /*END BSL TO FERN ROCK*/

//        nightOwlBslToFernRock = new HashMap<>();

        /*BEGIN BSL TO SOUTH PHILLY*/
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[0], 21531);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[1], 142);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[2], 152);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[3], 1277);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[4], 2440);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[5], 1281);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[6], 1284);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[7], 140);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[8], 1278);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[9], 20965);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[10], 20966);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[11], 1274);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[12], 1272);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[13], 1283);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[14], 2439);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[15], 82);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[16], 20967);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[17], 1280);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[18], 1286);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[19], 1279);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[20], 1276);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[21], 1285);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[22], 1282);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(bslStrings[23], 1273);
        /*END BSL TO SOUTH PHILLY*/

//        nightOwlBslToSouthPhilly = new HashMap<>();
//        mfltoFrankford = new HashMap<>();

        /*BEGIN MFL TO FRANKFORD*/
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[0], 2456);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[1], 2455);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[2], 1392);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[3], 428);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[4], 21532);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[5], 2453);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[6], 2452);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[7], 2451);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[8], 2451);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[9], 2449);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[10], 2448);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[11], 2447);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[12], 416);//SKIP 13 BECAUSE EXTRA 69th Street line
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[14], 2457);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[15], 60);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[16], 217);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[17], 2460);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[18], 2464);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[19], 838);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[20], 61);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[21], 353);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[22], 2462);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[23], 2446);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[24], 797);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[25], 2459);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[26], 2463);
        subwayStopIdLocationMap_NORTH_AND_EAST.put(mflStrings[27], 2461);
        /*END MFL TO FRANKFORD*/

        /*BEING MFL TO 69TH*/
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[0], 32173);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[1], 32174);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[2], 32175);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[3], 32170);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[4], 32176);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[5], 32177);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[6], 32178);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[7], 32179);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[8], 32180);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[9], 32181);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[10], 32171);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[11], 32182);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[13], 20845);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[14], 32172);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[15], 32163);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[16], 32159);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[17], 32167);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[18], 32160);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[19], 32161);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[20], 32158);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[21], 32168);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[22], 32165);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[23], 32184);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[24], 32164);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[25], 32169);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[26], 32162);
        subwayStopIdLocationMap_SOUTH_AND_WEST.put(mflStrings[27], 32166);
        /*END MFL TO 69TH*/

//        nightOwlMfltoFrankford = new HashMap<>();
//        nightOwlMflto69th = new HashMap<>();
    }
}
