package socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-25 21:57
 **/
public class Server {

    public static Map<Integer,Socket> cache = new HashMap<Integer, Socket>();
    static class MyThread extends Thread{
        private Socket conn;
        MyThread(Socket conn){
            this.conn = conn;
        }
        @Override
        public void run() {
            while (true){
                try {
                    InputStream input = conn.getInputStream();
                    int len = input.available();
                    while (len>0){
                        byte[] buff = new byte[len];
                        input.read(buff);
                        JSONObject jo = (JSONObject)JSON.parse(buff);
                        int cmd = judge(jo);
                        switch (cmd){
                            case 0 :{
                                // 1. 判断信息类型（认证、数据，通讯）
                                if(true || cache.get((Integer) jo.get("id"))!=null){
                                    // 认证成功
                                    // 缓存认证信息给Redis
                                    cache.put((Integer) jo.get("id"),this.conn);
                                    // 下发成功
                                    OutputStream out = conn.getOutputStream();
                                    out.write("success".getBytes());
                                    out.flush();
                                }else {
                                    // 断开连接
                                    conn.close();
                                }
                                break;
                            }

                            case 1:{
                                // 数据成熟,to Redis and RabbitMQ
                               dataTrans();
                               break;
                            }
                            case 2:{
                                // 控制
                                community((Integer) jo.get("id"), (String) jo.get("data"));
                                break;
                            }default:{
                                System.out.println("数据类型不支持");
                                break;
                            }

                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private int judge(JSONObject jo) {

            if(jo.get("cmd").equals(1)){
                return 1;
            }
            if(jo.get("cmd").equals(2)){
                return 2;
            }
            if(jo.get("cmd").equals(3)){
                return 3;
            }
            return 0;
        }

        private void community(Integer to,String body) throws IOException {
            // 对body解析，是配置信息，还是控制指令
            OutputStream out = cache.get(to).getOutputStream();
            out.write(body.getBytes());
            out.flush();

        }

        private void dataTrans() {
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(1234);
        while (true) {
            Socket conn = server.accept();

            MyThread t = new MyThread(conn);
            t.start();

        }

    }
}
