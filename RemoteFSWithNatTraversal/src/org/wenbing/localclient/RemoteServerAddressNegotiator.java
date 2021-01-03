package org.wenbing.localclient;

import org.wenbing.remoteserver.LocalClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class RemoteServerAddressNegotiator {
    public static void main(String[] args) throws SocketException {
        Socket socket = new Socket();
        socket.setReuseAddress(true);
        try {
            socket.bind(new InetSocketAddress(Config.localPort));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.connect(new InetSocketAddress(Config.natBreakerHost, Config.natBreakerPort));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (Config.class) {
            Config.curServerAddr = line.split(":")[0];
            Config.curServerPort = Integer.parseInt(line.split(":")[1]);
        }
        /*try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalClient client = new LocalClient();
        client.start();
    }
}
