package cn.trainservice.trainservice.service.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cn.trainservice.trainservice.R;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.impl.DefaultImageLoadHandler;

/**
 * An activity representing a single Food detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 */
public class FoodDetailActivity extends AppCompatActivity {
    private  ImageLoader imageLoader;
    private CubeImageView thumb,thumb_food;
    private TextView tv_food_price,tv_food_sales,tv_food_list_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        thumb = (CubeImageView) findViewById(R.id.thumb_detail_food);
        thumb_food= (CubeImageView) findViewById(R.id.thumb_food);
        tv_food_price = (TextView) findViewById(R.id.tv_food_price);
        tv_food_sales = (TextView) findViewById(R.id.tv_food_sales);
        tv_food_list_title= (TextView) findViewById(R.id.tv_food_detail_title);
        DefaultImageLoadHandler handler = new DefaultImageLoadHandler(this);
        imageLoader = ImageLoaderFactory.create(this, handler);
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        final String title =intent.getStringExtra("title");
        final String imgUrl =intent.getStringExtra("imgUrl");
        String number =intent.getStringExtra("number");
        String Price =intent.getStringExtra("Price");
        actionBar.setTitle(title);
        tv_food_list_title.setText(title);
        tv_food_price.setText(String.format("￥%s",Price));
        tv_food_sales.setText(String.format("Sales:%s",number));
        thumb.loadImage(imageLoader, imgUrl );
        //thumb_food.loadImage(imageLoader, imgUrl );

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

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
