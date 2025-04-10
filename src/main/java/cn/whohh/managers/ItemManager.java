package cn.whohh.managers;

import cn.whohh.BreadTitlePlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.stream.Collectors;

public class ItemManager {

    public static ItemStack getTitleBread() {
        BreadTitlePlugin plugin = BreadTitlePlugin.getInstance();
        ConfigManager config = plugin.getConfigManager();

        // 从配置获取数据（带默认值）
        String displayName = config.getDisplayName().orElse("");
        List<String> lore = config.getLore();

        // 创建物品实例（使用甜菜根材质）
        ItemStack bread = new ItemStack(Material.SUSPICIOUS_STEW);
        ItemMeta meta = bread.getItemMeta();

        // 处理颜色代码 & -> §
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));

        // 处理Lore颜色代码
        List<String> coloredLore = lore.stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList());
        meta.setLore(coloredLore);

        // 设置NBT标记
        meta.getPersistentDataContainer().set(
                new NamespacedKey(plugin, "title_bread"),
                PersistentDataType.BYTE,
                (byte) 1
        );

        // 添加绑定诅咒附魔并隐藏附魔效果
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);

        bread.setItemMeta(meta);
        return bread;
    }
}