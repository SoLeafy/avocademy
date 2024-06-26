package com.avocado.web.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.CslSearchDTO;
import com.avocado.web.entity.MyinfoDTO;
import com.avocado.web.entity.UserDTO;

@Repository
@Mapper
public interface MailDAO {
	
	void setKey(UserDTO dto);

	String getEmail(String email);

	List<CslSearchDTO> reservationList(int stud_no);

	UserDTO verifyCode(String uid);
	
	void save(UserDTO user);

	int resetPassword(Map<String, Object> params);

//


}
