package Color_yr.Control;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketSend {
    public String SocketSendCommand(SocketObj obj) {
        try {
            if (!obj.getDo().isEmpty()) {
                Socket socket = new Socket("127.0.0.1", Control.Port);
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                String data = new Gson().toJson(obj);
                out.write(data);
                out.flush();
                out.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                String temp = in.readLine();
                socket.close();
                return temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "错误";
    }
}
