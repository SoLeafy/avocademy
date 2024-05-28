package com.avocado.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avocado.web.entity.ProfessorDTO;
import com.avocado.web.service.ProfessorService;
import com.avocado.web.util.Util;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class ProfessorAdminController {
	@Resource(name = "professorService")
	private ProfessorService professorService;
	
	@Autowired
	private Util util;
	
	// 교수 관리자페이지 메인
	@GetMapping ("/professorAdmin")
	private String professorAdmin() {
		
		
		return "admin/professor/professorAdmin";
	}
	
	// 지도교수일정등록 들어갔을때 교수 정보 및 스케쥴 가져오기
	@GetMapping("/registPsCounsel")
	public String registPsCounsel(Model model) {
		
		List<ProfessorDTO> list = new ArrayList<ProfessorDTO>();
		HttpSession session = util.getSession();
		// String uname = (String)(session.getAttribute("uname"));
		int uno = (int) session.getAttribute("uno");
		list = professorService.professorInfo(uno);
//		for (ProfessorDTO e : list) {
//			System.out.println(e.getPs_no());
//			System.out.println(e.getUser_no());
//			System.out.println(e.getPs_nm());
//			System.out.println(e.getScsbjt_cd());
//		}
		
		List<ProfessorDTO> list2 = new ArrayList<ProfessorDTO>();
		//list2 = professorService.psSchedule(uno);
//		System.out.println(list2);
//		for (ProfessorDTO e : list2) {
//			System.out.println(e.getPsc_no());
//			System.out.println(e.getPs_no());
//			System.out.println(e.getPsc_ymd());
//			System.out.println(e.getPsc_hr());
//			System.out.println(e.getHr_nm());
//		}
		
		
		model.addAttribute("list", list);
		//model.addAttribute("list2", list2);
		
		return "admin/professor/registPsCounsel";
	}
	
	// 교수 상담일정 등록하기
	@PostMapping("/registPsCounsel")
	public String registPsCounsel(@RequestParam("psName") String psName,
									@RequestParam("scsbjt") String scsbjt,
									@RequestParam("selectedDate") String selectedDate,
									@RequestParam("time") List<String> time ) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		for (String times : time) {
			map.put("psName", psName);
			map.put("scsbjt", scsbjt);
			map.put("selectedDate", selectedDate);
			map.put("times", times);
			int result = professorService.registPsCounsel(map);
		}
		
		
		return "redirect:/registPsCounsel";
	}
	
	// 교수 상담일정관리페이지
	@GetMapping("/psCounselList")
	private String psCounselList (Model model) {
		HttpSession session = util.getSession();
		List<ProfessorDTO> list = new ArrayList<ProfessorDTO>();
		String uname = (String)(session.getAttribute("uname"));
		list = professorService.psCounselList(uname);
		model.addAttribute("list", list);
		//System.out.println(list);
		return "admin/professor/psCounselList";
	}
	
	// 캘린더 표시할 일정
	@ResponseBody
	@PostMapping("/psScheduleAjax")
	public List<ProfessorDTO> psScheduleAjax(@RequestBody ProfessorDTO pf) {
		
		List<ProfessorDTO> list = professorService.psSchedule(pf);
		System.out.println(list);
		
		return list;
	}
	
	@ResponseBody
	@PostMapping("/changeStatus")
	public String changeStatus (@RequestParam("status") String status, 
								@RequestParam("no") String no) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("status", status);
		int result = professorService.changeStatus(map);
		return "result";
	}
	
	@ResponseBody
	@PostMapping("/psTimeList") // ajax 날짜 누르면 기존 상담시간 가져오기
	  public List<Map<String, Object>> psTimeList(@RequestParam ("selectedDate") String selectedDate) {
		System.out.println(selectedDate);
		
		//세션 값 불러오기
		HttpSession session = util.getSession();
		String uname = (String)(session.getAttribute("uname"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("selectedDate", selectedDate);
		map.put("uname", uname);
		System.out.println(selectedDate + uname);
		List<Map<String, Object>> maps = professorService.psTimeList(map);
		System.out.println(maps);
		
		return maps;
	}
	
	// 담당 학생 리스트
	@GetMapping("/studentList")
	private String studentList (Model model) {
		HttpSession session = util.getSession();
		List<ProfessorDTO> list = new ArrayList<ProfessorDTO>();
		int uno = (int)(session.getAttribute("uno"));
		list = professorService.studentList(uno);
		model.addAttribute("list", list);
		//System.out.println(list);
		return "admin/professor/studentList";
	}
	

}
