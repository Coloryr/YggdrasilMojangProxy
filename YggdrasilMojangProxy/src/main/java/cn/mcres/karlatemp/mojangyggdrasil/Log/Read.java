package cn.mcres.karlatemp.mojangyggdrasil.Log;

import cn.mcres.karlatemp.mojangyggdrasil.Config.MainConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Config.PlayerConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.SocketObj;

public class Read {
    public static String ReadThread(SocketObj obj) {
        try {
            switch (obj.getDo()) {
                case SetPlayer:
                    if (obj.getID().isEmpty() && obj.getUUID().isEmpty()) {
                        return "无法添加空ID";
                    } else {
                        PlayerConfig.AddPlayer(obj.getID(), obj.getUUID().replaceAll("-", ""));
                        String temp = "已设置" + obj.getID() + "的UUID为：" + obj.getUUID();
                        Loggin.boot.info(temp);
                        return temp;

                    }
                case ReSkin:
                    if (obj.getID().isEmpty()) {
                        return "刷新皮肤失败";
                    } else {
                        PlayerConfig.RemoveSkin(obj.getID());
                        String temp = "刷新" + obj.getID() + "刷新皮肤成功，重进服务器后生效";
                        Loggin.boot.info(temp);
                        return temp;
                    }
                case Reload:
                    MainConfig.loadconfig();
                    Loggin.boot.info("已重读配置文件");
                    return "已重读";
                case AddBanID:
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
                case AddPlayer:
                    if (!obj.getID().isEmpty()) {
                        if (PlayerConfig.HavePlayer(obj.getID())) {
                            String temp = "玩家ID：" + obj.getID() + "已存在";
                            Loggin.boot.info(temp);
                            return temp;
                        }
                        PlayerConfig.AddPlayer(obj.getID(), "");
                        String temp = "已添加空白玩家：" + obj.getID();
                        Loggin.boot.info(temp);
                        return temp;
                    } else {
                        return "无法添加空ID";
                    }
                case RemoveBanID:
                    if (!obj.getID().isEmpty()) {
                        PlayerConfig.RemoveBanID(obj.getID());
                        String temp = "已解禁玩家：" + obj.getID();
                        Loggin.boot.info(temp);
                        return temp;
                    } else {
                        return "无法解禁空ID";
                    }
                case RemoveBanUUID:
                    if (!obj.getID().isEmpty()) {
                        PlayerConfig.RemoveBanUUID(obj.getID());
                        String temp = "已解禁玩家：" + obj.getID();
                        Loggin.boot.info(temp);
                        return temp;
                    } else {
                        return "无法解禁空ID";
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

