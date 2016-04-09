package cn.trainservice.trainservice.journey.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.trainservice.trainservice.journey.model.TrainStation;

/**
 * Created by BrainWang on 2016/4/8.
 */
public class JsonHelper {

    public static ArrayList<TrainStation> parseStationList(String str) {
        ArrayList<TrainStation> list = new ArrayList<>();
        try {
            JSONObject Json = new JSONObject(str);
            boolean result = Json.getBoolean("result");
            if (result) {
                int size = Json.getInt("station_size");
                JSONObject stationsJson=Json.getJSONObject("stations");

                String index = "-1";
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = stationsJson.getJSONObject(index);
                    index = jsonObject.getString("Station_Id");
                    list.add(new TrainStation(jsonObject));
                }
            } else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
//    public static void main(String[] str2){
//
//        String str="{\"result\":true,\"station_size\":9,\"stations\":{\"-1\":{\"Pre_Station\":-1,\"Station_Id\":\"5\",\"SName\":\"LanZhou\",\"Next_Station\":\"6\",\"Arrival_Time\":\"14:31:00\",\"Stop_time\":\"0\",\"Drive_Time\":\"14:31:00\"},\"5\":{\"Pre_Station\":\"5\",\"Station_Id\":\"6\",\"SName\":\"LongXi\",\"Next_Station\":\"7\",\"Arrival_Time\":\"16:48:00\",\"Stop_time\":\"4\",\"Drive_Time\":\"16:52:00\"},\"6\":{\"Pre_Station\":\"6\",\"Station_Id\":\"7\",\"SName\":\"TianShui\",\"Next_Station\":\"8\",\"Arrival_Time\":\"19:26:00\",\"Stop_time\":\"7\",\"Drive_Time\":\"19:33:00\"},\"7\":{\"Pre_Station\":\"7\",\"Station_Id\":\"8\",\"SName\":\"XiAn\",\"Next_Station\":\"9\",\"Arrival_Time\":\"00:43:00\",\"Stop_time\":\"10\",\"Drive_Time\":\"00:53:00\"},\"8\":{\"Pre_Station\":\"8\",\"Station_Id\":\"9\",\"SName\":\"ZhengZhou\",\"Next_Station\":\"10\",\"Arrival_Time\":\"07:31:00\",\"Stop_time\":\"19\",\"Drive_Time\":\"07:50:00\"},\"9\":{\"Pre_Station\":\"9\",\"Station_Id\":\"10\",\"SName\":\"AnYang\",\"Next_Station\":\"11\",\"Arrival_Time\":\"11:00:00\",\"Stop_time\":\"10\",\"Drive_Time\":\"11:10:00\"},\"10\":{\"Pre_Station\":\"10\",\"Station_Id\":\"11\",\"SName\":\"XingTai\",\"Next_Station\":\"12\",\"Arrival_Time\":\"12:32:00\",\"Stop_time\":\"4\",\"Drive_Time\":\"12:36:00\"},\"11\":{\"Pre_Station\":\"11\",\"Station_Id\":\"12\",\"SName\":\"BaoDing\",\"Next_Station\":\"13\",\"Arrival_Time\":\"15:15:00\",\"Stop_time\":\"4\",\"Drive_Time\":\"15:19:00\"},\"12\":{\"Pre_Station\":\"12\",\"Station_Id\":\"13\",\"SName\":\"BeiJing\",\"Next_Station\":null,\"Arrival_Time\":\"17:00:00\",\"Stop_time\":\"0\",\"Drive_Time\":\"17:00:00\"}}}";
//        System.out.println(str);
//        parseStationList(str);
//
//    }
}
