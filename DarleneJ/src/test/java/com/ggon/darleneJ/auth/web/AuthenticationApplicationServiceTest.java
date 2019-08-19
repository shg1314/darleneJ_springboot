/**
 * 
 */
package com.ggon.darleneJ.auth.web;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ggon.darleneJ.auth.application.AuthenticationApplicationService;
import com.ggon.darleneJ.auth.domain.AccessToken;
import com.ggon.darleneJ.auth.domain.AuthUser;
import com.ggon.darleneJ.common.application.AlreadyExistsException;
import com.ggon.darleneJ.common.application.IApplicationSessionService;
import com.ggon.darleneJ.configuration.db.DatabaseConfiguration;
import com.ggon.darleneJ.user.application.UserApplicationService;
import com.ggon.darleneJ.user.domain.User;
import com.ggon.darleneJ.user.domain.UserRoleType;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName  : AuthenticationApplicationServiceTest.java
 * @Project   : DarleneJ
 * @since     : Aug 19, 2019
 * @author    : ggon
 * 
 */
@RunWith(SpringRunner.class)
@Import({DatabaseConfiguration.class,AuthenticationApplicationService.class,ApplicationSessionServiceTest.class})
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.properties")
@MybatisTest
public class AuthenticationApplicationServiceTest {

	@Autowired
	AuthenticationApplicationService authService;
	
	
	@Test
	public void whenLoginWithCorrectUserAndPwdThenGetAccessToken() {
		//given logout
		String name = "test";
		String email = "test@test.com";
		String pwd = "test";
		UserRoleType role = UserRoleType.CUSTOMER;
		User expectedUser = User.newUser(email, name, role);
		assertThat(expectedUser).isNotNull();
		
		if(authService.isLogined())
		{
			authService.logout();
		}
		assertThat(authService.isLogined()).isEqualTo(false);
		
		//when login
		AccessToken token = authService.login(email, pwd);
		assertThat(token).isNotNull();
		AuthUser auth = token.getUser();
		assertThat(auth).isNotNull();
		
		//then get user
		assertThat(auth.getEmail()).isEqualTo(expectedUser.getEmail());
		assertThat(auth.getName()).isEqualTo(expectedUser.getName());
		assertThat(auth.getRole()).isEqualTo(expectedUser.getRole());
	}
	
	@Test
	public void givenNoLoginUser_WhenthrowWhenHasNoAuthentication_ThenThrow() {
		//given logout
		if(authService.isLogined())
		{
			authService.logout();
		}
		
		boolean throwEx = false;
		
		try {
			authService.throwWhenHasNoAuthentication();
			assertThat(false);
		}catch(Exception e) {
			throwEx = true;
		}
		assertThat(throwEx).isEqualTo(true);
	}
	
	@Test
	public void givenCustomerLogined_whenCheckHigherAuthorityWithAdminOrMaintainer_ThenReturnFalse() {
		//given customer logined
		String email = "test@test.com";
		String pwd = "test";

		
		if(authService.isLogined())
		{
			authService.logout();
		}
		assertThat(authService.isLogined()).isEqualTo(false);
		AccessToken token = authService.login(email, pwd);
		assertThat(token).isNotNull();
		AuthUser auth = token.getUser();
		assertThat(auth).isNotNull();
		
		assertThat(authService.isCurrentUserCustomer()).isEqualTo(true);
		
		//when check higherAuthorith with admin
		
		assertThat(authService.hasHigherAuthority(UserRoleType.ADMIN)).isEqualTo(false);
		assertThat(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).isEqualTo(false);
		
		assertThat(authService.hasHigherAuthority(UserRoleType.MAINTAINER)).isEqualTo(false);
		assertThat(authService.hasEqualOrHigherAuthority(UserRoleType.MAINTAINER)).isEqualTo(false);
	}
	
