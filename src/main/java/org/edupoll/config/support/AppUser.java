package org.edupoll.config.support;

import java.util.Collection;
import java.util.List;

import org.edupoll.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUser implements UserDetails {

	User userEntity;
	
	public AppUser() {
	}
	
	public AppUser(User userEntity) {
		this.userEntity = userEntity;
	}
	
	public List<String> getFriends() {
		return List.of("zoro77");
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userEntity.getRoles().stream().map(t-> new SimpleGrantedAuthority(t.getRole())).toList();
	}

	@Override
	public String getPassword() {
		return userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return userEntity.getEnabled().equals("Y") ? true : false;
	}

}
