package de.ait_tr.g_33_shop.service;

import de.ait_tr.g_33_shop.domain.entity.ConfirmationCode;
import de.ait_tr.g_33_shop.domain.entity.User;
import de.ait_tr.g_33_shop.repository.ConfirmationCodeRepository;
import de.ait_tr.g_33_shop.repository.UserRepository;
import de.ait_tr.g_33_shop.service.interfaces.ConfirmationService;
import de.ait_tr.g_33_shop.service.interfaces.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationCodeRepository repository;

    private final UserRepository userRepository;

    public ConfirmationServiceImpl(ConfirmationCodeRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public String generateConfirmationCode(User user) {
        //LocalDateTime expired = LocalDateTime.now().plusDays(1);
        LocalDateTime expired = LocalDateTime.now().plusMinutes(10);
        String code = UUID.randomUUID().toString();
        ConfirmationCode entity = new ConfirmationCode(code, expired, user);
        repository.save(entity);
        return code;
    }

    @Override
    public String confirmRegistration(String code) {
        Optional<ConfirmationCode> optionalConfirmationCode = repository.findByCode(code);
        if (optionalConfirmationCode.isPresent()) {
            ConfirmationCode confirmationCode = optionalConfirmationCode.get();
            if (confirmationCode.getExpired().isAfter(LocalDateTime.now())) {
                User user = confirmationCode.getUser();
                user.setActive(true);
                userRepository.save(user);
                return "User confirmed.";
            } else {
                return "User verification timed out. Please try again.";
            }

        }
        return "Error in verification code. Please check the verification code and try again.";

    }


    public static class FileServiceImpl implements FileService {
        @Override
        public String upload(MultipartFile file, String productTitle) {
            return null;
        }
    }
}