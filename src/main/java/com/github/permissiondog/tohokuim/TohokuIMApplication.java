package com.github.permissiondog.tohokuim;

import com.github.permissiondog.tohokuim.controller.MainController;
import com.github.permissiondog.tohokuim.service.impl.MessageServiceImpl;
import com.github.permissiondog.tohokuim.service.impl.ProfileServiceImpl;
import com.github.permissiondog.tohokuim.util.FileUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class TohokuIMApplication extends Application {

    private static final Logger logger = LogManager.getLogger(TohokuIMApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        if ("".equals(ProfileServiceImpl.getInstance().get().getName())) {
            var editProfileStage = new Stage();
            var editProfileScene = new Scene(
                    new FXMLLoader(TohokuIMApplication.class.getResource("view/edit-profile.fxml")).load(),
                    400, 300);

            editProfileStage.setScene(editProfileScene);
            editProfileStage.showAndWait();
        }

        var mainStage = new Stage();
        var mainStageLoader = new FXMLLoader(TohokuIMApplication.class.getResource("view/main.fxml"));
        var mainScene = new Scene(
                mainStageLoader.load(),
                659, 572
        );
        mainStage.setScene(mainScene);
        MainController mainController = mainStageLoader.getController();
        mainController.init();
        mainStage.setOnCloseRequest(event -> System.exit(0));
        mainStage.show();
    }
    private static void writeResource(String resource, String fileName) {
        if (Files.notExists(Paths.get(fileName))) {
            logger.debug("创建默认文件 {}", fileName);
            FileUtil.writeResource(resource, fileName);
        }
    }
    private static void initFiles() {

        Path dataPath = Path.of(Constant.DATA_PATH);
        if (Files.notExists(dataPath)) {
            logger.debug("创建数据文件夹 {}", Constant.DATA_PATH);
            try {
                Files.createDirectories(dataPath);
            } catch (IOException e) {
                logger.fatal("创建目录错误", e);
                System.exit(1);
            }
        }
        writeResource(Constant.DEFAULT_PROFILE, Constant.PROFILE_FILE);
        writeResource(Constant.DEFAULT_FRIENDS, Constant.FRIENDS_FILE);
        writeResource(Constant.DEFAULT_MESSAGES, Constant.MESSAGES_FILE);
    }

    public static void main(String[] args) {
        logger.info("正在启动 Tohoku IM");
        logger.trace("{}", Config.getInstance().getUUID());
        if (Config.getInstance().getUUID() == null) {
            Config.getInstance().setUUID(UUID.randomUUID());
        }
        initFiles();
        MessageServiceImpl.getInstance().initNet();
        launch();
    }
}