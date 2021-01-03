package org.wenbing.remoteserver;

import java.io.IOException;
import java.net.Socket;

public class LocalClientHandler extends Thread {
    private Socket socket = null;
    public LocalClientHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        System.out.println("get it " + socket.getInetAddress().getHostName() + " " + socket.getPort());
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
