package org.gfg.UserServiceApplication.service;

import org.gfg.UserServiceApplication.dtos.UserRequestDTO;
import org.gfg.UserServiceApplication.model.Users;
import org.gfg.UserServiceApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.Authority}")
    private String userAuthority;

    @Value("${admin.Authority}")
    private String adminAuthority;


    public Users addUpdate(UserRequestDTO dto) {
        Users user = dto.toUser();
        user.setAuthorities(userAuthority);
        if(userRepository.findByContact(user.getContact()) != null){
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return userRepository.save(user);
//        wallet service , send a mail that the user has been created
        // kafka ?
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
