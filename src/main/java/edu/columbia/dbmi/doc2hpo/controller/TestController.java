package edu.columbia.dbmi.doc2hpo.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import edu.columbia.dbmi.doc2hpo.pojo.ParsingResults;



@Controller
@RequestMapping("/session")
public class TestController {
	
	@RequestMapping("/test")
	@ResponseBody
	public List<ParsingResults> updateTerms(@RequestBody ParsingResults pr)
			throws Exception {
		System.out.println("updateTerms....");
		return null;
	}
}

