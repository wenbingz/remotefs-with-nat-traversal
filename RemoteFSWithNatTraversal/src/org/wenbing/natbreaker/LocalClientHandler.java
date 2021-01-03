package org.wenbing.natbreaker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class LocalClientHandler {
    public LocalClientHandler start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverSocket.bind(new InetSocketAddress("0.0.0.0", Config.clientPort));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverSocket.setReuseAddress(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (Config.class) {
                if (Config.curServerAddr == null) {
                    try {
                        clientSocket.getOutputStream().write(new String("err").getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        clientSocket.getOutputStream().write((Config.curServerAddr + ":" + Config.curServerPort).getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String curClientAddr = clientSocket.getInetAddress().getHostAddress();
                    int curClientPort = clientSocket.getPort();
                    try {
                        RemoteServerHandler.remoteServer.getOutputStream().write((curClientAddr + ":" + curClientPort).getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        RemoteServerHandler.remoteServer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 

            }
            break;
        }
        return this;
    }
}
