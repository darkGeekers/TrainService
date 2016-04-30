package cn.trainservice.trainservice.service.food;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.trainservice.trainservice.R;
import cn.trainservice.trainservice.TrainServiceApplication;
import cn.trainservice.trainservice.service.model.SimpleServiceRecyclerViewAdapter;
import cn.trainservice.trainservice.service.model.SingleSimpleService;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.impl.DefaultImageLoadHandler;

/**
 * Created by BrainWang on 4/18/2016.
 */
public class FoodService extends SingleSimpleService {

    private final ImageLoader imageLoader;
    private Context context;

    public FoodService(Context context, String name, String listUrl, String[] types) {
        super(name, listUrl, types);
        this.context = context;
        DefaultImageLoadHandler handler = new DefaultImageLoadHandler(this.context);
        handler.setLoadingResources(R.mipmap.loading);
        imageLoader = ImageLoaderFactory.create(this.context, handler);
    }


    @Override
    public SimpleServiceRecyclerViewAdapter getViewAdapter() {
        return new foodListAdapter(context, this.mListUrl);
    }

    @Override
    public Drawable getThemeColor() {
        return context.getResources().getDrawable(R.color.service_dining_main);
    }

    @Override
    public void jumpToDetail() {

    }


    public class foodListAdapter extends SimpleServiceRecyclerViewAdapter {
        // private ArrayList<ServiceItem> mMovies =new ArrayList<>();
        public foodListAdapter(Context context, String url) {
            super(context, url);

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fooditem_list_content, parent, false);
            return new FoodViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
           // final String introduce =(String) mServiceItems.get(position).getExtraByKey("FIntroduction");
            final String title =(String) mServiceItems.get(position).getExtraByKey("CName");
            final String imgUrl=TrainServiceApplication.getMovieImagePath((String) mServiceItems.get(position).getExtraByKey("image_url"));
            final String Category=(String) mServiceItems.get(position).getExtraByKey("Category");
            final String number=(String) mServiceItems.get(position).getExtraByKey("number");
            final String Price=(String) mServiceItems.get(position).getExtraByKey("Price");
            //final String genres=(String) mServiceItems.get(position).getExtraByKey("FRate");
            final String sourceUrl=TrainServiceApplication.getMoviePath((String) mServiceItems.get(position).getExtraByKey("source_url"));
            FoodViewHolder mvHolder = (FoodViewHolder) holder;
            mvHolder.thumb.loadImage(imageLoader, imgUrl );

            mvHolder.tv_food_list_title.setText(title);
            mvHolder.tv_food_price.setText(String.format("￥%s",Price));
            mvHolder.tv_food_sales.setText(String.format("Sales:%s",number));

            mvHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, FoodDetailActivity.class);

                    intent.putExtra("Category",Category);
                    intent.putExtra("number",number);
                    intent.putExtra("imgUrl",imgUrl);
                    intent.putExtra("title",title);
                    intent.putExtra("sourceUrl",sourceUrl);
                    intent.putExtra("Price",Price);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mServiceItems.size();
        }

        public class FoodViewHolder extends ViewHolder {
            final View view;
            final CubeImageView thumb;
            final TextView tv_food_list_title,tv_food_sales,tv_food_price;

            public FoodViewHolder(View itemView) {
                super(itemView);
                view=itemView;
                thumb = (CubeImageView) itemView.findViewById(R.id.thumb_food);
                tv_food_price = (TextView) itemView.findViewById(R.id.tv_food_price);
                tv_food_sales = (TextView) itemView.findViewById(R.id.tv_food_sales);
                tv_food_list_title= (TextView) itemView.findViewById(R.id.tv_food_list_title);

            }
        }

    }

}
