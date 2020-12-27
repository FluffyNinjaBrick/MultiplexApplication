package com.example.multiplex.security;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.repository.MultiplexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// This class supplies Security with user information
@Service
public class MultiplexUserDetailsService implements UserDetailsService {

    @Autowired
    private MultiplexRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDetails userDetails = this.repository.getUserByUsername(username);
            return userDetails;
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
