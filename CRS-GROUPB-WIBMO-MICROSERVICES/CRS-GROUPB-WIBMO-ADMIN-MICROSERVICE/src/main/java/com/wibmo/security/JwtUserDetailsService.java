/**
 * 
 */
package com.wibmo.security;


import com.wibmo.entity.User;
import com.wibmo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserId(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		Set<SimpleGrantedAuthority> roles = new HashSet<>();
		roles.add(new SimpleGrantedAuthority(user.get().getRole().toString()));
		org.springframework.security.core.userdetails.User userDetailsUser = new org.springframework.security.core.userdetails.User(user.get().getUserId(), user.get().getPassword(),
				roles);
		return userDetailsUser;
	}

	public User save(User user) {
		System.out.println("Entered save method of user details service");
		userRepository.save(user);
		return user;
	}
}