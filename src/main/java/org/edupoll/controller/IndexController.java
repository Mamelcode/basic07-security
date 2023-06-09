package org.edupoll.controller;

import org.edupoll.config.support.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

	@GetMapping("/")
	public String IndexHadle(CsrfToken csrfToken, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails detail = (UserDetails)auth.getPrincipal();
		model.addAttribute("msg", "반가워요!");
		System.out.println("인증유저 ==> "+ auth.getName() +"//"+ detail.getUsername());
		
		model.addAttribute("csrf", csrfToken.getToken());
		return "landing/index";
	}
	
	@GetMapping("/status")
	@ResponseBody
	public Object statusHandle(HttpSession session) {
		return session.getAttribute("SPRING_SECURITY_CONTEXT");
	}
	
	@GetMapping("/my-home")
	public String showMyHome(@AuthenticationPrincipal AppUser appUser
			,Model model) {
		System.out.println("인증유저 ===> "+ appUser.getUsername());
		model.addAttribute("msg", "반가워요!");
		return "landing/index";
	}
}
