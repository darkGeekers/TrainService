package cn.trainservice.trainservice.service.Chat;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by 旭 on 2016/4/16.
 */

public class MessageManager {
    public static List<FriendAdapter.Friend> lists;
    private  Map<String, ConcurrentLinkedQueue<String>> message;
    private  Map<String, String> userClient;
    private static MessageManager mag = null;

    private MessageManager() {
        message = new HashMap<>();
        userClient = new HashMap<>();
    }

    public static MessageManager getMessageManager() {
        if (mag == null)
            mag = new MessageManager();
        return mag;
    }


    public String getClient(String user_id) {
        return userClient.get(user_id);
    }

    public void addMsg(String msg) {
        String user_id = null;
        try {
            JSONObject json = new JSONObject(msg);
            user_id = json.getString("user_id");
            String info = json.getString("info");
            addMsg(user_id, info);

        } catch (JSONException je) {
            je.printStackTrace();

        }
    }

    private void addMsg(String user_id, String msg) {
       if(!messageArriverList.containsKey(user_id))
       { Log.d("data1","no listener");
            if (message.get(user_id) == null) {
                ConcurrentLinkedQueue<String> msgLists = new ConcurrentLinkedQueue<>();
                msgLists.add(msg);
                message.put(user_id, msgLists);
            } else {
                message.get(user_id).add(msg);
            }
        } else {
            Log.d("data1","listener");
           if( messageArriverList.get(user_id).messageReach(user_id ,msg )){
               Log.d("data1","listener error");
               if (message.get(user_id) == null) {
                   ConcurrentLinkedQueue<String> msgLists = new ConcurrentLinkedQueue<>();
                   msgLists.add(msg);
                   message.put(user_id, msgLists);
               } else {
                   message.get(user_id).add(msg);
               }
           }

        }

    }

    public List<String> getUserMsg(String user_id) {
        List<String> lists = new ArrayList<>();
        ConcurrentLinkedQueue<String> queue = message.get(user_id);
        String msg = null;
        if(queue!=null){
            while((msg=queue.poll())!=null)
            {
                lists.add(msg);
            }
        }

        return  lists;
    }

    public String getMsg(String user_id) {
        String msg = null;
        if (message.get(user_id) == null) {
            msg = null;
        } else {
            msg = message.get(user_id).poll();
        }
        return msg;
    }

/*
* activity 需要实现 MessageArriverListener ，用来接受呀返回的数据
* 还需要重载 activity 的 onPause() ,onStop(),onDestory
 * ,onStart ,onRuseme(),onCreate()
* 改变 MessageArriverListener.ListenserStatus
* 的状态值
*
*
* */
    interface MessageArriverListener {///需要实现的监听
        boolean ListenserStatus = false;
        boolean messageReach(String user_id ,String msg);
    }

    private Map<String, MessageArriverListener> messageArriverList = new HashMap<>();

    public void registerListener(String user_id, MessageArriverListener listenr) {
        messageArriverList.put(user_id, listenr);
    }

    public void unregisterListener(String user_id) {
        messageArriverList.remove(user_id);
    }
}
