package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.models.AppUserDetail;
import com.kelempok7.serverapp.models.entity.User;
import com.kelempok7.serverapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserDetailService implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Username or Email not Found")
                );
        return new AppUserDetail(user);
    }
}
