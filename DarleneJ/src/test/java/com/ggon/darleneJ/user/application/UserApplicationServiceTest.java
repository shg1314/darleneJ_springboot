/**
 * 
 */
package com.ggon.darleneJ.user.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.ggon.darleneJ.user.application.UserApplicationService;
import com.ggon.darleneJ.DarleneJApplicationTests;
import com.ggon.darleneJ.auth.application.AuthenticationApplicationService;
import com.ggon.darleneJ.auth.domain.AccessToken;
import com.ggon.darleneJ.auth.domain.AuthUser;
import com.ggon.darleneJ.configuration.db.DatabaseConfiguration;
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
@RunWith(SpringRunner.class)
@MybatisTest
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//@AutoConfigureTestDatabase
//@AutoConfigureTestDatabase(replace=Replace.NONE)
//@AutoConfigureMybatis
@Import({DatabaseConfiguration.class,UserApplicationService.class})
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.properties")
//@DataJpaTest
//@MybatisTest
//@ContextConfiguration( loader = AnnotationConfigContextLoader.class)
//@ActiveProfiles("test")
public class UserApplicationServiceTest {
	
	@Autowired
	UserApplicationService userService;
	
	@MockBean
	AuthenticationApplicationService authService;

	
	@Test
	public void whenGetCustomerWithAuthorityThenGetCoustomer() {
		//given
		//admin logined
		String email = "test@test.com";
		User loginuser = User.loadUser(1, email, "test", UserRoleType.ADMIN, LocalDateTime.now(), LocalDateTime.now());;
		AuthUser authuser = AuthUser.authUser(loginuser);
		
		when(authService.isLogined()).thenReturn(true);
		when(authService.getCurrentLoginAccessUserIfExists()).thenReturn(authuser);
		when(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).thenReturn(true);
		
				
		User user = userService.getUser(1);
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo("test");
		assertThat(user.getEmail()).isEqualTo(email);
		assertThat(user.getRole()).isEqualTo(UserRoleType.CUSTOMER);
	}

	
	@Test
	public void whenGetCustomerWithoutAuthorityThenThrowError() {
		//given
		//CUSTOMER logined
		String email = "test@test.com";
		User loginuser = User.loadUser(1, email, "test", UserRoleType.CUSTOMER, LocalDateTime.now(), LocalDateTime.now());;
		AuthUser authuser = AuthUser.authUser(loginuser);
		
		when(authService.isLogined()).thenReturn(true);
		when(authService.getCurrentLoginAccessUserIfExists()).thenReturn(authuser);
		when(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).thenReturn(false);
		
		User user = null;
		try {
			user = userService.getUser(1);
			assertThat(false);
		}catch(Exception e)
		{
			assertThat(user).isNull();
		}
	}
	
	@Test
	public void whenAddNewCustomerWithAuthorityThenNewUserAdded() {
		//given
		//admin logined
		String email = "test@test.com";
		User loginuser = User.loadUser(1, email, "test", UserRoleType.ADMIN, LocalDateTime.now(), LocalDateTime.now());;
		AuthUser authuser = AuthUser.authUser(loginuser);
		
		when(authService.isLogined()).thenReturn(true);
		when(authService.getCurrentLoginAccessUserIfExists()).thenReturn(authuser);
		when(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).thenReturn(true);
		
		String newname = "test3";
		String newemail = "test3@test.com";
		UserRoleType newrole = UserRoleType.CUSTOMER;
		User newUser = User.newUser(newname, newemail , newrole);
		if(userService.getUser(newemail)!=null) {
			userService.deleteUserByEmail(newemail);
		}
		//when add new user
		User u = userService.addUser(newUser.getName(), newUser.getEmail(), "test", newrole);
		
		assertThat(u).isNotNull();
		assertThat(u.getName()).isEqualTo(newUser.getName());
		assertThat(u.getEmail()).isEqualTo(newUser.getEmail());
		assertThat(u.getRole()).isEqualTo(newUser.getRole());
		assertThat(u.getId()).isNotEqualTo(newUser.getId());
		assertThat(u.getId()).isNotEqualTo(User.INVALID_ID);
	}
	
