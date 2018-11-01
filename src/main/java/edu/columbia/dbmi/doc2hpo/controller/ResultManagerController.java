package edu.columbia.dbmi.doc2hpo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.columbia.dbmi.doc2hpo.pojo.ParsingResults;

@Controller
@RequestMapping("/session")
public class ResultManagerController {
	

	@RequestMapping("/addTerms")
	@ResponseBody
	public List<ParsingResults> addTerms(HttpSession httpSession, @RequestBody ParsingResults pr)
			throws Exception {
		
		List<ParsingResults> hmName2Id = (List<ParsingResults>) httpSession.getAttribute("hmName2Id");
		hmName2Id.add(pr);
		httpSession.setAttribute("hmName2Id",hmName2Id);
		return hmName2Id;
	}
	
	@RequestMapping("/deleteTerms")
	@ResponseBody
	public List<ParsingResults> deleteTerms(HttpSession httpSession, @RequestBody ParsingResults pr)
			throws Exception {

		
		List<ParsingResults> hmName2Id = (List<ParsingResults>) httpSession.getAttribute("hmName2Id");
		for(ParsingResults prIn : hmName2Id) {
			if(prIn.getStart() == pr.getStart() && prIn.getLength() == pr.getLength()) {
				hmName2Id.remove(prIn);
			}
		}
		httpSession.setAttribute("hmName2Id",hmName2Id);
		return hmName2Id;
	}
	
	@RequestMapping("/updateTerms")
	@ResponseBody
	public List<ParsingResults> updateTerms(HttpSession httpSession, @RequestBody ParsingResults pr)
			throws Exception {

		String hpoId = pr.getHpoId();
		String hpoName = pr.getHpoName();
		
		List<ParsingResults> hmName2Id = (List<ParsingResults>) httpSession.getAttribute("hmName2Id");
		for(ParsingResults prIn : hmName2Id) {
			if(prIn.getStart() == pr.getStart() && prIn.getLength() == pr.getLength()) {
				prIn.setHpoName(hpoName);
				prIn.setHpoId(hpoId);
			}
		}
		httpSession.setAttribute("hmName2Id",hmName2Id);
		return hmName2Id;
	}
}
