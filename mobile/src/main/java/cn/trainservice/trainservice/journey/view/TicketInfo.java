package cn.trainservice.trainservice.journey.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.trainservice.trainservice.LoginActivity;
import cn.trainservice.trainservice.R;
import cn.trainservice.trainservice.TrainServiceApplication;

/**
 * Created by BrainWang on 2016/4/6.
 */
public class TicketInfo  {
    public Context context;
    public String userName="",ID="",from="",To="",ticketName="";

    public enum TicketTypes{
        Student{
            public String toLocaleString(Context icontext){
                return icontext.getString(R.string.student_ticket);
            }
        },
        Adult{
            public String toLocaleString(Context icontext){
                return icontext.getString(R.string.adult_ticket);
            }

        }


    }
    public enum SeatTypes{
        Berth{
            public String toLocaleString(Context icontext){
                return icontext.getString(R.string.SeatBerth);
            }
        },
        HardSeat{
            public String toLocaleString(Context icontext){
                return icontext.getString(R.string.SeatHardSeat);
            }

        }


    }

    public TicketTypes ticketType=TicketTypes.Student;
    public SeatTypes seatType=SeatTypes.HardSeat;
    public TicketInfo (Context context){
        this.context=context;
    }
    public TicketInfo (Context context,String ticketName,String userName,String ID,String from,String To){
        this.context=context;
        this.ticketName=ticketName;
        this.userName=userName;
        this.ID=ID;
        this.from=from;
        this.To=To;
    }
    public FrameLayout getView (){
        FrameLayout view= (FrameLayout) LayoutInflater.from(context).inflate(R.layout.journey_ticket_info,null);
        FrameLayout containerview=(FrameLayout) view.findViewById(R.id.ticket_info_person);
        FrameLayout childview;
        if(TrainServiceApplication.hasLogin){
            childview = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.journey_ticket_info_login,null);
            TextView tv_ticket_userName=(TextView)view.findViewById(R.id.tv_ticket_userName);
            TextView tv_ticket_ID=(TextView)view.findViewById(R.id.tv_ticket_ID);
            // TextView tv_ticket_FromTo=(TextView)view.findViewById(R.id.tv_ticket_FromTo);
            tv_ticket_userName.setText(userName);
            tv_ticket_ID.setText(ID);
            // tv_ticket_FromTo.setText(fromTo);
        }else{
            childview= (FrameLayout) LayoutInflater.from(context).inflate(R.layout.journey_ticket_info_notlogin,null);
        }
        containerview.removeAllViews();
        containerview.addView(childview);

        //view.setLayoutParams(new ViewGroup.LayoutParams());
        return view;
    }


}
