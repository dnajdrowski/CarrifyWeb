package com.carrify.web.carrifyweb.service;


import com.carrify.web.carrifyweb.model.DriverLicence.DriverLicence;
import com.carrify.web.carrifyweb.model.User.User;
import com.carrify.web.carrifyweb.repository.DriverLicenceRepository;
import com.carrify.web.carrifyweb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class DriverLicenceService {

    private final UserRepository userRepository;
    private final DriverLicenceRepository driverLicenceRepository;
    private final File uploadDirectory;

    public DriverLicenceService(@Value("${image.upload.dir}") String uploadDirectory, UserRepository userRepository,
                                DriverLicenceRepository driverLicenceRepository) {
        this.userRepository = userRepository;
        this.driverLicenceRepository = driverLicenceRepository;
        this.uploadDirectory = new File(uploadDirectory);
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
    public String uploadFrontDriverLicenceImage(String userId, MultipartFile file) {

        DriverLicence driverLicence = new DriverLicence();
        User user = userRepository.findById(Integer.parseInt(userId)).get();
        driverLicence.setImgFront(file.getName());
        driverLicence.setImgRevers(file.getName());
        driverLicence.setUser(user);

        File fileForDriverLicence = frontImageForUser(driverLicence);

        try (InputStream in = file.getInputStream();
             OutputStream out = new FileOutputStream(fileForDriverLicence)) {
            FileCopyUtils.copy(in, out);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .buildAndExpand(userId)
                .expand("/")
                .expand("front")
                .toUri();

        return location.toString();
    }

    private File frontImageForUser(DriverLicence driverLicence) {
        return new File(uploadDirectory, String.valueOf(driverLicence.getUser().getId()));
    }
}
