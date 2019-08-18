package com.ggon.darleneJ.user.port.adapter.persistence;

import java.util.List;
//import java.util.Map;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.ggon.darleneJ.user.domain.User;

@Mapper
public interface IUserMapper {
	public User login(@Param("email") String email, @Param("pwd") String pwd);
	 
    public User getById(@Param("id") long id);
    
    public User getByEmail(@Param("email") String email);
    
    public long deleteById(@Param("id") long id);
    
    public long deleteByEmail(@Param("email") String email);
    
    public long hardDeleteByEmail(@Param("email") String email);
    
    public long insertUser(@Param("user") User user, @Param("name") String name, @Param("email") String email ,@Param("role") String role,@Param("pwd") String pwd);
    
    //public long insert(User u,String pwd);
    
    public long customerCount();
    
    public List<User> getCustomers(@Param("offset") long offset,@Param("limit") int limit);
    
    public long adminCount();
    public List<User> getAdmins(@Param("offset") long offset,@Param("limit") int limit);
    
    public long update(@Param("id") long id, @Param("name") String name, @Param("role") String role);
    
    public long updatePassword(@Param("id") long id,@Param("pwd") String pwd);
 
   // public int updateEmployer(User vo);
 
    //public int deleteEmployer(User empNo);

}
