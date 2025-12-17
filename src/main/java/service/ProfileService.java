package service;

import entity.Profile;
import repository.ProfileRepository;
import exception.ServiceException;
import service.PasswordService;

import java.security.NoSuchAlgorithmException;

public class ProfileService {
    private final ProfileRepository profileRepository;
    private final PasswordService passwordService;

    public ProfileService(ProfileRepository proFileRepository, PasswordService passwordService) {
        this.profileRepository = proFileRepository;
        this.passwordService = passwordService;
    }

    public Profile createProfile(String login, String password) throws NoSuchAlgorithmException, ServiceException {
        if (profileRepository.isLoginExist(login)) {
            throw new ServiceException(String.format("Логин '%s' уже занят.", login));
        }

        String salt = passwordService.generateSalt();
        String hashPass = passwordService.hashPassword(password, salt);

        Profile newProfile = new Profile();
        newProfile.setLogin(login);
        newProfile.setPassword(hashPass);
        newProfile.setSalt(salt);


        profileRepository.save(newProfile);
        return newProfile;
    }

    public Long login(String login, String password) throws NoSuchAlgorithmException {
        Profile profile = profileRepository.getByLogin(login);
        if (profile == null) return null;
        boolean passwordValid = passwordService.verifyPassword(password, profile.getSalt(), profile.getPassword());
        return passwordValid ? profile.getId() : null;

    }

    private String passwordToHash(String password) {
        return String.valueOf(password.hashCode());
    }
}
