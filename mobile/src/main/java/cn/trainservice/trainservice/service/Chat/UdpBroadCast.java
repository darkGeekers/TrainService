package cn.trainservice.trainservice.service.Chat;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by 旭 on 2016/4/16.
 */
public class UdpBroadCast extends Thread {
    private static String BROADCAST_IP = "230.0.0.1";
    private static int PORT = 8888;

    public void run() {
        try {
            InetAddress inetRemoteAddr = InetAddress
                    .getByName(BROADCAST_IP);
            MulticastSocket socket = new MulticastSocket();
            socket.joinGroup(inetRemoteAddr);

            while (true) {

                String msg = "cmd:1--"+User.user_id+","+User.user_name;
                Log.d("data1" ,"send" + msg);
                if(User.user_id!=null){
                    DatagramPacket packet = new DatagramPacket(msg.getBytes(), 0, msg.length(), inetRemoteAddr, PORT);
                    //socket.setBroadcast(true);
                    socket.send(packet);
                }

                Thread.sleep(30 * 1000);
            }
        } catch (UnknownHostException un) {
            un.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ine) {
            ine.printStackTrace();
        }


    }
}
