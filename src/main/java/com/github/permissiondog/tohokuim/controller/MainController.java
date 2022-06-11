package com.github.permissiondog.tohokuim.controller;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainController {

    private static final Logger logger = LogManager.getLogger(MainController.class);

    @FXML
    private VBox friendsVBox;

    @FXML
    private MFXScrollPane friendsScrollPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label signatureLabel;

    @FXML
    private MFXScrollPane messagesScrollPane;

    public void init() {
        logger.trace("friendsScrollPane: {}", friendsScrollPane);
        logger.trace("friendsVBox: {}", friendsVBox);
        logger.trace("nameLabel: {}", nameLabel);
        ScrollUtils.addSmoothScrolling(friendsScrollPane);
        ScrollUtils.addSmoothScrolling(messagesScrollPane);
    }

}
