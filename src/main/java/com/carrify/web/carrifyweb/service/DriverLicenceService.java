package com.carrify.web.carrifyweb.service;



import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.exception.ApiInternalServerError;
import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.DriverLicence.DriverLicence;
import com.carrify.web.carrifyweb.model.User.User;
import com.carrify.web.carrifyweb.repository.DriverLicenceRepository;
import com.carrify.web.carrifyweb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;

@Slf4j
@Service
public class DriverLicenceService {

    private final UserRepository userRepository;
    private final DriverLicenceRepository driverLicenceRepository;

    public DriverLicenceService(UserRepository userRepository, DriverLicenceRepository driverLicenceRepository) {
        this.userRepository = userRepository;
        this.driverLicenceRepository = driverLicenceRepository;
    }

    public List<DriverLicence> getAllDriverLicences() {
        Iterable<DriverLicence> licences = driverLicenceRepository.findAll();
        List<DriverLicence> driverLicences = StreamSupport.stream(licences.spliterator(), false)
                .collect(Collectors.toList());
        if (driverLicences.isEmpty()) {
            log.info("No driver licences in database");
        }
        return driverLicences;
    }


    @Transactional
    public String uploadDriverLicence(String userId, MultipartFile frontImage, MultipartFile reverseImage) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        if(frontImage == null) {
            throw new ApiBadRequestException(CARRIFY018_MSG, CARRIFY018_CODE);
        }

        if(reverseImage == null) {
            throw new ApiBadRequestException(CARRIFY019_MSG, CARRIFY019_CODE);
        }

        Optional<DriverLicence> optionalDriverLicence = driverLicenceRepository.findById(id);

        if(optionalDriverLicence.isPresent()) {
            throw new ApiBadRequestException(CARRIFY020_MSG, CARRIFY020_CODE);
        }

        DriverLicence driverLicence = new DriverLicence();
        driverLicence.setUser(userOptional.get());
        try {
            driverLicence.setImgFront(Base64.getEncoder().encode(frontImage.getBytes()));
            driverLicence.setImgRevers(Base64.getEncoder().encode(reverseImage.getBytes()));
        } catch (IOException e) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        DriverLicence savedDriverLicence = driverLicenceRepository.save(driverLicence);

        if(savedDriverLicence == null) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        return savedDriverLicence.getId().toString();
    }

    public String verifyDriverLicence(String userId, LocalDate expireDate, BindingResult results) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        User user = optionalUser.get();

        if(user.getVerified() != 0) {
            throw new ApiBadRequestException(CARRIFY021_MSG, CARRIFY021_CODE);
        }

        Optional<DriverLicence> optionalDriverLicence = driverLicenceRepository.findById(id);

        if(optionalDriverLicence.isEmpty()) {
            throw new ApiBadRequestException(CARRIFY020_MSG, CARRIFY020_CODE);
        }

        DriverLicence driverLicence = optionalDriverLicence.get();
        driverLicence.setExpireDate(expireDate);
        user.setVerified(1);
        User savedUser = userRepository.save(user);
        DriverLicence savedDriverLicence = driverLicenceRepository.save(driverLicence);

        if(savedUser == null || savedDriverLicence == null) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        return expireDate.toString();
    }

    public Integer checkIfUserSendDriverLicence(String userId) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Optional<DriverLicence> driverLicence = driverLicenceRepository.findById(id);

        if(driverLicence.isEmpty()) {
            return 0;
        }

        return 1;
    }


    public void validateDriverLicenceRequest(BindingResult results) {
        if(results.hasErrors()) {
            for(ObjectError error : results.getAllErrors()) {
                if(error.getDefaultMessage() != null) {
                    String message = error.getDefaultMessage();
                    if (CARRIFY022_CODE.equalsIgnoreCase(message)) {
                        throw new ApiBadRequestException(CARRIFY022_MSG, CARRIFY022_CODE);
                    } else if(CARRIFY023_CODE.equalsIgnoreCase(message)) {
                        throw new ApiBadRequestException(CARRIFY023_MSG, CARRIFY023_CODE);
                    }
                }
            }
        }
    }
}
