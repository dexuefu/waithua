package org.waithua.common.just4fun.testSocket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jch on 18/3/29.
 */
public class SocketServer {
    public static void main(String[] args) throws Exception {
        // 监听指定的端口
        int port = 55533;
        ServerSocket server = new ServerSocket(port);

        // server将一直等待连接的到来
        System.out.println("server将一直等待连接的到来");
        Socket socket = server.accept();
        // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
//        while (true) {
        String input = null;
        while (!"bye".equalsIgnoreCase(input = reader.readLine())) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
//                sb.append(new String(bytes, 0, len,"UTF-8"));
                System.out.println("get message from client: " + input);
            }
//            if (sb.indexOf("bye") > -1) {
//                break;
//            }
//        }
        System.out.println("get message from client: " + input);
        inputStream.close();
        socket.close();
        server.close();
    }
}
