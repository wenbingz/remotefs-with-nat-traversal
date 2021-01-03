package org.wenbing.remoteserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CompletableFuture;

public class LocalClientRegister {
    public static void main(String[] args) {
        while (true) {
            Socket socket = new Socket();
            try {
                socket.setReuseAddress(true);
                socket.bind(new InetSocketAddress(Config.port));
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.connect(new InetSocketAddress(Config.natBreakerHost, Config.serverPort));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(socket.getLocalPort());
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String client = null;
            try {
                client = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String clientAddr = client.split(":")[0];
            String clientPort = client.split(":")[1];
            /*try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            socket = new Socket();
            try {
                socket.setReuseAddress(true);
                System.out.println("try to register " + clientAddr + ":" + clientPort);
                socket.bind(new InetSocketAddress(Config.port));
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.connect(new InetSocketAddress(clientAddr, Integer.parseInt(clientPort)));
            } catch (IOException e) {
                System.out.println("failed as expected");
                e.printStackTrace();
            }/* finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            RemoteServer remoteServer = new RemoteServer();
            remoteServer.start();
            try {
                remoteServer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
