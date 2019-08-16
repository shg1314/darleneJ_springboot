/**
 * 
 */
package com.ggon.darleneJ.user.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ggon.darleneJ.user.application.UserApplicationService;
import com.ggon.darleneJ.auth.application.AuthenticationApplicationService;
import com.ggon.darleneJ.auth.domain.AccessToken;
import com.ggon.darleneJ.auth.domain.AuthUser;
import com.ggon.darleneJ.user.domain.User;
import com.ggon.darleneJ.user.domain.UserRoleType;
import com.ggon.darleneJ.user.port.adapter.persistence.IUserMapper;

/**
 * @FileName  : UserApplicationServiceTest.java
 * @Project   : DarleneJ
 * @since     : Aug 10, 2019
 * @author    : ggon
 * 
 */
//@MybatisTest
@Import(IUserMapper.class)
//@ContextConfiguration(value={"sql-user.xml"})
//@ContextConfiguration(classes = {UserApplicationService.class,IUserMapper.class})
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApplicationServiceTest {
	@Autowired(required = true)
	UserApplicationService userService;
	
	@MockBean
	AuthenticationApplicationService authService;
	
	@Test
	public void whenGetCoustomerWithAuthorityThenGetCoustomer() {
		String email = "test@test.com";
		String pwd = "test";
		User loginuser = User.loadUser(1, email, "test", UserRoleType.ADMIN, LocalDateTime.now(), LocalDateTime.now());;
		AuthUser authuser = AuthUser.authUser(loginuser);
		AccessToken token = AccessToken.newToken(authuser);
		
		//when(authService.login(email, pwd)).thenReturn(token);
		when(authService.isLogined()).thenReturn(true);
		when(authService.getCurrentLoginAccessUserIfExists()).thenReturn(authuser);
		
		
		User user = userService.getUser(1);
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo("test");
		assertThat(user.getEmail()).isEqualTo(email);
		assertThat(user.getRole()).isEqualTo(UserRoleType.CUSTOMER);
	}
}
