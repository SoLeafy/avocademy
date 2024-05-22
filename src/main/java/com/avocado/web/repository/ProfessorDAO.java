package com.avocado.web.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.ProfessorDTO;

@Repository
@Mapper

public interface ProfessorDAO {

	List<ProfessorDTO> studentInfo(String uname);


	int savePs(Map<String, Object> map);


	List<ProfessorDTO> psSchedule(String uname);

	Map<String, Object> timeList(HashMap<String, Object> map);

	int pscReserved(Map<String, Object> map2);


	List<Map<String, Object>> getAll(Map<String, Object> map);


	List<ProfessorDTO> professorInfo(String uname);


	int registPsCounsel(Map<String, Object> map);


	List<ProfessorDTO> psCounselList(String uname);


	int changeStatus(Map<String, Object> map);


	List<Map<String, Object>> psTimeList(Map<String, Object> map);


		
}
