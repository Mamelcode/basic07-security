package org.edupoll.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

	@GetMapping("/")
	public String IndexHadle(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("msg", "반가워요!");
		UserDetails detail = (UserDetails)auth.getPrincipal();
		System.out.println("인증유저 ==> "+ auth.getName() +"//"+ detail.getUsername());
		
		return "landing/index";
	}
	
	@GetMapping("/status")
	@ResponseBody
	public Object statusHandle(HttpSession session) {
		return session.getAttribute("SPRING_SECURITY_CONTEXT");
	}
	
	@GetMapping("/my-home")
	public String showMyHome(@AuthenticationPrincipal UserDetails userDetails
			,Model model) {
		System.out.println("인증유저 ===> "+ userDetails.getUsername());
		model.addAttribute("msg", "반가워요!");
		return "landing/index";
	}
}
