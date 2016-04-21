package cn.trainservice.trainservice.journey.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapLabel;

import cn.trainservice.trainservice.R;
import cn.trainservice.trainservice.TrainServiceApplication;

/**
 * Created by BrainWang on 2016/4/6.
 */
public class TicketInfo {
    public Context context;
    public String userName = "", ID = "", from = "--", To = "--", ticketName = "";

    public enum TicketTypes {
        Student, Adult;

        public String toLocaleString(Context icontext) {
            switch (this){
                case Student:
                    return icontext.getString(R.string.student_ticket);
                case Adult:
                    return icontext.getString(R.string.adult_ticket);
            }
            return icontext.getString(R.string.adult_ticket);
        }


    }

    public enum SeatTypes {
        Berth ,HardSeat ;

            public String toLocaleString(Context icontext) {
                switch(this){
                    case Berth:
                    return icontext.getString(R.string.SeatBerth);
                    case HardSeat:
                        return icontext.getString(R.string.SeatHardSeat);
                }
                return icontext.getString(R.string.SeatHardSeat);
            }


    }

    public TicketTypes ticketType = TicketTypes.Student;
    public SeatTypes seatType = SeatTypes.HardSeat;

    public TicketInfo(Context context) {
        this.context = context;
    }

    public TicketInfo(Context context, String ticketName, String userName, String ID, String from, String To) {
        this.context = context;
        this.ticketName = ticketName;
        this.userName = userName;
        this.ID = ID;
        this.from = from;
        this.To = To;
    }

    public FrameLayout getView() {
        FrameLayout view = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.journey_ticket_info, null);
        FrameLayout containerview = (FrameLayout) view.findViewById(R.id.ticket_info_person);
        FrameLayout childview;
        if (TrainServiceApplication.hasLogin) {
            childview = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.journey_ticket_info_login, null);
            TextView tv_ticket_userName = (TextView) childview.findViewById(R.id.tv_ticket_userName);
            TextView tv_ticket_ID = (TextView) childview.findViewById(R.id.tv_ticket_ID);
            TextView tv_ticket_start = (TextView) childview.findViewById(R.id.tv_ticket_start);
            TextView tv_ticket_end = (TextView) childview.findViewById(R.id.tv_ticket_end);
            BootstrapLabel tv_seat_user_type = (BootstrapLabel) childview.findViewById(R.id.tv_seat_user_type);
            BootstrapLabel tv_seat_type = (BootstrapLabel) childview.findViewById(R.id.tv_seat_type);

            tv_ticket_userName.setText(userName);
            tv_ticket_ID.setText(ID);
            tv_ticket_start.setText(from);
            tv_ticket_end.setText(To);
            tv_seat_user_type.setText(ticketType.toLocaleString(context));
            tv_seat_type.setText(seatType.toLocaleString(context));
        } else {
            childview = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.journey_ticket_info_notlogin, null);
        }
        containerview.removeAllViews();
        containerview.addView(childview);

        //view.setLayoutParams(new ViewGroup.LayoutParams());
        return view;
    }


}
