package com.avocado.web.controller;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.service.MyInfoServiceImpl;
@Controller
public class MyInfoController {

	
	@Autowired
	private MyInfoServiceImpl myInfoService;
	
	//마이페이지 이동
	@GetMapping("/myInfo")
	public String myInfo() throws EmailException {
		return "myInfo";   
	}
	
	
	@GetMapping("/mail")
	public String mail() {
		return "mail";
	}
	@PostMapping("/mail")
	public String mail(@RequestParam("email") String email, 
					   @RequestParam("title")String title, 
					   @RequestParam("content")String content) throws EmailException {
		
		System.out.println("email : " + email);
		System.out.println("title : " + title);
		System.out.println("content : " + content);
		
		myInfoService.sendMail(email, title, content);
		return "redirect:/mail";

	}
}