	@Test
	public void givenAdminLogined_whenCheckHigherAuthorityWithAdminOrMaintainer_ThenReturnFalse() {
		//given admin logined
		String email = "darlenej@naver.com";
		String pwd = "darlenej";
 
		if(authService.isLogined())
		{
			authService.logout();
		}
		assertThat(authService.isLogined()).isEqualTo(false);
		AccessToken token = authService.login(email, pwd);
		assertThat(token).isNotNull();
		AuthUser auth = token.getUser();
		assertThat(auth).isNotNull();
		
		assertThat(authService.isCurrentUserAdmin()).isEqualTo(true);
		
		//when check higherAuthorith with admin
		assertThat(authService.hasHigherAuthority(UserRoleType.ADMIN)).isEqualTo(false);
		assertThat(authService.hasHigherAuthority(UserRoleType.MAINTAINER)).isEqualTo(false);
		
	}
	
	
	@Test
	public void givenAdminLogined_whenCheckHigherAuthorityWithAdminOrCustomer_ThenReturnTrue() {
		//given admin logined
		String email = "darlenej@naver.com";
		String pwd = "darlenej";
 
		if(authService.isLogined())
		{
			authService.logout();
		}
		assertThat(authService.isLogined()).isEqualTo(false);
		AccessToken token = authService.login(email, pwd);
		assertThat(token).isNotNull();
		AuthUser auth = token.getUser();
		assertThat(auth).isNotNull();
		
		assertThat(authService.isCurrentUserAdmin()).isEqualTo(true);
		
		//when check higherAuthorith with admin
		assertThat(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).isEqualTo(true);
		assertThat(authService.hasEqualOrHigherAuthority(UserRoleType.CUSTOMER)).isEqualTo(true);
	}
	
	@Test
	public void givenMaintainerLogined_whenCheckHigherAuthorityWithAdminOrCustomer_ThenReturnTrue() {
		//given admin logined
		String email = "maintainer@test.com";
		String pwd = "test2";
 
		if(authService.isLogined())
		{
			authService.logout();
		}
		assertThat(authService.isLogined()).isEqualTo(false);
		AccessToken token = authService.login(email, pwd);
		assertThat(token).isNotNull();
		AuthUser auth = token.getUser();
		assertThat(auth).isNotNull();
		
		assertThat(authService.isCurrentUserMaintainer()).isEqualTo(true);
		
		//when check higherAuthorith with admin
		assertThat(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).isEqualTo(true);
		assertThat(authService.hasEqualOrHigherAuthority(UserRoleType.CUSTOMER)).isEqualTo(true);
		assertThat(authService.hasEqualOrHigherAuthority(UserRoleType.MAINTAINER)).isEqualTo(true);
		
		assertThat(authService.hasHigherAuthority(UserRoleType.ADMIN)).isEqualTo(true);
		assertThat(authService.hasHigherAuthority(UserRoleType.CUSTOMER)).isEqualTo(true);
		assertThat(authService.hasHigherAuthority(UserRoleType.MAINTAINER)).isEqualTo(true);
	}
}

class ApplicationSessionServiceTest implements IApplicationSessionService{
	private Map<String,Object> map = new HashMap<String,Object>();
	
	public ApplicationSessionServiceTest() {
		
	}
	@Override
	public void add(String name, Object obj) {
		// TODO Auto-generated method stub
		Object o = map.get(name);
		if(null != o) throw new AlreadyExistsException("alread exists key(" + name + ")");
		if(null == obj) new IllegalArgumentException("obj is null");
		map.put(name, obj);
	}

	@Override
	public void addIfExistsReplace(String name, Object obj) {
		if(null == name || name.isEmpty() == true) new IllegalArgumentException("name is null or empty");
		if(null == obj) new IllegalArgumentException("obj is null");
		map.put(name, obj);
	}

	@Override
	public Object getIfExists(String name) {
		if(null == name || name.isEmpty() == true) new IllegalArgumentException("name is null or empty");
		return map.get(name);
	}

	@Override
	public void removeIfExists(String name) {
		if(null == name || name.isEmpty() == true) new IllegalArgumentException("name is null or empty");
		map.put(name, null);
	}
	
}
