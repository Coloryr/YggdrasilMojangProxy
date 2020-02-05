package cn.mcres.karlatemp.mojangyggdrasil.Log;

import cn.mcres.karlatemp.mojangyggdrasil.Config.MainConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Config.PlayerConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Main;
import cn.mcres.karlatemp.mojangyggdrasil.Obj_save.Properties;
import cn.mcres.karlatemp.mojangyggdrasil.Obj_save.SocketObj;
import cn.mcres.karlatemp.mojangyggdrasil.Obj_save.TestObj;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Read {
    final ServerSocket server = new ServerSocket(123);
    final Thread thread = new Thread(this::ReadThread);

    private void ReadThread() {

        while (true) {
            try {
                Socket socket = server.accept();
                Loggin.bungee.info("控制端口连接：" + socket);
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String str = in.readLine();
                    SocketObj obj = new Gson().fromJson(str, SocketObj.class);
                    if (obj.getDo().equalsIgnoreCase("reload")) {
                        MainConfig.loadconfig();
                        Loggin.bungee.info("已重读配置文件");
                    } else if (obj.getDo().equalsIgnoreCase("AddBanID")) {
                        if (!obj.getID().isEmpty()) {
                            PlayerConfig.AddBanID(obj.getID());
                            Loggin.bungee.info("已禁封ID：" + obj.getID());
                        } else if (!obj.getUUID().isEmpty()) {
                            PlayerConfig.AddBanUUID(obj.getUUID());
                            Loggin.bungee.info("已禁封UUID：" + obj.getUUID().replaceAll("-", ""));
                        } else {
                            Loggin.bungee.info("无法添加空ID");
                        }
                    } else if (obj.getDo().equalsIgnoreCase("SetPlayer")) {
                        if (obj.getID().isEmpty() && obj.getUUID().isEmpty()) {
                            Loggin.bungee.info("无法修改空ID");
                        } else {
                            PlayerConfig.AddPlayer(obj.getID(), obj.getUUID().replaceAll("-", ""));
                            Loggin.bungee.info("已设置" + obj.getID() + "的UUID为：" + obj.getUUID());
                        }
                    } else if(obj.getDo().equalsIgnoreCase("GetSkin")) {
                        if (obj.getUUID().isEmpty()) {
                            Loggin.bungee.info("UUID为空无法查找");
                        } else {
                            String UUID = obj.getUUID().replaceAll("-", "");
                            Loggin.bungee.info("查找：" + UUID + "的皮肤");

                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            if (Main.SKinTemp.containsKey(UUID)) {
                                TestObj obj1 = new TestObj(Main.SKinTemp.get(UUID));
                                String data = new Gson().toJson(obj1);
                                out.println(data);
                                out.flush();
                                out.close();
                                Loggin.bungee.info("找到" + UUID + "的皮肤");
                            } else {
                                out.println("");
                                out.flush();
                                out.close();
                                Loggin.bungee.info("没有找到" + UUID + "的皮肤");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Read() throws IOException {
        Loggin.bungee.info("控制端口启动：" + server);
        thread.start();
    }
}
