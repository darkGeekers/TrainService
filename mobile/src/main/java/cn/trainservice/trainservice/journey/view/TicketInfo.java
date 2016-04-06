package cn.trainservice.trainservice.journey.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.trainservice.trainservice.R;

/**
 * Created by BrainWang on 2016/4/6.
 */
public class TicketInfo  {
    private Context context;
    public String userName="",ID="", fromTo="";

    public TicketInfo (Context context,String userName,String ID,String fromTo){
        this.context=context;
        this.userName=userName;
        this.ID=ID;
        this.fromTo=fromTo;
    }
    public FrameLayout getView (){
        FrameLayout view= (FrameLayout) LayoutInflater.from(context).inflate(R.layout.journey_ticket_info,null);


        TextView tv_ticket_userName=(TextView)view.findViewById(R.id.tv_ticket_userName);
        TextView tv_ticket_ID=(TextView)view.findViewById(R.id.tv_ticket_ID);
        TextView tv_ticket_FromTo=(TextView)view.findViewById(R.id.tv_ticket_FromTo);

        tv_ticket_userName.setText(userName);
        tv_ticket_ID.setText(ID);
        tv_ticket_FromTo.setText(fromTo);

        //view.setLayoutParams(new ViewGroup.LayoutParams());
        return view;
    }


}
