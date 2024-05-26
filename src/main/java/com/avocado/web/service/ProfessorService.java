package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import com.avocado.web.entity.ProfessorDTO;

public interface ProfessorService {

	List<ProfessorDTO> studentInfo(String uname);


	int savePs(Map<String, Object> map);


	List<ProfessorDTO> psSchedule(ProfessorDTO pf);

	int pscReserved(Map<String, Object> map2);


	List<Map<String, Object>> getAll(Map<String, Object> map);


	List<ProfessorDTO> professorInfo(int uno);


	int registPsCounsel(Map<String, Object> map);


	List<ProfessorDTO> psCounselList(String uname);


	int changeStatus(Map<String, Object> map);


	List<Map<String, Object>> psTimeList(Map<String, Object> map);


	List<ProfessorDTO> studentList(int uno);





}
