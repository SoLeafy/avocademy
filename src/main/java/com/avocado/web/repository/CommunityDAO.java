package com.avocado.web.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.CommunityDTO;
import com.avocado.web.entity.OnlineDTO;

@Repository
@Mapper
public interface CommunityDAO {

		CommunityDTO detail(int cno);

		int write(Map<String, Object> map);

		int count();

		List<OnlineDTO> findAll(Map<String, Integer> map);

		int deletecd(String cno);

		void inserFile(CommunityDTO communityDTO);

		List<CommunityDTO> community(Map<String, Integer> pageMap);
	
	
		
	}


