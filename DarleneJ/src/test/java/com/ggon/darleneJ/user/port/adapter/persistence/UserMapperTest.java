/**
 * 
 */
package com.ggon.darleneJ.user.port.adapter.persistence;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import com.ggon.darleneJ.common.domain.entity.Identifiable;
import com.ggon.darleneJ.configuration.db.DatabaseConfiguration;
import com.ggon.darleneJ.user.domain.User;
import com.ggon.darleneJ.user.domain.UserRoleType;

/**
 * @FileName  : UserMapperTest.java
 * @Project   : DarleneJ
 * @since     : Aug 16, 2019
 * @author    : ggon
 * 
 */
@RunWith(SpringRunner.class)
@Import(DatabaseConfiguration.class)
@MybatisTest
@TestPropertySource("classpath:application.properties")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {

	@Autowired
	IUserMapper userMapper;
	
	

	
	User testUser = null;
	User newUser = null;
	
	@Before
	public void setup() {
		User u = userMapper.getByEmail("test@test.com");
		if(u==null) {
			u = User.newUser("test@test.com", "test", UserRoleType.ADMIN);
			userMapper.insertUser(u,u.getName(),u.getEmail(),u.getRole().toString(), "test");
		}else testUser = u;
	}
	
	@Test
	@Transactional
	//@Rollback(false)
	public void whenAddNotExistsUserThenAdded() {
		//given
		String email = "test2@test.com";
		userMapper.hardDeleteByEmail(email);
		User tmp  = userMapper.getByEmail(email);
		assertThat(tmp).isNull();
		
		String name = "test2";
		UserRoleType role = UserRoleType.CUSTOMER;
		newUser = User.newUser(email, name, role);
		assertThat(newUser).isNotNull();
		

		//when insert new user
		long count  = userMapper.insertUser(newUser,newUser.getName(),newUser.getEmail(),newUser.getRole().toString(), "test");
	 	assertThat(count).isNotEqualTo(0);
	
	 	//then used added
		User user =userMapper.getByEmail(email);
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo(newUser.getName());
		assertThat(user.getEmail()).isEqualTo(newUser.getEmail());
		assertThat(user.getRole()).isEqualTo(newUser.getRole());
		assertThat(user.getId()).isEqualTo(newUser.getId());
	}
	
	@Test
	public void whenGetUserWithExistsIdThenGetUser() {
		
		User user = userMapper.getById(testUser.getId());
		
		assertThat(user.getName()).isEqualTo(testUser.getName());
		assertThat(user.getEmail()).isEqualTo(testUser.getEmail());
		assertThat(user.getRole()).isEqualTo(testUser.getRole());
		assertThat(user.getId()).isEqualTo(testUser.getId());
	}
	

}
