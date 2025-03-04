package com.example.SpringLibraryTest3.Security;

import com.example.SpringLibraryTest3.Entities.Student;
import com.example.SpringLibraryTest3.Entities.Users;
import com.example.SpringLibraryTest3.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailService.class);
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)  {
        Users users =userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        String role = "";
        if (users instanceof Student) {
            role = "ROLE_Student";
        } else {
            role = "ROLE_Instructor";
        }

        return new User(
                users.getEmail(),
                users.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
