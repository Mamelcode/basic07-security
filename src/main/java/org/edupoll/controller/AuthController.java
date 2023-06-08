package org.edupoll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
	
	@GetMapping("/login")
	public String showLogin(@RequestParam(required = false) String error,
			Model model) {
		System.out.println("showLoginForm....");
		System.out.println("error ==> "+ error);
		
		model.addAttribute("error", error != null);
		return "auth/custom-login";
	}
}