	@Test
	public void whenDeleteCustomerWithAuthorityThenUserDeleted() {
		//given
		//admin logined
		String email = "test@test.com";
		User loginuser = User.loadUser(1, email, "test", UserRoleType.ADMIN, LocalDateTime.now(), LocalDateTime.now());;
		AuthUser authuser = AuthUser.authUser(loginuser);
		
		when(authService.isLogined()).thenReturn(true);
		when(authService.getCurrentLoginAccessUserIfExists()).thenReturn(authuser);
		when(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).thenReturn(true);
		
		
		String newname = "test3";
		String newemail = "test3@test.com";
		UserRoleType newrole = UserRoleType.CUSTOMER;
		User newUser = User.newUser( newemail,newname , newrole);
		User tmp = userService.getUser(newemail);
		if(tmp==null) {
			User u = userService.addUser(newUser.getName(), newUser.getEmail(), "test", newrole);
			assertThat(u).isNotNull();
			newUser = u;
			u = null;
		}
		
		
		userService.deleteUser(newUser);
	
		User deletedUser = userService.getUser(newemail);
		assertThat(deletedUser).isNull();
	}
	
	@Test
	public void whenGetCustomersWithAuthorityAnd20CustomerThenGetUsers() {
		//given
		//admin login
		String email = "test@test.com";
		User loginuser = User.loadUser(1, email, "test", UserRoleType.ADMIN, LocalDateTime.now(), LocalDateTime.now());;
		AuthUser authuser = AuthUser.authUser(loginuser);
		
		when(authService.isLogined()).thenReturn(true);
		when(authService.getCurrentLoginAccessUserIfExists()).thenReturn(authuser);
		when(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).thenReturn(true);
	
		
		// add new 20 Customer
		int maxuser = 20;
		String newname = "test3";
		UserRoleType newrole = UserRoleType.CUSTOMER;
	
		String emailprix = "off_lmt_t1_";
		String emailsuffix = "@test.com" ;
		for( int i =0; i < maxuser; i++ ) {
			try {
			userService.addUser(newname, emailprix + String.valueOf(i) + emailsuffix, "test", newrole);
			}catch(Exception e) {
				
			}
		}
		
		
		long totalcustomercount = userService.customerCount();
		List<User> users = null;
		int emailoffset = 5;
		int offset = (int) ((totalcustomercount == maxuser ) ? emailoffset : (totalcustomercount - maxuser) + emailoffset);
		int limits = 7;
		users = userService.getCustomers(offset, limits);
		assertThat(users).isNotNull();
		assertThat(users.size()).isEqualTo(limits);

		int emialindex = emailoffset;
		User tmp = null;
		for(int i =0  ; i< limits; i++) {
			String emailtmp = emailprix + emialindex + emailsuffix;
			tmp = users.get(i);
			assertThat(tmp).isNotNull();
			assertThat(tmp.getEmail()).isEqualTo(emailtmp);
			emialindex++;
		}
	}
	
	@Test
	public void whenGetAdminsWithAuthorityAnd20AdminThenGetUsers() {
		//given
		//admin login
		String email = "test@test.com";
		User loginuser = User.loadUser(1, email, "test", UserRoleType.ADMIN, LocalDateTime.now(), LocalDateTime.now());;
		AuthUser authuser = AuthUser.authUser(loginuser);
		
		when(authService.isLogined()).thenReturn(true);
		when(authService.getCurrentLoginAccessUserIfExists()).thenReturn(authuser);
		when(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).thenReturn(true);
	
		
		// add new 20 Admin
		int maxuser = 20;
		String newname = "test3";
		UserRoleType newrole = UserRoleType.ADMIN;

		String emailprix = "off_lmt_t1_";
		String emailsuffix = "@test.com" ;
		for( int i =0; i < maxuser; i++ ) {
			try {
			userService.addUser(newname, emailprix + String.valueOf(i) + emailsuffix, "test", newrole);
			}catch(Exception e) {
				
			}
		}
		
		
		long totalcustomercount = userService.adminCount();
		List<User> users = null;
		int emailoffset = 5;
		int offset = (int) ((totalcustomercount == maxuser ) ? emailoffset : (totalcustomercount - maxuser) + emailoffset);
		int limits = 7;
		users = userService.getAdmins(offset, limits);
		assertThat(users).isNotNull();
		assertThat(users.size()).isEqualTo(limits);

		int emialindex = emailoffset;
		User tmp = null;
		for(int i =0  ; i< limits; i++) {
			String emailtmp = emailprix + emialindex + emailsuffix;
			tmp = users.get(i);
			assertThat(tmp).isNotNull();
			assertThat(tmp.getEmail()).isEqualTo(emailtmp);
			emialindex++;
		}
	}
	
