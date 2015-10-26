package net.hoyoung.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hoyoung.entity.User;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/user")
public class UserController {
	private Logger log = Logger.getLogger(UserController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
	@RequestMapping("/")
	public String index(){
		log.warn("fsdffffff------------------------------");
		return "user/index";
	}
    @RequestMapping("/db")
    public @ResponseBody Map<String,Object> db(){
        log.warn("db------------------------------");
        jdbcTemplate.update("insert into test(id,name) VALUES (?,?)",5,"hoyoung");

        Map<String,Object> model = new HashMap<String, Object>();
        model.put("success",true);
        return model;
    }
    @RequestMapping("/loadData")
    public @ResponseBody Map<String,Object> importDb() throws IOException {
        log.warn("loadData------------------------------");
        List<String> sqlLines = FileUtils.readLines(new File("company_info_to_sqllite.csv"), "UTF-8");
        String valueFields = sqlLines.get(0).replace("\"","'");
        for (int i = 1; i < sqlLines.size(); i++) {
            int status = jdbcTemplate.update("INSERT INTO company_info");
        }


        Map<String,Object> model = new HashMap<String, Object>();
        System.err.println();
        model.put("success", sqlLines.get(0));
        return model;
    }
	@RequestMapping("/add")
	public String add(){
		log.warn("add------------------------------");
		return "user/index";
	}
	@RequestMapping("/show")
	public ModelAndView show(){
		log.warn("show------------------------------");
		List<User> list = new ArrayList<User>();
		list.add(new User("hoyoung", 24));
		list.add(new User("xiaoniu", 22));
		ModelMap mm = new ModelMap();
		mm.put("users", list);
		
		User boss = new User();
		boss.setName("boss");
		mm.put("boss", boss);
		return new ModelAndView("user/show",mm);
	}
	@RequestMapping("/par")
	public ModelAndView par(@RequestParam("p") String p){
		log.warn("show------------------------------"+p);
		log.warn("length------------------------------"+p.length());
		Map<String,String> map = new HashMap<String, String>();
		map.put("par", p);
		return new ModelAndView("user/par",map);
	}
	@ResponseBody
	@RequestMapping("/info.json")
	public Map<String,String> jsonTest(){
		Map<String,String> map = new HashMap<String, String>();
		map.put("name", "hoyoung");
		return map;
	}
	
}
