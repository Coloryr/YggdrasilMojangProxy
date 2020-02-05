package Color_yr.Control;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {
    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
        if (e.getPlayer() == null) {
            return;
        }
        ProxiedPlayer player = e.getPlayer();
        new SocketSend().SocketGetSkin(player);
        Control.log.info("查找玩家：" + player.getName() + "的皮肤");
    }
}
