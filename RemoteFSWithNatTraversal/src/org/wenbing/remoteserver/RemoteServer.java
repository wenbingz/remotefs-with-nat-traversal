package org.wenbing.remoteserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class RemoteServer extends Thread {
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverSocket.setReuseAddress(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            serverSocket.bind(new InetSocketAddress(Config.port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Socket localClient = null;
            try {
                System.out.println("waiting to accept");
                localClient = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                localClient.setReuseAddress(true);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            new LocalClientHandler(localClient).start();
        }
    }
}
