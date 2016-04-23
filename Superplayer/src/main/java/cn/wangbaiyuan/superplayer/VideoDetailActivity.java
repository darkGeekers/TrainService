package cn.wangbaiyuan.superplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * An activity representing a single Movie detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 */
public class VideoDetailActivity extends AppCompatActivity {


    private VideoView videoView;
    private MediaController mc;
    private long pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            Vitamio.isInitialized(this);
            videoView = (VideoView) findViewById(R.id.videoView);
            Intent intent = getIntent();
            final String title =intent.getStringExtra("title");
            actionBar.setTitle(title);
            final String sourceUrl =intent.getStringExtra("sourceUrl");
            String uri = sourceUrl;
            videoView.setVideoPath(uri);
            mc = new MediaController(this);
            mc.setFileName(title);
            videoView.setMediaController(mc);

            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
           // NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
           finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
