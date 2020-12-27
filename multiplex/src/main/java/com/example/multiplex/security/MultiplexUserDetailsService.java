package com.example.multiplex.security;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.repository.MultiplexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This is a mock user supplier. It will need to be replaced with an actual supplier
 * that uses the database.
 *
 * This class is used in the REST controller and in the filter.
 */
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
