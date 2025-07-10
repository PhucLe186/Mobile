package com.example.projectmobile;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketConfig {
    private static Socket socket;

    public static Socket getSocket(String token) {
        if (socket == null) {
            try {
                IO.Options opts = new IO.Options();
                opts.query = "token=" + token;
                socket = IO.socket("http://10.0.2.2:5000", opts);
                socket.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return socket;
    }
    public static void disconnectSocket() {
        if (socket != null) {
            socket.disconnect();
            socket = null;
        }
    }
}
