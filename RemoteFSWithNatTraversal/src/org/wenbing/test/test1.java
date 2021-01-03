package org.wenbing.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class test1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress("0.0.0.0", 12345));
        while (true) {
            Socket s = serverSocket.accept();

        }
    }
}
