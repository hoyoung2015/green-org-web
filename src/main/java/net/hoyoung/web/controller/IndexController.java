package net.hoyoung.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	private Logger log = Logger.getLogger(IndexController.class);
	@RequestMapping(value="/reqBody")
	public String reqBody(@RequestBody String body){
		log.info("reqBody");
		System.out.println(body);
		return "index";
	}
	@RequestMapping(value="/lanjie")
	public @ResponseBody Map<String,Object> lanjie(@RequestParam String ceshi){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "hoyoung");
		return map;
	}
}
