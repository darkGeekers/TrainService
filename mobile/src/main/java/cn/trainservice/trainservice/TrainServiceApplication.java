package cn.trainservice.trainservice;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;

import cn.trainservice.trainservice.journey.view.TicketInfo;

/**
 * Created by BrainWang on 2016/3/27.
 */
public class TrainServiceApplication extends Application {

    public static boolean hasLogin = false;
    public static String JourneyBroadcastAction = "cn.trainservice.trainservice.JourneyBroadcastAction";

    //some url
    private static String http = "http";
    private static String serverIP = "172.21.19.1";
    private static String URLGetStationList = "/journey/stationList";
    private static String URLGetCurrentCityInfo = "/journey/currentCityInfo";

    //public static String URLGetCurrentCityInfo="/journey/currentCityInfo";

    private static TicketInfo ticket;

    public static String[] getTabTitles(Context context) {
        String[] result = {
                context.getString(R.string.tab_journey),
                context.getString(R.string.tab_services),
                context.getString(R.string.tab_chat)
        };
        return result;
    }

    public static String[] getTabIcons(Context context) {
        String[] result = {
                FontAwesome.FA_TRAIN,
                FontAwesome.FA_TH_LARGE,
                FontAwesome.FA_COMMENTS
        };
        return result;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }

    public static void setTickt(TicketInfo tk) {
        ticket = tk;
    }

    public static TicketInfo getTicket() {
        return ticket;
    }

    public static void attemptToEnterUserCenter(Context context) {
        if (!hasLogin) {
            context.startActivity(new Intent(context, LoginActivity.class));
        } else {

        }
    }

    public static String getWebServerHome() {
        return http + "://" + serverIP;
    }

    public static String getStationListUrl() {
        return http + "://" + serverIP + URLGetStationList;
    }

    public static String getCurrentCityInfoUrl() {
        return http + "://" + serverIP + URLGetCurrentCityInfo;
    }

    public static LiteHttp getLiteHttp(Context context) {

            HttpConfig config = new HttpConfig(context) // configuration quickly
                    .setDebugged(true)                   // log output when debugged
                    .setDetectNetwork(true)              // detect network before connect
                    .setDoStatistics(true)               // statistics of time and traffic
                    .setUserAgent("Mozilla/5.0 (...)")   // set custom User-Agent
                    .setTimeOut(10000, 10000);             // connect and socket timeout: 10s
            return LiteHttp.newApacheHttpClient(config);

    }

}
