package cn.whohh.listeners;

import cn.whohh.BreadTitlePlugin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

public class BreadEatListener implements Listener {

    private LuckPerms getLuckPerms() {
        return Bukkit.getServicesManager().getRegistration(LuckPerms.class).getProvider();
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() != Material.SUSPICIOUS_STEW) return;

        ItemMeta meta = e.getItem().getItemMeta();
        Byte isTitleBread = meta.getPersistentDataContainer().get(
                new NamespacedKey(BreadTitlePlugin.getInstance(), "title_bread"),
                PersistentDataType.BYTE);

        if (isTitleBread != null && isTitleBread == 1) {
            String rawTitle = meta.getDisplayName(); 
            Player player = e.getPlayer();

            Bukkit.getScheduler().runTaskAsynchronously(BreadTitlePlugin.getInstance(), () -> {
                try {
                    LuckPerms lp = getLuckPerms();
                    if (lp == null) {
                        sendSyncMessage(player, "§c需要安装LuckPerms");
                        return;
                    }

                    lp.getUserManager().modifyUser(player.getUniqueId(), user -> {
                        Set<Node> toRemove = new HashSet<>();
                        for (Node node : user.data().toCollection()) {
                            if (node instanceof PrefixNode) {
                                PrefixNode pNode = (PrefixNode) node;
                                if (pNode.getPriority() == 100) {
                                    toRemove.add(node);
                                }
                            }
                        }
                        toRemove.forEach(user.data()::remove);

                        user.data().add(PrefixNode.builder()
                                .priority(100)
                                .prefix(rawTitle)
                                .build());
                    });
                    sendSyncMessage(player, "§a你的称号已更改为: " + meta.getDisplayName()); 
                } catch (Exception ex) {
                    sendSyncMessage(player, "§c你的称号更改失败，请联系管理员！");
                    Bukkit.getLogger().severe("[BreadTitle] 错误: " + ex.getMessage());
                }
            });
        }
    }

    private void sendSyncMessage(Player player, String message) {
        Bukkit.getScheduler().runTask(BreadTitlePlugin.getInstance(),
                () -> player.sendMessage(message));
    }
}