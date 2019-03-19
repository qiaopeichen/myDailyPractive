package com.example.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by qiaopc on 2019/2/25.
 */

public class EchoClient {
    private final Socket mScoket;
    public EchoClient(String host, int port) throws IOException {
        mScoket = new Socket(host, port);
    }

    public void run() throws IOException {
        // 和服务端进行通信
        final Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                readResponse();
            }
        });
        readerThread.start();

        OutputStream out = mScoket.getOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = System.in.read(buffer)) > 0) {
            out.write(buffer, 0, n);
        }
    }

    private void readResponse() {
        try {
            InputStream in = mScoket.getInputStream();
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) > 0) {
                System.out.write(buffer,0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // 由于服务端运行在同一主机，这里我们使用localhost
            EchoClient client = new EchoClient("localhost", 9877);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
