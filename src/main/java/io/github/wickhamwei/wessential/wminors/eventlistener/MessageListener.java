package io.github.wickhamwei.wessential.wminors.eventlistener;

import io.github.wickhamwei.wessential.WEssentialMain;
import io.github.wickhamwei.wessential.wtools.WPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MessageListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String chatString = event.getMessage();
        chatString = chatString.replaceAll("testJHY|前列腺|扣b|打飞机|微距|xp|撸|来一发|屎|尿|屁|法器|hso|精子|阴道|色图|手冲|jy|jp|米青液|拉珠|丁丁|牛子|祭品|性癖|春梦|硅胶塞|揉道|米青|飞机杯|提肛|灌肠|施法|杯子|ph|足交|鸡儿|扩肛|粪|旅人|幕刃|青液|ntr|凌辱|恋尸|魅魔|牛牛|av", "***");
        event.setMessage(chatString);
    }
}
