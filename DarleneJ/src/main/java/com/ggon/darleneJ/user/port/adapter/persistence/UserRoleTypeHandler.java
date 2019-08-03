/**
 * 
 */
package com.ggon.darleneJ.user.port.adapter.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.ggon.darleneJ.user.domain.UnknownUserRoleTypeException;
import com.ggon.darleneJ.user.domain.UserIllegalArgumentException;
import com.ggon.darleneJ.user.domain.UserRoleType;
import com.mysql.cj.jdbc.CallableStatement;

/**
 * @FileName  : UserRoleTypeHandler.java
 * @Project   : DarleneJ
 * @since     : Aug 3, 2019
 * @author    : ggon
 * 
 */
public class UserRoleTypeHandler implements TypeHandler<UserRoleType> {
	public UserRoleType getResult(ResultSet rs, String param) throws SQLException {
		UserRoleType role = UserRoleType.UNKNOWN;
	    try {
	    	role = UserRoleType.getEnum(rs.getString(param));
		} catch (UserIllegalArgumentException | UnknownUserRoleTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return role;
	}

	public UserRoleType getResult(CallableStatement cs, int col) throws SQLException {
		UserRoleType role = UserRoleType.UNKNOWN;
	    try {
	    	role = UserRoleType.getEnum(cs.getString(col));
		} catch (UserIllegalArgumentException | UnknownUserRoleTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return role;
	}

	public void setParameter(PreparedStatement ps, int paramInt, UserRoleType paramType, JdbcType jdbctype)
	        throws SQLException {
	    ps.setString(paramInt, paramType.toString());
	}

	@Override
	public UserRoleType getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRoleType getResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
