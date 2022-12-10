package com.project.devgram.oauth2.principal;


import com.project.devgram.entity.User;
import com.project.devgram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{


	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			Optional<User> optionalUser = userRepository.findByUsername(username);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			return new PrincipalDetails(user);
		}
		log.error("loadUserByUsername : x");
		return null;
	}

}
