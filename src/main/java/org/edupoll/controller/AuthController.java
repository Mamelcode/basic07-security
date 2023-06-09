package org.edupoll.controller;

import org.edupoll.config.support.JpaUserDetailsService;
import org.edupoll.model.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
	
	@Autowired JpaUserDetailsService userService;
	
	@GetMapping("/login")
	public String showLogin(@RequestParam(required = false) String error,
			Model model, CsrfToken csrfToken) {
		System.out.println("error ==> "+ error);
		
		model.addAttribute("error", error != null);
		model.addAttribute("csrf", csrfToken.getToken());
		return "auth/custom-login";
	}
	
	@GetMapping("/join")
	public String showJoinHandle(Model model, CsrfToken csrfToken) {
		model.addAttribute("csrf", csrfToken.getToken());
		return "auth/join";
	}
	
	@PostMapping("/join-task")
	public String joinTaskHandle(UserData user) {
		
		System.out.println("user ==> "+ user);
		
		boolean result = userService.createUser(user);
		System.out.println("회원가입 여부 ==> "+ result);
		
		if(result) {
			return "redirect:/login";
		}else {
			return "redirect:/join";
		}
	}
	
	@GetMapping("/access/dinied")
	public String showDiniedHandle() {
		return "auth/dinied";
	}
}
