package cn.trainservice.trainservice.journey.model;

/**
 * Created by BrainWang on 2016/4/8.
 */
public class CityInfo {
    public String mId="-1";
    public String mName="- -";
    public String mIntroduce="";
    public String mImageUrl="";
    public String mAllIntroduceUrl="";
    public String mAddress="";

    private static CityInfo currentCityInfo;
    private CityInfo(){

    }

    public static CityInfo getCurrentCityInfo(){
        if(currentCityInfo==null)
            currentCityInfo=new CityInfo();
        return currentCityInfo;

    }

    public static void loadCurrentCityData(String id,String mName, String mIntroduce,String mImageUrl,
                                           String mAllIntroduceUrl,String mAddress){
        currentCityInfo.mId=id;
        currentCityInfo.mName=mName;
        currentCityInfo.mIntroduce=mIntroduce;
        currentCityInfo.mImageUrl=mImageUrl;
        currentCityInfo.mAllIntroduceUrl=mAllIntroduceUrl;
        currentCityInfo.mAddress=mAddress;
    }

}
