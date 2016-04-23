package cn.trainservice.trainservice.service.model;

import android.content.Context;

import cn.trainservice.trainservice.TrainServiceApplication;

/**
 * Created by BrainWang on 4/18/2016.
 */
public class ServiceFactory {
//
//    public enum ServiceTypes {
//        movie, food;
//
//
//        public String getName() {
//            switch (this) {
//                case movie:
//                    return "Movie";
//                //  break;
//                case food:
//                    return "Food";
//                // break;
//            }
//            return "";
//        }
//
//        public String getLocaleName() {
//            switch (this) {
//                case movie:
//                    return "Movie";
//                //  break;
//                case food:
//                    return "Food";
//                // break;
//            }
//            return "";
//        }
//
//        public String getListUrl() {
//            switch (this) {
//                case movie:
//                    return "Movie";
//                //  break;
//                case food:
//                    return "Food";
//                // break;
//            }
//            return "";
//        }
//
//        public String[] getTypes() {
//            switch (this) {
//                case movie:
//                    return new String[]{"国产","国外"};
//                //  break;
//                case food:
//                    return new String[]{""};
//                // break;
//            }
//            return null;
//        }
//
//    }

    public static SingleSimpleService create(Context context, String serviceStr) {

        switch (serviceStr) {
            case "movie":
                return new MovieService(context,"Movies", TrainServiceApplication.getMovieListUrl(),null);
            //  break;
            case "food":
             //   return new MovieService(context,service.getName(),service.getListUrl(),service.getTypes());
            // break;
        }
        return null;
    }

}
