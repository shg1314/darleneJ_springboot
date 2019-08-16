package com.ggon.darleneJ.user.port.adapter.persistence;

import java.util.List;
//import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.ggon.darleneJ.user.domain.User;

@Mapper
public interface IUserMapper {
	public User login(@Param("email") String email, @Param("pwd") String pwd);
	 
    public User getById(@Param("id") long id);
    
    public long deleteById(@Param("id") long id);
 
    public long insert(User u,@Param("pwd") String pwd);
    
    public List<User> getCustomers(@Param("offset") long offset,@Param("limit") int limit);
 
   // public int updateEmployer(User vo);
 
    //public int deleteEmployer(User empNo);

}
