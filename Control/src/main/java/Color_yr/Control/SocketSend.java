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
}
