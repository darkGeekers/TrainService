package cn.trainservice.trainservice.journey.model;

import java.util.ArrayList;

import cn.trainservice.trainservice.journey.JourneyViewModelInterface;

/**
 * Created by BrainWang on 2016/4/6.
 */
public class ViewModel {
    private JourneyViewModelInterface delegate;
    public String mUserName="";
    public String mID="";
    public String mFromCity="";
    public String mToCity="";
    public String mTrainNumber="";

    public ArrayList<TrainStation> mTrainStations;
    public int mCurrentStationIndex;


    public ViewModel(JourneyViewModelInterface idelegate){
        delegate=idelegate;
    }




}
