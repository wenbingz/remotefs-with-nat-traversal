package org.wenbing.localclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class LocalClient {
    public void start() {
        Socket socket = new Socket();
        try {
            socket.setReuseAddress(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            socket.bind(new InetSocketAddress(Config.localPort));
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (Config.class) {
            int count = 0;
            while (count < Config.MAX_TRAILS) {
                boolean moved = true;
                try {
                    System.out.println(Config.curServerAddr + ":" + Config.curServerPort);
                    socket.connect(new InetSocketAddress(Config.curServerAddr, Config.curServerPort));
                } catch (IOException e) {
                    moved = false;
                    e.printStackTrace();
                }
                if (moved) {
                    break;
                }
                ++ count;
            }
        }
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
