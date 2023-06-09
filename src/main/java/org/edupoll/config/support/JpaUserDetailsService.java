package org.edupoll.config.support;

import java.util.Optional;

import org.edupoll.model.dto.UserData;
import org.edupoll.model.entity.User;
import org.edupoll.model.entity.UserRole;
import org.edupoll.repository.UserRepository;
import org.edupoll.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired UserRepository userRepository;
	
	@Autowired UserRoleRepository userRoleRepository;
		
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<User> user = userRepository.findById(username);
		if(user.isPresent()) {

			User findUser = user.get();
			
			findUser.getRoles().get(0);
			
			return new AppUser(findUser);
		} else {
			throw new UsernameNotFoundException("Not Found : "+ username);
		}
	}
	
	public boolean createUser(UserData userData) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		userData.setPassword("{bcrypt}"+encoder.encode(userData.getPassword()));
		User user = new User(userData.getUsername(), userData.getPassword());
		userRepository.save(user);
		UserRole role = new UserRole("ROLE_GUEST", user);
		userRoleRepository.save(role);
		
		return true;
	}
}
