package org.wenbing.natbreaker;

import java.io.IOException;

public class NatBreaker {
    public static void main(String[] args) throws IOException, InterruptedException {
        RemoteServerHandler remoteServerHandler = new RemoteServerHandler().start();
        LocalClientHandler localClientHandler = new LocalClientHandler().start();
        Thread.sleep(3000);
    }
}
