package com.sy.investment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;
@ApiIgnore
@Controller
@RequestMapping({"","/index"})
public class IndexController extends BaseController {
	@RequestMapping()
	public String index() {
		return "forward:/index.html";
	}
}
