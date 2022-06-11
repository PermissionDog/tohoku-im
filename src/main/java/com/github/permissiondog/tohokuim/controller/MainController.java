package com.github.permissiondog.tohokuim.controller;

import com.github.permissiondog.tohokuim.entity.Friend;
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

import java.util.UUID;

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

    @FXML
    private VBox contentVBox;

    public void init() {
        logger.trace("friendsScrollPane: {}", friendsScrollPane);
        logger.trace("friendsVBox: {}", friendsVBox);
        logger.trace("nameLabel: {}", nameLabel);
        ScrollUtils.addSmoothScrolling(friendsScrollPane);
        ScrollUtils.addSmoothScrolling(messagesScrollPane);

        friendsVBox.getChildren().clear();
        contentVBox.setVisible(false);

        var friends = FriendServiceImpl.getInstance().getAll();
        friends.forEach(friend -> {
            var node = new MFXRectangleToggleNode();
            node.setText(friend.getName());
            node.setUserData(friend.getUUID());
            node.setOnAction(event -> {
                logger.trace("toggle source: {}", event.getSource());
                var self = (MFXRectangleToggleNode) event.getSource();
                friendsVBox.getChildren().forEach(node1 -> {
                    var other = (MFXRectangleToggleNode) node1;
                    if (!other.equals(self)) {
                        other.setSelected(false);
                    }
                });
                var selectedFriendUUID = (UUID) self.getUserData();
                var selectedFriend = FriendServiceImpl.getInstance().get(selectedFriendUUID);

                showContent(selectedFriend);

            });
            friendsVBox.getChildren().add(node);
        });
    }

    private void showContent(Friend friend) {

        // 展示名字
        nameLabel.setText(friend.getName());

        //展示个性签名
        signatureLabel.setText(friend.getSignature());

        contentVBox.setVisible(true);
    }
}
