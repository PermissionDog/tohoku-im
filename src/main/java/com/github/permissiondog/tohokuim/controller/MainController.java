package com.github.permissiondog.tohokuim.controller;

import com.github.permissiondog.tohokuim.service.FriendService;
import com.github.permissiondog.tohokuim.service.impl.FriendServiceImpl;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
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

        friendsVBox.getChildren().clear();

        var friends = FriendServiceImpl.getInstance().getAll();
        friends.forEach(friend -> {
            var node = new MFXRectangleToggleNode();
            node.setText(friend.getName());
            node.setOnAction(event -> {
                logger.trace("toggle source: {}", event.getSource());
                var self = (MFXRectangleToggleNode) event.getSource();
                friendsVBox.getChildren().forEach(node1 -> {
                    var other = (MFXRectangleToggleNode) node1;
                    if (!other.equals(self)) {
                        other.setSelected(false);
                    }
                });
            });
            friendsVBox.getChildren().add(node);
        });
    }

}
