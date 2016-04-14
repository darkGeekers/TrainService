package cn.trainservice.trainservice.journey.model;

import org.json.JSONException;
import org.json.JSONObject;

import cn.trainservice.trainservice.TrainServiceApplication;

/**
 * Created by BrainWang on 2016/4/8.
 */
public class CityInfo {
    public int mId=-1;
    public String mName="- -";
    public String mIntroduce="";
    public String mImageUrl="";
    public String mAllIntroduceUrl="";
    public String mAddress="";

    private static CityInfo currentCityInfo=new CityInfo();
    public CityInfo(){

    }
    private CityInfo(String json){
        try {
            JSONObject jso=new JSONObject(json);
            mId=Integer.parseInt(jso.getString("cur_station"));
            mName=jso.getString("SName");
            mIntroduce=jso.getString("Description");

            String mAllIntroduceHomeUrl = TrainServiceApplication.getCityInfoUrl(mId);
             mImageUrl = mAllIntroduceHomeUrl + "/thumb.jpg";
             mAllIntroduceUrl = mAllIntroduceHomeUrl + "/index.html";
            if(jso.has("SImageUrl")){
                mImageUrl=jso.getString("SImageUrl");
            }
            if(jso.has("SUrl")){
                mAllIntroduceUrl=jso.getString("SUrl");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public static CityInfo getCurrentCityInfo(){
        if(currentCityInfo==null)
            currentCityInfo=new CityInfo();
        return currentCityInfo;

    }

    public static void loadCurrentCityData(String json){
        currentCityInfo=new CityInfo(json);
//        currentCityInfo.mId=id;
//        currentCityInfo.mName=mName;
//        currentCityInfo.mIntroduce=mIntroduce;
//        currentCityInfo.mImageUrl=mImageUrl;
//        currentCityInfo.mAllIntroduceUrl=mAllIntroduceUrl;
//        currentCityInfo.mAddress=mAddress;
    }

}
