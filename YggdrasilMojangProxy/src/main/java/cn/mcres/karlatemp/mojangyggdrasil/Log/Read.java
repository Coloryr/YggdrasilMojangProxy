package cn.mcres.karlatemp.mojangyggdrasil.Log;

import cn.mcres.karlatemp.mojangyggdrasil.Config.MainConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Config.PlayerConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.SocketObj;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Read {
    final ServerSocket server = new ServerSocket(123);
    final Thread thread = new Thread(this::ReadThread);

    public Read() throws IOException {
        Loggin.boot.info("控制端口启动：" + server);
        thread.start();
    }

    private void ReadThread() {

        while (true) {
            try {
                Socket socket = server.accept();
                Loggin.boot.info("控制端口连接：" + socket);
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String str = in.readLine();
                    SocketObj obj = new Gson().fromJson(str, SocketObj.class);
                    if (obj.getDo().equalsIgnoreCase("reload")) {
                        MainConfig.loadconfig();
                        Loggin.boot.info("已重读配置文件");
                    } else if (obj.getDo().equalsIgnoreCase("AddBanID")) {
                        if (!obj.getID().isEmpty()) {
                            PlayerConfig.AddBanID(obj.getID());
                            Loggin.boot.info("已禁封ID：" + obj.getID());
                        } else if (!obj.getUUID().isEmpty()) {
                            PlayerConfig.AddBanUUID(obj.getUUID());
                            Loggin.boot.info("已禁封UUID：" + obj.getUUID().replaceAll("-", ""));
                        } else {
                            Loggin.boot.info("无法添加空ID");
                        }
                    } else if (obj.getDo().equalsIgnoreCase("SetPlayer")) {
                        if (obj.getID().isEmpty() && obj.getUUID().isEmpty()) {
                            Loggin.boot.info("无法修改空ID");
                        } else {
                            PlayerConfig.AddPlayer(obj.getID(), obj.getUUID().replaceAll("-", ""));
                            Loggin.boot.info("已设置" + obj.getID() + "的UUID为：" + obj.getUUID());
                        }
                    } else if (obj.getDo().equalsIgnoreCase("ReSkin")) {
                        if (obj.getID().isEmpty()) {
                            Loggin.boot.info("刷新刷新皮肤失败");
                        } else {
                            PlayerConfig.RemoveSkin(obj.getID());
                            String temp = "刷新" + obj.getID() + "刷新皮肤成功，重进服务器后生效";
                            Loggin.boot.info(temp);
                            OutputStream out = socket.getOutputStream();
                            out.write(temp.getBytes(StandardCharsets.UTF_8));
                            out.flush();
                            out.close();
                        }
                    }
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
