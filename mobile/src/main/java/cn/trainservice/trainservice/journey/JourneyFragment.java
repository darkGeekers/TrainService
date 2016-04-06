package cn.trainservice.trainservice.journey;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.trainservice.trainservice.R;
import cn.trainservice.trainservice.journey.model.ViewModel;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.ImageProvider;
import in.srain.cube.image.impl.DefaultImageLoadHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JourneyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JourneyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JourneyFragment extends Fragment implements JourneyViewModelInterface{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ImageLoader imageLoader;

    private ViewModel model;
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
        model=new ViewModel(this);
        bindViewModel(model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journey, container, false);
        CubeImageView thumb_city_brief = (CubeImageView) view.findViewById(R.id.thumb_city_brief);
        DefaultImageLoadHandler handler = new DefaultImageLoadHandler(getActivity());
        handler.setLoadingResources(R.mipmap.loading);
        imageLoader = ImageLoaderFactory.create(getActivity(),handler);

        thumb_city_brief.loadImage(imageLoader, "http://www.wzljl.cn/images/attachement/jpg/site2/20100407/001c25db23ed0d269b7a30.jpg");
        return view;
    }


    private void loadView(){

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

    @Override
    public void viewChange(final int section) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch(section){
                    case 1://journey用户信息部分
                        break;
                    case 2://车站列表部分
                        break;
                    case 3://城市介绍部分
                        break;
                    case 4:
                        break;
                }
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void bindViewModel(ViewModel vmodel){
        model=vmodel;
    }
}
