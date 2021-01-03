package org.wenbing.natbreaker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class RemoteServerHandler {
    public static Socket remoteServer = null;
    public RemoteServerHandler start() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress("0.0.0.0", Config.serverPort));

        while (true) {
            remoteServer = serverSocket.accept();
            remoteServer.setReuseAddress(true);
            synchronized (Config.class) {
                Config.curServerAddr = remoteServer.getInetAddress().getHostAddress();
                Config.curServerPort = remoteServer.getPort();
                System.out.println("remote server addr registered as " + Config.curServerAddr + ":" + Config.curServerPort);
            }
            break;
        }
        return this;
    }
}
