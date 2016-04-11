package cn.trainservice.trainservice.journey;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.response.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.trainservice.trainservice.R;
import cn.trainservice.trainservice.TrainServiceApplication;
import cn.trainservice.trainservice.journey.model.CityInfo;
import cn.trainservice.trainservice.journey.model.StationlistRecyclerViewAdapter;
import cn.trainservice.trainservice.journey.model.TrainStation;
import cn.trainservice.trainservice.journey.util.JsonHelper;
import cn.trainservice.trainservice.journey.view.TicketInfo;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.impl.DefaultImageLoadHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JourneyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JourneyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JourneyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public TextToSpeech speaker;
    RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    private ImageLoader imageLoader;
    private TicketInfo ticket;
    private int currentCityId = -1;

    private View view;
    private ArrayList<TrainStation> stationslist = new ArrayList<>();

    private JourneyBroadcastReceiver receiver;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StationlistRecyclerViewAdapter adapter;
    private CubeImageView thumb_city_brief;
    private CardView cardCurrentCity;

    public JourneyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JourneyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JourneyFragment newInstance(String param1, String param2) {
        JourneyFragment fragment = new JourneyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        registerReceiver();


    }

    private void registerReceiver() {
        receiver = new JourneyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(TrainServiceApplication.JourneyBroadcastAction);
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_journey, container, false);
            LinearLayout llayout = (LinearLayout) view.findViewById(R.id.journeyLlayout);
            recyclerView = (RecyclerView) view.findViewById(R.id.stationlistView);
            adapter = new StationlistRecyclerViewAdapter(stationslist, getContext());

            recyclerView.setAdapter(adapter);
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    refreshSections();

                }
            });
            TrainServiceApplication.setTickt(new TicketInfo(getContext()));
            ticket = TrainServiceApplication.getTicket();
            FrameLayout childview = ticket.getView();
            childview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TrainServiceApplication.attemptToEnterUserCenter(getActivity());
                    //    context.startActivity(new Intent(context, LoginActivity.class));
                }
            });
            llayout.addView(childview, 0);

             cardCurrentCity = (CardView) view.findViewById(R.id.card_current_city);
            //cardCurrentCity.setEnabled(false);
            final String mName = "Xi'an";
            String mIntroduce = "";
            final String mImageUrl =  "http://www.wzljl.cn/images/attachement/jpg/site2/20100407/001c25db23ed0d269b7a30.jpg";
            final String mAllIntroduceUrl = "https://en.m.wikipedia.org/wiki/Xi%27an";

            cardCurrentCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(getActivity(), CityDetailActivity.class);
                    intent.putExtra("url",mAllIntroduceUrl);
                    intent.putExtra("cityName",mName);
                    intent.putExtra("imgUrl",mImageUrl);
                    startActivity(intent);
                }
            });
            thumb_city_brief = (CubeImageView) view.findViewById(R.id.thumb_city_brief);
            DefaultImageLoadHandler handler = new DefaultImageLoadHandler(getActivity());
            handler.setLoadingResources(R.mipmap.loading);
            imageLoader = ImageLoaderFactory.create(getActivity(), handler);


        }
        if (speaker == null) {
            speaker = new TextToSpeech(getContext(), null);
//            new TextToSpeech.OnInitListener() {
//                @Override
//                public void onInit(int status) {
//                    //如果装载TTS引擎成功
//                    if (status == TextToSpeech.SUCCESS) {
//                        //设置使用美式英语朗读(虽然设置里有中文选项Locale.Chinese,但并不支持中文)
//                        int result = speaker.setLanguage(Locale.CHINESE);
//                        //如果不支持设置的语言
//                        if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
//                                && result != TextToSpeech.LANG_AVAILABLE) {
//                            Toast.makeText(getContext(), "TTS暂时不支持这种语言朗读", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
        }


        refreshSections();
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


//    public void loadTicketInfo(){
//
//    }

    /**
     * 刷新站点列表、城市介绍板块
     */

    public void refreshStations() {
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        new Thread(new Runnable() {

            //请求站点列表信息
            @Override
            public void run() {
                String url = TrainServiceApplication.getStationListUrl();
                TrainServiceApplication.getLiteHttp(getContext()).execute(new StringRequest(url).setHttpListener(
                        new HttpListener<String>() {
                            @Override
                            public void onSuccess(String data, Response<String> response) {
                                //填充到stationslist

                                try {
                                    JSONObject Json = new JSONObject(data);
                                    boolean result = Json.getBoolean("result");
                                    if (result) {
                                        stationslist = JsonHelper.parseStationList(data);
                                        adapter.loadStation(stationslist);
                                        adapter.notifyDataSetChanged();
                                    } else {

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onFailure(HttpException e, Response<String> response) {
                                Log.i("httpp", e.toString());
                            }
                        }
                ));
            }
        }).start();
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void refreshSections() {

        refreshStations();
        refreshCurrentcity();

    }


    public void refreshCurrentcity() {
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        new Thread(new Runnable() {

            //请求站点列表信息
            @Override
            public void run() {
                final String url = TrainServiceApplication.getStationListUrl();
                TrainServiceApplication.getLiteHttp(getContext()).execute(new StringRequest(url).setHttpListener(
                        new HttpListener<String>() {
                            @Override
                            public void onSuccess(String data, Response<String> response) {
                                String mId = "";
                                final String mName = "Xi'an";
                                String mIntroduce = "";
                                final String mImageUrl =  "http://www.wzljl.cn/images/attachement/jpg/site2/20100407/001c25db23ed0d269b7a30.jpg";
                                final String mAllIntroduceUrl = "https://en.m.wikipedia.org/wiki/Xi%27an";
                                String mAddress = "";
                                thumb_city_brief.loadImage(imageLoader,mImageUrl);
                                CityInfo.loadCurrentCityData(mId, mName, mIntroduce, mImageUrl,
                                        mAllIntroduceUrl, mAddress);//填充当前城市信息
                               // cardCurrentCity.setEnabled(true);
                                cardCurrentCity.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent=new Intent(getActivity(), CityDetailActivity.class);
                                        intent.putExtra("url",mAllIntroduceUrl);
                                        intent.putExtra("cityName",mName);
                                        intent.putExtra("imgUrl",mImageUrl);
                                        startActivity(intent);
                                    }
                                });

                            }

                            @Override
                            public void onFailure(HttpException e, Response<String> response) {
                                Log.i("httpp", e.toString());
                            }
                        }
                ));
            }
        }).start();
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(receiver);
        speaker.shutdown();
        super.onDestroy();
    }

    //private
    class JourneyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int stationId = intent.getIntExtra("stationId", -1);
            String stationName = intent.getStringExtra("stationName");
            String nextStation = intent.getStringExtra("nextStation");
            if (currentCityId != stationId) {
                refreshSections();
                speaker.speak("Dear passengers,Here is " + stationName + "  Railway Station , the next one is " + nextStation, TextToSpeech.QUEUE_ADD, null);

            }

        }
    }


}
