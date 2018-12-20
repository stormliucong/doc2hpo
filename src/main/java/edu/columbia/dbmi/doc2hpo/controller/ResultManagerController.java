package edu.columbia.dbmi.doc2hpo.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.columbia.dbmi.doc2hpo.pojo.ParsingResults;

@Controller
@RequestMapping("/session")
public class ResultManagerController {

	@RequestMapping(value = "/getTerms", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTerms(HttpSession httpSession) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		@SuppressWarnings("unchecked")
		List<ParsingResults> hmName2Id = (List<ParsingResults>) httpSession.getAttribute("hmName2Id");
		httpSession.setAttribute("hmName2Id", hmName2Id);
		map.put("hmName2Id", hmName2Id);
		return map;
	}

	@RequestMapping("/addTerms")
	@ResponseBody
	public Map<String, Object> addTerms(HttpSession httpSession, @RequestBody ParsingResults pr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		String hpoId = pr.getHpoId();
		String hpoName = pr.getHpoName();

		@SuppressWarnings("unchecked")
		List<ParsingResults> hmName2Id = (List<ParsingResults>) httpSession.getAttribute("hmName2Id");

		ParsingResults prIn = new ParsingResults();
		prIn.setHpoName(hpoName.toLowerCase());
		prIn.setHpoId(hpoId.replaceAll("_", ":"));
		prIn.setStart(pr.getStart());
		prIn.setLength(pr.getLength());
		hmName2Id.add(prIn);

		httpSession.setAttribute("hmName2Id", hmName2Id);
		map.put("hmName2Id", hmName2Id);
		return map;
	}

	@RequestMapping("/deleteTerms")
	@ResponseBody
	public Map<String, Object> deleteTerms(HttpSession httpSession, @RequestBody ParsingResults pr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		@SuppressWarnings("unchecked")
		List<ParsingResults> hmName2Id = (List<ParsingResults>) httpSession.getAttribute("hmName2Id");
		Iterator<ParsingResults> it = hmName2Id.iterator();
		while (it.hasNext()) {
			ParsingResults prIn = it.next();
			if (prIn.getStart() == pr.getStart() && prIn.getLength() == pr.getLength()) {
				it.remove();
			}
		}
		httpSession.setAttribute("hmName2Id", hmName2Id);
		map.put("hmName2Id", hmName2Id);
		return map;
	}

	@RequestMapping("/updateTerms")
	@ResponseBody
	public Map<String, Object> updateTerms(HttpSession httpSession, @RequestBody ParsingResults pr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		String hpoId = pr.getHpoId();
		String hpoName = pr.getHpoName();

		@SuppressWarnings("unchecked")
		List<ParsingResults> hmName2Id = (List<ParsingResults>) httpSession.getAttribute("hmName2Id");
		for (ParsingResults prIn : hmName2Id) {
			System.out.println(prIn.getHpoId());
			if (prIn.getStart() == pr.getStart() && prIn.getLength() == pr.getLength()) {
				prIn.setHpoName(hpoName.toLowerCase());
				prIn.setHpoId(hpoId.replaceAll("_", ":"));
			}
		}
		httpSession.setAttribute("hmName2Id", hmName2Id);
		map.put("hmName2Id", hmName2Id);
		return map;
	}
}
