package cn.trainservice.trainservice.journey.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BrainWang on 2016/4/6.
 */
public class TrainStation {
    public int mId=-1;
    public String mName="";
    public String mArrivalTime="";
    public int mTimeStay=3;
    public TrainStation(int id,String name,String arrivaltime,int timeStay){
        mName=name;
        mId=id;
        mArrivalTime=arrivaltime;
        mTimeStay=timeStay;
    }

    public TrainStation(JSONObject js){

        try {
            mName=js.getString("SName");
            mId=Integer.parseInt(js.getString("Station_Id"));
            mArrivalTime=js.getString("Arrival_Time");
            mTimeStay=Integer.parseInt(js.getString("Stop_time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
