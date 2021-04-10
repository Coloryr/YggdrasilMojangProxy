package Color_yr.Control;

import cn.mcres.karlatemp.mojangyggdrasil.Log.Read;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.SocketObj;

public class CommandEX {
    public static void EX(Object sender, String[] args, String name)
    {
        if (args.length == 0) {
            Control.side.sendMessage(sender, "§d[Control]§c错误，请使用/my help 获取帮助");
        } else if (args[0].equalsIgnoreCase("reskin")) {
            SocketObj obj = new SocketObj(SocketObj.Fun.ReSkin, name, "");
            String temp = Read.ReadThread(obj);
            Control.side.sendMessage(sender, "§d[Control]§2" + temp);
        } else if (!Control.config.Admin.contains(name)) {
            Control.side.sendMessage(sender, "§d[Control]§c禁止使用该指令");
        } else if (args[0].equalsIgnoreCase("help")) {
            Control.side.sendMessage(sender, "§d[Control]§2帮助手册");
            Control.side.sendMessage(sender, "§d[Control]§2使用/my reskin 来刷新你的皮肤");
            if (Control.config.Admin.contains(name)) {
                Control.side.sendMessage(sender,"§d[Control]§2使用/my reload 来重载UUID缓存");
                Control.side.sendMessage(sender,"§d[Control]§2使用/my banID [ID] 禁用ID");
                Control.side.sendMessage(sender,"§d[Control]§2使用/my banUUID [UUID] 禁用UUID");
                Control.side.sendMessage(sender,"§d[Control]§2使用/my SetPlayer [ID] [UUID] 设置玩家ID和UUID");
                Control.side.sendMessage(sender,"§d[Control]§2使用/my AddPlayer [ID] 添加空白玩家到列表");
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            SocketObj obj = new SocketObj(SocketObj.Fun.Reload, "", "");
            String temp = Read.ReadThread(obj);
            Control.side.sendMessage(sender,"§d[Control]§2" + temp);
        } else if (args[0].equalsIgnoreCase("banID")) {
            if (args.length != 2) {
                Control.side.sendMessage(sender,"§d[Control]§c错误，请使用/my help 获取帮助");
                return;
            }
            SocketObj obj = new SocketObj(SocketObj.Fun.AddBanID, args[1], "");
            String temp = Read.ReadThread(obj);
            Control.side.sendMessage(sender,"§d[Control]§2" + temp);
        } else if (args[0].equalsIgnoreCase("banUUID")) {
            if (args.length != 2) {
                Control.side.sendMessage(sender,"§d[Control]§c错误，请使用/my help 获取帮助");
                return;
            }
            SocketObj obj = new SocketObj(SocketObj.Fun.AddBanID, "", args[1]);
            String temp = Read.ReadThread(obj);
            Control.side.sendMessage(sender,"§d[Control]§2" + temp);
        } else if (args[0].equalsIgnoreCase("unbanID")) {
            if (args.length != 2) {
                Control.side.sendMessage(sender,"§d[Control]§c错误，请使用/my help 获取帮助");
                return;
            }
            SocketObj obj = new SocketObj(SocketObj.Fun.RemoveBanID, args[1], "");
            String temp = Read.ReadThread(obj);
            Control.side.sendMessage(sender,"§d[Control]§2" + temp);
        } else if (args[0].equalsIgnoreCase("unbanUUID")) {
            if (args.length != 2) {
                Control.side.sendMessage(sender,"§d[Control]§c错误，请使用/my help 获取帮助");
                return;
            }
            SocketObj obj = new SocketObj(SocketObj.Fun.RemoveBanUUID, "", args[1]);
            String temp = Read.ReadThread(obj);
            Control.side.sendMessage(sender,"§d[Control]§2" + temp);
        } else if (args[0].equalsIgnoreCase("SetPlayer")) {
            if (args.length != 3) {
                Control.side.sendMessage(sender,"§d[Control]§c错误，请使用/my help 获取帮助");
                return;
            }
            SocketObj obj = new SocketObj(SocketObj.Fun.SetPlayer, args[1], args[2]);
            String temp = Read.ReadThread(obj);
            Control.side.sendMessage(sender,"§d[Control]§2" + temp);
        } else if (args[0].equalsIgnoreCase("AddPlayer")) {
            if (args.length != 2) {
                Control.side.sendMessage(sender,"§d[Control]§c错误，请使用/my help 获取帮助");
                return;
            }
            SocketObj obj = new SocketObj(SocketObj.Fun.AddPlayer, args[1], "");
            String temp = Read.ReadThread(obj);
            Control.side.sendMessage(sender,"§d[Control]§2" + temp);
        } else if (args[0].equalsIgnoreCase("RemovePlayer")) {
            if (args.length != 2) {
                Control.side.sendMessage(sender,"§d[Control]§c错误，请使用/my help 获取帮助");
                return;
            }
            SocketObj obj = new SocketObj(SocketObj.Fun.RemovePlayer, args[1], "");
            String temp = Read.ReadThread(obj);
            Control.side.sendMessage(sender,"§d[Control]§2" + temp);
        } else {
            Control.side.sendMessage(sender,"§d[Control]§c错误，请使用/my help 获取帮助");
        }
    }
}
