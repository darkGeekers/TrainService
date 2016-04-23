package cn.trainservice.trainservice.service.movie;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.trainservice.trainservice.R;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.impl.DefaultImageLoadHandler;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        ImageButton btn_movie_play=(ImageButton)findViewById(R.id.btn_movie_play);
        CubeImageView thumb_movie=(CubeImageView)findViewById(R.id.thumb_movie);
        TextView mv_introduce=(TextView)findViewById(R.id.mv_introduce);
        TextView mv_title=(TextView)findViewById(R.id.tv_mv_title);
        TextView tv_mv_year=(TextView)findViewById(R.id.tv_mv_year);
        TextView tv_mv_genres=(TextView)findViewById(R.id.tv_mv_genres);
        TextView tv_mv_rate=(TextView)findViewById(R.id.tv_mv_rate);
        Intent intent = getIntent();
        String filmType =intent.getStringExtra("filmType");
        String introduce =intent.getStringExtra("introduce");
        String imgUrl =intent.getStringExtra("imgUrl");
        final String title =intent.getStringExtra("title");
        final String sourceUrl =intent.getStringExtra("sourceUrl");
        String filmYear =intent.getStringExtra("filmYear");
        String filmRate =intent.getStringExtra("filmRate");
        ActionBar actionbar=getSupportActionBar();
        mv_title.setText(title);
        actionbar.setTitle(title);
        mv_introduce.setText(introduce);
        tv_mv_year.setText(filmYear);
        tv_mv_genres.setText(filmType);
        tv_mv_rate.setText(filmRate);
        DefaultImageLoadHandler handler = new DefaultImageLoadHandler(this);
        handler.setLoadingResources(R.mipmap.loading);
        imageLoader = ImageLoaderFactory.create(this, handler);
        thumb_movie.loadImage(imageLoader, imgUrl );
        btn_movie_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("title",title);
                intent.putExtra("sourceUrl",sourceUrl);
                intent.setClassName("cn.wangbaiyuan.superplayer","cn.wangbaiyuan.superplayer.VideoDetailActivity");
                try{
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(MovieDetailActivity.this,"Plugin not found!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
           finish();
           // startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
