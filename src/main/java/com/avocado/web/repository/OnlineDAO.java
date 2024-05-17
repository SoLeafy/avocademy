package com.avocado.web.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.OnlineDTO;

@Repository
@Mapper
public interface OnlineDAO {

	List<OnlineDTO> online(Map<String, Integer> pageMap);

	OnlineDTO detail(int bno);

	int write(Map<String, Object> map);

	int count();

	List<OnlineDTO> findAll(Map<String, Integer> map);

	int deletecd(String bno);
}
