package cn.whohh.commands;

// 添加以下导入
import cn.whohh.BreadTitlePlugin;
import cn.whohh.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class BRTCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) return false;

        switch (args[0].toLowerCase()) {
            case "give":
                if (args.length < 2) return false;
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    target.getInventory().addItem(ItemManager.getTitleBread());
                    sender.sendMessage("已给予玩家称号食物");
                }
                break;
                
            case "reload":
                BreadTitlePlugin.getInstance().getConfigManager().reload();
                sender.sendMessage("配置已重载");
                break;
                
            case "name":
                if (sender instanceof Player && args.length >= 2) {
                    Player player = (Player) sender;
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item.getType() == Material.SUSPICIOUS_STEW) {
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(args[1].replace('&', '§'));
                        item.setItemMeta(meta);
                    }
                }
                break;
        }
        return true;
    }
}