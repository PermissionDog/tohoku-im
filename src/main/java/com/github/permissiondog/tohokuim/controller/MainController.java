package com.github.permissiondog.tohokuim.controller;

import com.github.permissiondog.tohokuim.Constant;
import com.github.permissiondog.tohokuim.entity.Friend;
import com.github.permissiondog.tohokuim.entity.Message;
import com.github.permissiondog.tohokuim.service.FriendService;
import com.github.permissiondog.tohokuim.service.exception.NetworkException;
import com.github.permissiondog.tohokuim.service.exception.NoSuchFriendException;
import com.github.permissiondog.tohokuim.service.impl.FriendServiceImpl;
import com.github.permissiondog.tohokuim.service.impl.MessageServiceImpl;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.UUID;

public class MainController {

    private static final Logger logger = LogManager.getLogger(MainController.class);
    public MFXTextField messageTextField;

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
    @FXML
    private VBox messagesVBox;

    private Friend selectedFriend;

    public void init() {
        logger.trace("friendsScrollPane: {}", friendsScrollPane);
        logger.trace("friendsVBox: {}", friendsVBox);
        logger.trace("nameLabel: {}", nameLabel);
        ScrollUtils.addSmoothScrolling(friendsScrollPane);
        ScrollUtils.addSmoothScrolling(messagesScrollPane);

        friendsVBox.getChildren().clear();
        contentVBox.setVisible(false);

        var friends = FriendServiceImpl.getInstance().getAll();
        friends.forEach(this::insertFriend);

        // 有新朋友就加入到左侧列表
        FriendServiceImpl.getInstance().registerOnAddListener(friend -> {
            Platform.runLater(() -> insertFriend(friend));
        });

        MessageServiceImpl.getInstance().registerOnAddListener(message -> {
            Platform.runLater(() -> {
                if (!message.getSession().equals(selectedFriend.getUUID())) {
                    return;
                }
                insertNewMessage(message);
            });
        });
    }

    private void insertFriend(Friend friend) {
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
            selectedFriend = FriendServiceImpl.getInstance().get(selectedFriendUUID).orElseThrow();

            showContent();

        });
        friendsVBox.getChildren().add(node);
    }

    private void insertMessage(String msg, String styleClass) {
        var hBoxNode = new HBox();
        hBoxNode.getStyleClass().add(styleClass);
        var labelNode = new Label();
        labelNode.setText(msg);
        hBoxNode.getChildren().add(labelNode);
        messagesVBox.getChildren().add(hBoxNode);
    }
    private void insertTimeMessage(String msg) {
        insertMessage(msg, "time");
    }
    private void insertReceivedMessage(String msg) {
        insertMessage(msg, "received-msg");
    }
    private void insertSentMessage(String msg) {
        insertMessage(msg, "sent-msg");
    }
    private void insertNewMessage(Message message) {
        var sendTime = message.getSendTime();
        if (sendTime.toLocalDate().equals(LocalDate.now())) {
            insertTimeMessage(sendTime.format(Constant.TIME_FORMATTER));
        } else {
            insertTimeMessage(sendTime.format(Constant.DATE_TIME_FORMATTER));
        }
        switch (message.getDirection()) {
            case Sent -> insertSentMessage(message.getMessage());
            case Received -> insertReceivedMessage(message.getMessage());
        }
    }
    private void showContent() {

        // 展示名字
        nameLabel.setText(selectedFriend.getName());

        // 展示个性签名
        signatureLabel.setText(selectedFriend.getSignature());

        // 展示消息
        messagesVBox.getChildren().clear();
        MessageServiceImpl.getInstance().getAll(selectedFriend.getUUID()).stream().sorted(Comparator.comparing(Message::getSendTime)).forEach(this::insertNewMessage);

        contentVBox.setVisible(true);
    }

    public void onSend() {
        var msg = messageTextField.getText();
        messageTextField.setText("");
        logger.info("发送消息给 {}: {}", selectedFriend.getName(), msg);

       new Thread(() -> {
           try {
               var result = MessageServiceImpl.getInstance().sendMessage(selectedFriend.getUUID(), msg);
               logger.trace("成功发送消息: {}", result.getMessage());
           } catch (NoSuchFriendException e) {
               logger.error("朋友不存在 {} {}", selectedFriend.getUUID(), selectedFriend.getName());
           } catch (NetworkException e) {
               Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, e.getMessage()).show());
               logger.warn("无法发送消息", e);
           }
       }).start();
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        if (!keyEvent.getCode().equals(KeyCode.ENTER)) {
            return;
        }
        onSend();
    }
}
