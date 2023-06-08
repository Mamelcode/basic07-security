package org.edupoll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {
	
	@GetMapping("/manager")
	public String showManagerHandle() {
		return "manager/index";
	}
	
	@GetMapping("/manager/report")
	public String showManagerReportHandle() {
		return "manager/report";
	}
}
