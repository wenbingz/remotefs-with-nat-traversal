package org.wenbing.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class test2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket s = new Socket();
        s.setReuseAddress(true);
        s.bind(new InetSocketAddress(12345));
        s.connect(new InetSocketAddress("localhost", 12345));
        System.out.println("succ");
        Thread.sleep(10000);
    }
}
