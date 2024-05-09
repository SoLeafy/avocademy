package com.avocado.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.GroupDTO;

@Mapper
@Repository
public interface GroupDAO {
	
	List<GroupDTO> programList();
}