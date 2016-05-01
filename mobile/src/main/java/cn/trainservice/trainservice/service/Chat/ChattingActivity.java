package cn.trainservice.trainservice.service.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.litesuits.http.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.trainservice.trainservice.R;
import cn.trainservice.trainservice.TrainServiceApplication;
import cn.trainservice.trainservice.journey.view.TicketInfo;
import cn.trainservice.trainservice.view.IconTextView;

public class ChattingActivity extends AppCompatActivity implements MessageManager.MessageArriverListener {


    private Button send;
    private EditText editText;
    private ScrollView scrollView;
    private static String user_id;
    private static String ip;
    private Toolbar toolbar;
    private TextView chat_title;

    private List<MessageManager.MyMessage> lists = new ArrayList<>();

    private LinearLayout content;
    private MessageManager mag = MessageManager.getMessageManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        Log.d("data1", "user_id" + user_id);
        chat_title = (TextView) findViewById(R.id.chat_title);
        chat_title.setText(intent.getStringExtra("name"));
        ip = intent.getStringExtra("ip");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        content = (LinearLayout) findViewById(R.id.content);
        scrollView = (ScrollView) findViewById(R.id.chat_cont);
        send = (Button) findViewById(R.id.send_button);

        IconTextView send_changeSeat = (IconTextView) findViewById(R.id.send_changeSeat);

