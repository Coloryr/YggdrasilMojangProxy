package Color_yr.Control;

import cn.mcres.karlatemp.mojangyggdrasil.Log.Read;
import com.google.gson.Gson;

public class SocketSend {
    public String SocketSendCommand(SocketObj obj) {
        try {
            if (!obj.getDo().isEmpty()) {
                
                String data = new Gson().toJson(obj);
                return Read.ReadThread(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "错误";
    }
}
