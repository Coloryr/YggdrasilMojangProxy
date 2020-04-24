package cn.mcres.karlatemp.mojangyggdrasil.Log;

import cn.mcres.karlatemp.mojangyggdrasil.Config.MainConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Config.PlayerConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.SocketObj;
import com.google.gson.Gson;

public class Read {
    public static String ReadThread(String data) {
        try {
            SocketObj obj = new Gson().fromJson(data, SocketObj.class);
            if (obj.getDo().equalsIgnoreCase("reload")) {
                MainConfig.loadconfig();
                Loggin.boot.info("已重读配置文件");
            } else if (obj.getDo().equalsIgnoreCase("AddBanID")) {
                if (!obj.getID().isEmpty()) {
                    PlayerConfig.AddBanID(obj.getID());
                    String temp = "已禁封ID：" + obj.getID();
                    Loggin.boot.info(temp);
                    return temp;
                } else if (!obj.getUUID().isEmpty()) {
                    PlayerConfig.AddBanUUID(obj.getUUID());
                    String temp = "已禁封UUID：" + obj.getUUID().replaceAll("-", "");
                    Loggin.boot.info(temp);
                    return temp;
                } else {
                    return "无法添加空ID";
                }
            } else if (obj.getDo().equalsIgnoreCase("SetPlayer")) {
                if (obj.getID().isEmpty() && obj.getUUID().isEmpty()) {
                    return "无法添加空ID";
                } else {
                    PlayerConfig.AddPlayer(obj.getID(), obj.getUUID().replaceAll("-", ""));
                    String temp = "已设置" + obj.getID() + "的UUID为：" + obj.getUUID();
                    Loggin.boot.info(temp);
                    return temp;

                }
            } else if (obj.getDo().equalsIgnoreCase("ReSkin")) {
                if (obj.getID().isEmpty()) {
                    return "刷新皮肤失败";
                } else {
                    PlayerConfig.RemoveSkin(obj.getID());
                    String temp = "刷新" + obj.getID() + "刷新皮肤成功，重进服务器后生效";
                    Loggin.boot.info(temp);
                    return temp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

