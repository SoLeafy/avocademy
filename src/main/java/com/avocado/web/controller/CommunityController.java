package com.avocado.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.OnlineDTO;
import com.avocado.web.service.OnlineService;
import com.avocado.web.util.Util;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	private Util util;

	@Resource(name = "onlineService")
	private OnlineService onlineService;

	@GetMapping("/detail")
	public String detail(Model model, @RequestParam(name = "bno", required = false, defaultValue = "1") int bno,
			HttpSession session) {
		System.out.println(bno);
		OnlineDTO detail = onlineService.detail(bno);
		System.out.println(detail.toString());

		// 세션에 로그인이 안되어있는 상태에서 session.get 하면 null 반환되고
		// null 이랑 equals 연산을 하다보니 null 오류가 발생
		// 로그인이 안되어있으면 다시 게시판 페이지로 로딩이되거나 로그인으로 이동시키거나 골라서
		if(session.getAttribute("uname") == null) {
			// return "redirect:/login";
			return "redirect:/online";
		} else if (detail.getUname().equals(session.getAttribute("uname")) || (int) session.getAttribute("ugrade") == 5) {
			model.addAttribute("detail", detail);
			return "online/detail";
		} else {
			return "redirect:/online";
		}
	}

	@GetMapping("/write")
	public String write() {

		HttpSession session = util.getSession();

		if (session.getAttribute("uname") != null) {

			return "online/write";
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping("/write")
	public String write(@RequestParam(name = "btitle") String btitle, @RequestParam(name = "bcontent") String bcontent,
			HttpSession session) {
		System.out.println(btitle + bcontent);
		// 글 작성 로직 실행

		// 로그인 검사해주세요

		String uname = (String) session.getAttribute("uname");

		if (uname != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("btitle", btitle);
			map.put("bcontent", bcontent);
			map.put("uname", uname);
			map.put("uno", session.getAttribute("uno"));

			System.out.println(map);

			int result = onlineService.write(map);

			// 성공시 목록 페이지로 리디렉션
			/* String url = "online"; */
			return "redirect:/online";

		} else {
			return "redirect:/login";
		}

	}

	@PostMapping("/deletecd")
	public String deletecd(@RequestParam(name = "bno") String bno) {
		System.out.println("삭제 : " + bno);
		int result = onlineService.deletecd(bno);
		return "redirect:/online";
	}
	

}