	@Test
	public void wheUpdateUserWithAuthorityThenUpdateUser() {
		//given
		//admin logined
		String email = "test@test.com";
		User loginuser = User.loadUser(1, email, "test", UserRoleType.ADMIN, LocalDateTime.now(), LocalDateTime.now());;
		AuthUser authuser = AuthUser.authUser(loginuser);
		
		when(authService.isLogined()).thenReturn(true);
		when(authService.getCurrentLoginAccessUserIfExists()).thenReturn(authuser);
		when(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).thenReturn(true);
		
				
		String newname = "test3";
		String newemail = "test3@test.com";
		UserRoleType newrole = UserRoleType.CUSTOMER;
		User newUser = User.newUser(newname, newemail , newrole);
		if(userService.getUser(newemail)!=null) {
			userService.deleteUserByEmail(newemail);
		}
		//given add new user
		User u = userService.addUser(newUser.getName(), newUser.getEmail(), "test", newrole);
		
		assertThat(u).isNotNull();
		assertThat(u.getName()).isEqualTo(newUser.getName());
		assertThat(u.getEmail()).isEqualTo(newUser.getEmail());
		assertThat(u.getRole()).isEqualTo(newUser.getRole());
		assertThat(u.getId()).isNotEqualTo(newUser.getId());
		assertThat(u.getId()).isNotEqualTo(User.INVALID_ID);
		
		String name2 ="testtest";
		UserRoleType role2 = UserRoleType.ADMIN;
		u.changeName(name2);
		u.changeRole(role2);
		
		userService.update(u);
		
		User u2 = userService.getUser(u.getId());
		
		assertThat(u2).isNotNull();
		assertThat(u2.getName()).isEqualTo(u.getName());
		assertThat(u2.getEmail()).isEqualTo(u.getEmail());
		assertThat(u2.getRole()).isEqualTo(u.getRole());
		assertThat(u2.getId()).isEqualTo(u.getId());
		
	}
	
	@Test
	public void wheUpdateUserPasswordWithAuthorityThenUpdateUserPassword() {
		//given
		//admin logined
		String email = "test@test.com";
		User loginuser = User.loadUser(1, email, "test", UserRoleType.ADMIN, LocalDateTime.now(), LocalDateTime.now());;
		AuthUser authuser = AuthUser.authUser(loginuser);
		
		when(authService.isLogined()).thenReturn(true);
		when(authService.getCurrentLoginAccessUserIfExists()).thenReturn(authuser);
		when(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)).thenReturn(true);
		
				
		String newname = "test3";
		String newemail = "test3@test.com";
		UserRoleType newrole = UserRoleType.CUSTOMER;
		User newUser = User.newUser(newname, newemail , newrole);
		if(userService.getUser(newemail)!=null) {
			userService.deleteUserByEmail(newemail);
		}
		//given add new user
		User u = userService.addUser(newUser.getName(), newUser.getEmail(), "test", newrole);
		
		assertThat(u).isNotNull();
		assertThat(u.getName()).isEqualTo(newUser.getName());
		assertThat(u.getEmail()).isEqualTo(newUser.getEmail());
		assertThat(u.getRole()).isEqualTo(newUser.getRole());
		assertThat(u.getId()).isNotEqualTo(newUser.getId());
		assertThat(u.getId()).isNotEqualTo(User.INVALID_ID);
		

	
		String newpassword = "newtest";
		userService.updatePassword(u,newpassword);
		
		User u2 = userService.getUser(u.getEmail(),newpassword);
		
		assertThat(u2).isNotNull();
		assertThat(u2.getName()).isEqualTo(u.getName());
		assertThat(u2.getEmail()).isEqualTo(u.getEmail());
		assertThat(u2.getRole()).isEqualTo(u.getRole());
		assertThat(u2.getId()).isEqualTo(u.getId());
		
	}
}
