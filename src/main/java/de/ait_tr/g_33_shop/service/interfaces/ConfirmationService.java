package de.ait_tr.g_33_shop.service.interfaces;

import de.ait_tr.g_33_shop.domain.entity.ConfirmationCode;
import de.ait_tr.g_33_shop.domain.entity.User;

import java.util.Optional;

public interface ConfirmationService {

    String generateConfirmationCode(User user);


    String confirmRegistration(String code);


}
