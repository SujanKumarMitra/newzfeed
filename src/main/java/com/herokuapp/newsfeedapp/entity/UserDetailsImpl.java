package com.herokuapp.newsfeedapp.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.herokuapp.newsfeedapp.dto.SignupUser;

/**
 * This class contains data related to user security. This class is used
 * internally by Spring Security.
 * 
 * @author Sujan Kumar Mitra
 * @since 2020-05-15
 */
@Entity
@Table(name = "user_details")
public class UserDetailsImpl implements UserDetails, OAuth2User, OidcUser {

	@Transient
	private static final long serialVersionUID = 1L;

	/**
	 * id of user details
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	/**
	 * username by which user is identified during authentication (email)
	 */
	@Column(name = "username", nullable = false)
	private String username;

	/**
	 * password of the user
	 */
	@Column(name = "password")
	private String password;

	/**
	 * holds the status of account expiration
	 */
	@Column(name = "account_expired")
	private boolean accountExpired;

	/**
	 * holds the status of account being locked or not
	 */
	@Column(name = "account_locked")
	private boolean accountLocked;

	/**
	 * holds the status of credentials being expired or not
	 */
	@Column(name = "credentials_expired")
	private boolean credentialsExpired;

	/**
	 * hold the status of account enabled or not
	 */
	@Column(name = "enabled")
	private boolean enabled;

	/**
	 * contains list of roles granted to the user
	 */
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_details_id"))
	@Column(name = "role")
	private List<Role> authorities;
	
	/**
	 * injection mapping of user
	 */
	@OneToOne(mappedBy = "userDetails",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JsonManagedReference
	private  User user;
	
	/**
	 * oauth attributes
	 */
	@Transient
	private Map<String, Object> attributes;
	
	/**
	 * OpenID Connect 1.0 user information
	 */
	@Transient
	private OidcUserInfo oidcUserInfo;
	
	/**
	 * OpenID Connect 1.0 token
	 */
	@Transient
	private OidcIdToken oidcIdToken;

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public UserDetailsImpl() {
		super();
	}

	public UserDetailsImpl(OidcUser oidcUser) {
		this.id = 0;
		this.oidcIdToken = oidcUser.getIdToken();
		this.oidcUserInfo = oidcUser.getUserInfo();
		this.attributes = oidcUser.getAttributes();
		this.username = oidcUser.getAttribute("email");
		this.password = null;
		this.attributes = oidcUser.getAttributes();
		this.setAuthorities(oidcUser.getAuthorities());
		this.addRole(Role.USER);
		this.enabled = true;
	}
	
	public UserDetailsImpl(OAuth2User oAuth2User) {
		this.id = 0;
		this.oidcIdToken = null;
		this.oidcUserInfo = null;
		this.username = oAuth2User.getAttribute("email");
		this.password = null;
		this.setAuthorities(oAuth2User.getAuthorities());
		this.setAttributes(oAuth2User.getAttributes());
		this.addRole(Role.USER);
		this.enabled = true;
	}
	
	public UserDetailsImpl(SignupUser signupUser) {
		this.id = 0;
		this.username = signupUser.getEmail();
		this.password = signupUser.getPassword();
		this.user = new User(signupUser);
		this.accountExpired = false;
		this.accountLocked = false;
		this.credentialsExpired = false;
		this.enabled = true;
		this.authorities = Arrays.asList(Role.USER);
		this.user.setUserDetails(this);

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities.parallelStream()
				.map((role) -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		/*
		 * only use the authorities
		 * that are listed in {@link Role} enum*/
		this.authorities = authorities.parallelStream()
				.filter((authority)->{
					try {
						Role.valueOf(authority.getAuthority());
						return true;
					} catch (IllegalArgumentException e) {
						return false;
					}
				})
				.map((authority)->Role.valueOf(authority.getAuthority()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getName() {
		return (String) this.getAttribute("name");
	}

	@Override
	public Map<String, Object> getClaims() {
		return this.attributes;
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return oidcUserInfo;
	}

	@Override
	public OidcIdToken getIdToken() {
		return oidcIdToken;
	}

	public void setOidcIdToken(OidcIdToken oidcIdToken) {
		this.oidcIdToken = oidcIdToken;
	}

	public void setOidcUserInfo(OidcUserInfo oidcUserInfo) {
		this.oidcUserInfo = oidcUserInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Role> getRoles() {
		return authorities;
	}
	
	public void addRole(Role role) {
		if(!authorities.contains(role))
		authorities.add(role);
	}
	
	public void removeRole(Role role) {
		authorities.remove(role);
	}

	public long getId() {
		return id;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
