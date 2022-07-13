package edu.columbia.dbmi.doc2hpo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;


import edu.columbia.dbmi.doc2hpo.pojo.ParseJob;
import edu.columbia.dbmi.doc2hpo.pojo.ParsingResults;
import edu.columbia.dbmi.doc2hpo.service.ACTrieParser;
import edu.columbia.dbmi.doc2hpo.service.MetaMapLiteParser;
import edu.columbia.dbmi.doc2hpo.service.NcboParser;

@CrossOrigin
@Controller
@RequestMapping("/parse")
public class ParseController {
	private static Logger logger = Logger.getLogger(ParseController.class);
	private NcboParser ncbo;
	private ACTrieParser actp;
	private MetaMapLiteParser mmlp;


	@Value("#{configProperties['NcboApiKey']}")
	private String NcboApiKey;
	
	@Value("#{configProperties['Proxy']}")
	private String proxy;
	
	@Value("#{configProperties['Port']}")
	private String port;
	
	@Value("#{configProperties['NcboUrl']}")
	private String ncboUrl;
	
	@Value("#{configProperties['metamapliteDataRoot']}")
	private String metamapliteDataRoot;

	@PostConstruct
	public void init() throws FileNotFoundException, ClassNotFoundException, InstantiationException, NoSuchMethodException, IllegalAccessException, IOException {
		this.mmlp = new MetaMapLiteParser(metamapliteDataRoot);
		this.actp = new ACTrieParser();
		if(this.ncboUrl.trim().toLowerCase().equals("null")) {
			this.ncboUrl = "http://data.bioontology.org"; // default using public ncbo api.
		}
		logger.info("[ncboUrl]["+this.ncboUrl+"]");
		if(this.proxy.trim().toLowerCase().equals("null")) {
			this.ncbo = new NcboParser(this.NcboApiKey,this.ncboUrl);
		}else {
			this.ncbo = new NcboParser(this.NcboApiKey,this.ncboUrl,this.proxy,this.port);
		}
	}

	@RequestMapping("/acdat")
	@ResponseBody
	public Map<String, Object> getTerm1(HttpSession httpSession, @RequestBody ParseJob pj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<ParsingResults> hmName2Id = new ArrayList<ParsingResults>();
			String content = pj.getNote();
			boolean negex = pj.isNegex();
			boolean partial = pj.isAllowPartial();
			hmName2Id = this.actp.parse(this.actp, content, negex, partial);
			
			httpSession.setAttribute("hmName2Id", hmName2Id);
			map.put("hmName2Id", hmName2Id);
			map.put("hpoOption", false);
			return map;
		}catch (Exception e) {
			e.printStackTrace();
			map.put("hmName2Id", "ERROR");
		}
		return map;
		
	}

	@RequestMapping("/metamaplite")
	@ResponseBody
	public Map<String, Object> getTerm2(HttpSession httpSession, @RequestBody ParseJob pj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ParsingResults> hmName2Id = new ArrayList<ParsingResults>();
			String content = pj.getNote();
			boolean negex = pj.isNegex();
			
			hmName2Id = this.mmlp.parse(content,negex);
			httpSession.setAttribute("hmName2Id", hmName2Id);
			map.put("hmName2Id", hmName2Id);
			map.put("hpoOption", false);
		}catch (Exception e) {
			e.printStackTrace();
			map.put("hmName2Id", "ERROR");
		}
		return map;
		
	}
	
	@RequestMapping("/ncbo")
	@ResponseBody
	public Map<String, Object> getTerm3(HttpSession httpSession, @RequestBody ParseJob pj) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println(pj);
			List<ParsingResults> hmName2Id = new ArrayList<ParsingResults>();
			List<String> theOptions = pj.getOption();
			
			String content = pj.getNote();
			boolean negex = pj.isNegex();
			hmName2Id = this.ncbo.parse(content, theOptions,negex);
			httpSession.setAttribute("hmName2Id", hmName2Id);
			map.put("hmName2Id", hmName2Id);
			map.put("hpoOption", false);
		}catch (Exception e) {
			e.printStackTrace();
			map.put("hmName2Id", "ERROR");
		}
		return map;
	}
	
	@RequestMapping("/ensemble")
	@ResponseBody
	public Map<String, Object> getTerm4(HttpSession httpSession, @RequestBody ParseJob pj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ParsingResults> hmName2Id1 = new ArrayList<ParsingResults>();
			List<ParsingResults> hmName2Id2 = new ArrayList<ParsingResults>();
			List<ParsingResults> hmName2Id3 = new ArrayList<ParsingResults>();

			String content = pj.getNote();
			boolean negex = pj.isNegex();

			List<String> options = new ArrayList<String>();
			options.add("longest_only=true");
			options.add("whole_word_only=false");
			options.add("exclude_numbers=true");

		
			hmName2Id1 = this.mmlp.parse(content,negex);
			hmName2Id2 = this.actp.parse(this.actp, content, negex, false);
			hmName2Id3 = this.ncbo.parse(content, options, negex);

			
			List<ParsingResults> hmName2Id = ListUtils.union(ListUtils.union(hmName2Id1, hmName2Id2),hmName2Id3);
			
			httpSession.setAttribute("hmName2Id", hmName2Id);
			map.put("hmName2Id", hmName2Id);
			map.put("hpoOption", false);
		}catch (Exception e) {
			e.printStackTrace();
			map.put("hmName2Id", "ERROR");
		}
		return map;
		
	}
	
}
