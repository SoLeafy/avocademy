package com.avocado.web.service;

import java.util.List;

import com.avocado.web.entity.GroupDTO;

public interface GroupService {

	List<GroupDTO> programList();

	void registProgram(GroupDTO dto);

	List<GroupDTO> adminPRGList();

	void approvePRG(int prg_no);

	void disApprovePRG(int prg_no);

	void createSchedule(GroupDTO dto);

	int getProgramNo(String string);

	void openPRG(int prg_no);
	

}
