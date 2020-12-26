package com.example.multiplex.security;

import com.example.multiplex.model.persistence.User;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("Test", "Teston", "test@test.com", "test", "123");
    }
}
