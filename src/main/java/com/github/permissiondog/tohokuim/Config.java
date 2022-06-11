package com.github.permissiondog.tohokuim;

import com.github.permissiondog.tohokuim.util.FileUtil;
import com.github.permissiondog.tohokuim.util.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class Config {
    private static Config config;
    private Config() {
    }
    public static Config getInstance() {
        if (config == null) {
            config = load();
        }
        return config;
    }

    private volatile UUID uuid;

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
        save();
    }


    private static final Logger logger = LogManager.getLogger(Config.class);
    private static Config load() {
        if (Files.notExists(Paths.get(Constant.CONFIG_FILE))) {
            logger.debug("创建默认配置文件");
            FileUtil.writeResource(Constant.DEFAULT_CONFIG, Constant.CONFIG_FILE);
        }

        logger.debug("从 {} 中加载配置文件", () -> Constant.CONFIG_FILE);
        var str = FileUtil.readFile(Constant.CONFIG_FILE);
        Config conf = GsonUtil.gson.fromJson(str
                , new TypeToken<Config>(){}.getType());

        logger.debug("已加载配置文件 {}", () -> str);
        return conf;
    }

    private void save() {
        var json = GsonUtil.gson.toJson(this, new TypeToken<Config>(){}.getType());
        logger.debug("正在保存配置文件 {}", () -> json);
        FileUtil.writeFile(Constant.CONFIG_FILE, json);
    }

}
