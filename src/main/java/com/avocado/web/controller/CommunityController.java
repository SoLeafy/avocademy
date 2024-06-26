package com.avocado.web.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.avocado.web.entity.CommunityDTO;
import com.avocado.web.entity.FilesDTO;
import com.avocado.web.service.CommunityService;
import com.avocado.web.util.Util;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/community")
@Controller
public class CommunityController {

	@Autowired
	private Util util;

	@Resource(name = "communityService")
	private CommunityService communityService;
	
	
	
	
	//상세보기
	@GetMapping("/detail")
	public String detail(Model model, @RequestParam(name = "cno", required = false, defaultValue = "1") int cno,
			HttpSession session) {
		// System.out.println(cno);

		// 게시글 상세 정보 조회
		CommunityDTO detail = communityService.detail(cno);

		// 파일 번호 조회
		// int fileNo = communityService.getFileNo();
		String fsn = communityService.getFsn(detail.getFno());
		detail.setFsn(fsn);
		// model.addAttribute("detail", detail);
		// model.addAttribute("fileNo", fileNo);
		model.addAttribute("fsn", detail.getFno());
		model.addAttribute("fno", detail.getFno());
		System.out.println("fno:"+detail.getFno());
		System.out.println("fsn:"+detail.getFsn());

		// 세션에 로그인이 안되어있는 상태에서 session.get 하면 null 반환되고
		// null 이랑 equals 연산을 하다보니 null 오류가 발생
		// 로그인이 안되어있으면 다시 게시판 페이지로 로딩이되거나 로그인으로 이동시키거나 골라서
		if (session.getAttribute("uname") == null) {
			// return "redirect:/community";
			return "redirect:/login";
		} else {
			model.addAttribute("detail", detail);
			System.out.println("디테일 컨트롤러 :" + detail);

			return "community/detail";
		}
	}

	//글쓰기
	@GetMapping("/write")
	public String write(Model model) {

		HttpSession session = util.getSession();

		if (session.getAttribute("uname") != null) {
			  int ugrade = (int) session.getAttribute("ugrade");
	            model.addAttribute("ugrade", ugrade);

			return "community/write";
		} else {
			return "redirect:/login";
		}
	}
	
	//글쓰기
	@PostMapping("/write")
	public String write(@RequestParam(name = "ctitle") String ctitle, 
						@RequestParam(name = "ccontent") String ccontent,
						@RequestParam(name = "isNotice", required = false) boolean isNotice,
						@RequestParam("fileUp") MultipartFile file, 
						HttpSession session, 
						Model model) {
		// System.out.println(ctitle + ccontent);
		

		// 로그인 검사
		String uname = (String) session.getAttribute("uname");

		if (uname != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ctitle", ctitle);
			map.put("ccontent", ccontent);
			map.put("uname", uname);
			map.put("uno", session.getAttribute("uno"));
			map.put("isNotice", isNotice ? 1:0); // 공지사항 체크 여부 추가
			// System.out.println(map);

			// dto 객체 생성
			FilesDTO dto = new FilesDTO();
			

			communityService.write(map, dto, file,isNotice);

			// 성공시 목록 페이지로 리디렉션
			/* String url = "online"; */
			return "redirect:/community";

		} else {
			return "redirect:/login";
		}

	}

	//글삭제
	@PostMapping("/deletecd")
	public String deletecd(@RequestParam(name = "cno") String cno, HttpSession session) {
		//System.out.println("삭제 : " + cno);
		int result = communityService.deletecd(cno);
		return "redirect:/community";
	}

	
	// 댓글달기
		// /online/comment/bno=42
		@PostMapping("/comment")
		@ResponseBody
		public String comment(@RequestParam(name = "cno") String cno, HttpSession session,
				@RequestParam(name="ccontent") String ccontent, Model model) {
			int uno = (int) session.getAttribute("uno");
			
			System.out.println("글번호 : " + cno);
			System.out.println("글번호 : " + ccontent);
			
			int result = communityService.saveComment(uno, cno, ccontent);
			
			
			return String.valueOf(result);
		}

		
	@GetMapping("/downloadFile/{fsn}")
	public HttpEntity<UrlResource> downloadFile(@PathVariable("fsn") String fsn) throws MalformedURLException {
	System.out.println("컨트롤러------다운로드------------------:");

		// 파일 경로 설정 (예시: /avocademy/src/main/resources/static/files/)
	    //String baseDir = "/avocademy/src/main/resources/static/files/";
	  	String filePath = Paths.get( "src", "main", "resources", "static", "files", fsn).toString();
	 
	    // 파일 객체 생성
	    File file = new File(filePath);

	    // 파일이 존재하지 않는 경우 처리
	    if (!file.exists()) {
	        // 예외 처리 또는 에러 응답 반환
	        return ResponseEntity.notFound().build();
	    }
	    
	    // 절대 경로를 가져옴
        Path path = Paths.get(file.getAbsolutePath());

	    // Resource 객체 생성
	    UrlResource resource = new UrlResource(file.toURI());

	    // Content-Type 및 Content-Disposition 헤더 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", resource.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
		
	}


