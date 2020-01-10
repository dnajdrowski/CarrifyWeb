package com.carrify.web.carrifyweb.service;


import com.carrify.web.carrifyweb.exception.ApiErrorConstants;
import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.User.User;
import com.carrify.web.carrifyweb.security.UserPrincipal;
import com.carrify.web.carrifyweb.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ApiNotFoundException(ApiErrorConstants.CARRIFY009_MSG, ApiErrorConstants.CARRIFY009_CODE));

        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new ApiNotFoundException(ApiErrorConstants.CARRIFY009_MSG, ApiErrorConstants.CARRIFY009_CODE));
        return UserPrincipal.create(user);
    }

}
