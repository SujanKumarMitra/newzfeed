package com.herokuapp.newsfeedapp.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.herokuapp.newsfeedapp.entity.User;
import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;
import com.herokuapp.newsfeedapp.repository.UserDetailsImplRepository;

/**
 * This class performs OAuth2 authorization.
 * 
 * @since 2020-05-21
 * @author Sujan Kumar Mitra
 *
 */
@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
	
	/**
	 * for database queries
	 */
	@Autowired
	private UserDetailsImplRepository userDetailsImplRepository;
	
	/**
	 * if user didn't gave permission to access email-id then {@link OAuth2AuthenticationException}
	 * will be thrown with this message
	 */
	@Value("${security.error.scope :'Email Not Provided'}")
	private String errorScope;
	
	/**
	 * If the user trying to authenticate is not signed-up then {@link OAuth2AuthenticationException}
	 * will be thrown with this message.
	 */
	@Value("${security.error.signup:'User Not Registered'}")
	private String errorSignup;
	
	/**
	 * determines whether auto-signup is enabled or not
	 * 
	 */
	@Value("${security.auto-signup:false}")
	private boolean autoSignup;

	/**
	 * This method performs the following:
	 * <ul>
	 * <li>loads the {@link OAuth2User} based on the access-token received from resource server.</li>
	 * <li>verifies whether the user trying to authenticate is registered or not</li>
	 * <li>if registered, then Authentication success</li>
	 * <li>if not, then throws {@link OAuth2AuthenticationException}</li>
	 * </ul>
	 *  
	 *  @since 2020-05-21
	 *  @author Sujan Kumar Mitra
	 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("In OAuthUserService");
		OAuth2User user = super.loadUser(userRequest);
		String email = (String) user.getAttribute("email");
		if(email == null) {
			/*email access not provided*/
			throw new OAuth2AuthenticationException(new OAuth2Error(Integer.toString(HttpStatus.FAILED_DEPENDENCY.value())),errorScope);
		}
		UserDetailsImpl dbUser;
		try {
			dbUser = userDetailsImplRepository.findByUsername(email).get();
			dbUser.setAttributes(user.getAttributes());
		} catch (NoSuchElementException e) {
			if(!autoSignup) {
				/*user is not signed up*/
				throw new OAuth2AuthenticationException(new OAuth2Error("403"),errorSignup);
			}
			dbUser = new UserDetailsImpl(user);
			User newUser = new User(user);
			newUser.setUserDetails(dbUser);
			dbUser.setUser(newUser);
			userDetailsImplRepository.save(dbUser);
		}
		return dbUser;
	}

}
