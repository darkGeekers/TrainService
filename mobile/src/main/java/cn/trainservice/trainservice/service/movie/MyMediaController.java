package cn.trainservice.trainservice.service.movie;

import android.content.Context;
import android.util.AttributeSet;

import io.vov.vitamio.widget.MediaController;

/**
 * Created by BrainWang on 2016/4/5.
 */
public class MyMediaController extends MediaController {
    public MyMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMediaController(Context context) {
        super(context);
    }
}
