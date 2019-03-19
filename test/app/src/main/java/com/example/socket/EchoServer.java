package com.example.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by qiaopc on 2019/2/25.
 */

public class EchoServer {
    private final ServerSocket mServerSocket;

    public EchoServer(int port) throws IOException {
        //1.创建一个ServerSocket并坚挺端口port
        this.mServerSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        //2.开始接受客户连接
        Socket client = mServerSocket.accept();
        handleClient(client);
    }

    private void handleClient(Socket socket) throws IOException {
        //3.使用socket进行通信...
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = in.read(buffer)) > 0) {
            out.write(buffer, 0, n);
        }
    }
    public static void main(String[] args){
        try {
            EchoServer server = new EchoServer(9877);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
