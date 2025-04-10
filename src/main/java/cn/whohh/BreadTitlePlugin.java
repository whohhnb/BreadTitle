package cn.whohh;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import cn.whohh.listeners.BreadEatListener;
import cn.whohh.managers.ConfigManager;

public class BreadTitlePlugin extends JavaPlugin {

    private static BreadTitlePlugin instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") == null) {
            getLogger().severe("需要安装LuckPerms插件！");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new BreadEatListener(), this);
        // 注册命令
        getCommand("brt").setExecutor(new cn.whohh.commands.BRTCommand());

        getLogger().info("LP称号插件已加载！");
    }

    public static BreadTitlePlugin getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}