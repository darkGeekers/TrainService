package cn.trainservice.trainservice;

import android.app.Application;
import android.content.Context;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.beardedhen.androidbootstrap.font.FontAwesome;

/**
 * Created by BrainWang on 2016/3/27.
 */
public class TrainServiceApplication extends Application {
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
}
