package cn.whohh.managers;

import cn.whohh.BreadTitlePlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Optional;

public class ConfigManager {
    private final BreadTitlePlugin plugin;
    
    public ConfigManager() {
        this.plugin = BreadTitlePlugin.getInstance();
        plugin.saveDefaultConfig(); // 确保生成默认配置
    }

    // 返回Optional防止空指针
    public Optional<String> getDisplayName() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("bread.name")) {
            plugin.getLogger().warning("配置缺失 bread.name，使用默认值");
        }
        return Optional.ofNullable(config.getString("bread.name"));
    }

    public List<String> getLore() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("bread.lore")) {
            plugin.getLogger().warning("配置缺失 bread.lore，使用默认值");
            return List.of("铁砧里修改名字", "吃掉后获得称号");
        }
        return config.getStringList("bread.lore");
    }

    public void reload() {
        plugin.reloadConfig();
        plugin.saveConfig(); // 确保新增配置项会被保存
    }
}