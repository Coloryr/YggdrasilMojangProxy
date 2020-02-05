package Color_yr.Control;

import com.google.gson.Gson;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.*;
import java.net.Socket;

public class SocketSend {
    public void SocketSendCommand(SocketObj obj) {
        try {
            if (!obj.getDo().isEmpty()) {
                Socket socket = new Socket("127.0.0.1", Control.Port);
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                String data = new Gson().toJson(obj);
                out.write(data);
                out.flush();
                out.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SocketGetSkin(ProxiedPlayer player) {
        try {
            String UUID = player.getUniqueId().toString().replaceAll("-", "");
            SocketObj obj = new SocketObj("GetSkin", "", UUID);
            Socket socket = new Socket("127.0.0.1", Control.Port);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data = new Gson().toJson(obj);
            out.println(data);
            out.flush();
            String str = in.readLine();
            TestObj TestObj = new Gson().fromJson(str, TestObj.class);
            Control.log.info("找到玩家" + UUID + "的皮肤");
            Control.config.AddSkin(UUID, TestObj);
            Skin.SetSkin(player, TestObj);
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
