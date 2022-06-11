package com.github.permissiondog.tohokuim.controller;

import com.github.permissiondog.tohokuim.entity.Profile;
import com.github.permissiondog.tohokuim.service.impl.ProfileServiceImpl;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 设置自己的个人信息
 * @author PermissionDog
 */
public class EditProfileController {
    private static final Logger logger = LogManager.getLogger(EditProfileController.class);
    @FXML
    private MFXTextField nameTextField;
    @FXML
    private MFXTextField signatureTextField;
    @FXML
    public void onConfirm() {
        logger.trace("按钮被按下");
        var service = ProfileServiceImpl.getInstance();
        var profile = new Profile();
        logger.trace("name: {}", nameTextField::getText);
        logger.trace("signature: {}", signatureTextField::getText);

        profile.setName(nameTextField.getText());
        profile.setSignature(signatureTextField.getText());
        ProfileServiceImpl.getInstance().update(profile);

        var stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }
}