        editText = (EditText) findViewById(R.id.input);

        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadLog();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (!text.equals("")) {

                    if (MessageManager.ip_list.containsKey(user_id))
                        MessageManager.ip_list.get(user_id).last_msg = text;

                    Log.d("data1", "text :" + text);
                    MessageManager.MyMessage msg = new MessageManager.MyMessage(User.user_id, 1, text, 0);
                    mag.insertUserMsgLog(user_id, msg);
                    new ClientThread(ip, msg).start();
                    editText.setText("");
                    content.addView(new MesView(msg).getMsgView());
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);

                }
            }
        });

        send_changeSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageManager.MyMessage msg = new MessageManager.MyMessage(User.user_id, 1, "", 1);
                new ClientThread(ip, msg).start();
                mag.insertUserMsgLog(user_id, msg);
                content.addView(new MesView(msg).getMsgView());
            }
        });
    }

    public void loadLog() {
        Log.d("data1", "log");
        List<MessageManager.MyMessage> list = mag.getUserMsgLog(user_id);
        for (int i = 0, size = list.size(); i < size; i++) {
            content.addView(new MesView(list.get(i)).getMsgView());
            Log.d("data1", "log");
        }
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    public void initList() {

        List<MessageManager.MyMessage> list = mag.getUserMsg(user_id);
        for (int i = 0, size = list.size(); i < size; i++) {
            MessageManager.MyMessage msg = list.get(i);
            mag.insertUserMsgLog(msg.user_id, msg);
            content.addView(new MesView(msg).getMsgView());
        }
        //   content.addView(new MesView(new MessageManager.MyMessage("0",0,"CHENXU" ,0)).getMsgView());
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);

    }

    @Override
    public void onResume() {
        initList();
        mag.registerListener(user_id, this);
        super.onResume();
    }

    @Override
    public void onStop() {
        mag.unregisterListener(user_id);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mag.unregisterListener(user_id);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        mag.unregisterListener(user_id);
        super.onPause();
    }


    @Override
    public boolean messageReach(MessageManager.MyMessage msg) {

        if ((msg.user_id).equals(ChattingActivity.this.user_id)) {
            mag.insertUserMsgLog(user_id, msg);
            ChattingActivity.this.runOnUiThread(new upDateUi(msg));
            MessageManager.ip_list.get(user_id).last_msg = msg.info;
            return true;
        } else {
            return false;
        }
    }


    class upDateUi implements Runnable {
        private MessageManager.MyMessage msg;

        public upDateUi(MessageManager.MyMessage msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            ChattingActivity.this.content.addView(new MesView(msg).getMsgView());
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    private void agreeChangeSeat() {
        Log.d("data1", "user_id" + User.user_id + user_id);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uploadUrl = TrainServiceApplication.changeSeat();
                final StringRequest postRequest = new StringRequest(uploadUrl)
                        .setMethod(HttpMethods.Post)
                        .setHttpListener(new HttpListener<String>(true, false, true) {
                            @Override
                            public void onStart(AbstractRequest<String> request) {
                                super.onStart(request);
                            }

                            @Override
                            public void onUploading(AbstractRequest<String> request, long total, long len) {

                            }

                            @Override
                            public void onEnd(Response<String> response) {
                                if (response.isConnectSuccess()) {
                                    String jsonstr = response.getResult();
                                    try {
                                        JSONObject js = new JSONObject(jsonstr);
                                        if (js.has("result")) {

                                            Log.d("data", "change" + response.getResult());
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    HttpUtil.showTips(ChattingActivity.this, "Upload Failure", response.getException() + "");
                                }
                            }
                        });

                LinkedList<NameValuePair> pList = new LinkedList<>();
                pList.add(new NameValuePair("user_first", User.user_id));
                pList.add(new NameValuePair("user_second", user_id));

                postRequest.setHttpBody(new UrlEncodedFormBody(pList));
                TrainServiceApplication.getLiteHttp(ChattingActivity.this).executeAsync(postRequest);
            }
        }).start();
    }

    class MesView {

        public TextView left_tv;
        public TextView right_tv;
        public cyimageview left_image;
        public cyimageview right_image;
        public RelativeLayout left_view;
        public RelativeLayout right_view;
        private MessageManager.MyMessage msg;

        public MesView(MessageManager.MyMessage msg) {
            this.msg = msg;

        }

        public View getMsgView() {
            View view = null;
            switch (msg.m_type) {
                case 0:
                    view = LayoutInflater.from(ChattingActivity.this).inflate(R.layout.chat_content_view, null);
                    left_view = (RelativeLayout) view.findViewById(R.id.left_view);
                    right_view = (RelativeLayout) view.findViewById(R.id.right_view);
                    this.left_tv = (TextView) view.findViewById(R.id.left_content);
                    this.left_image = (cyimageview) view.findViewById(R.id.left_image);
                    this.right_tv = (TextView) view.findViewById(R.id.right_content);
                    this.right_image = (cyimageview) view.findViewById(R.id.right_image);
                    if (msg.m_from == 0) {
                        left_tv.setText(msg.info);
                        right_image.setVisibility(View.INVISIBLE);
                        right_tv.setVisibility(View.INVISIBLE);
                        right_view.setVisibility(View.INVISIBLE);
                    } else {
                        right_tv.setText(msg.info);
                        left_image.setVisibility(View.INVISIBLE);
                        left_tv.setVisibility(View.INVISIBLE);
                        left_view.setVisibility(View.INVISIBLE);
                    }
                    break;
                case 1:
                    if (msg.m_from == 0) {
                        view = LayoutInflater.from(ChattingActivity.this).inflate(R.layout.chat_change_seat_view, null);
                        BootstrapButton agree = (BootstrapButton) view.findViewById(R.id.agree);
                        BootstrapButton disagree = (BootstrapButton) view.findViewById(R.id.disagree);
                        agree.setOnClickListener(new ExchangeButtonListener());
                        disagree.setOnClickListener(new ExchangeButtonListener());
                    } else
                        view = LayoutInflater.from(ChattingActivity.this).inflate(R.layout.chat_change_requirment_view, null);

                    break;
                case 2:
                    if (msg.info.equals("agree")) {
                        view = LayoutInflater.from(ChattingActivity.this).inflate(R.layout.change_seat_agree, null);
                    } else if (msg.info.equals("disagree")) {
                        view = LayoutInflater.from(ChattingActivity.this).inflate(R.layout.change_seat_disagree, null);
                    }
                    break;
            }
            return view;
        }
    }

    class ExchangeButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.agree: {
                    MessageManager.MyMessage msg = new MessageManager.MyMessage(User.user_id, 1, "agree", 2);
                    new ClientThread(ip, msg).start();
                    mag.insertUserMsgLog(user_id, msg);
                    content.addView(new MesView(msg).getMsgView());
                    agreeChangeSeat();
                }

                break;
                case R.id.disagree: {
                    MessageManager.MyMessage msg = new MessageManager.MyMessage(User.user_id, 1, "disagree", 2);
                    new ClientThread(ip, msg).start();
                    mag.insertUserMsgLog(user_id, msg);
                    content.addView(new MesView(msg).getMsgView());
                }

                break;
                default:
                    break;
            }

        }
    }
}