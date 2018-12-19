package edu.columbia.dbmi.doc2hpo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.columbia.dbmi.doc2hpo.pojo.ParseJob;
import edu.columbia.dbmi.doc2hpo.pojo.ParsingResults;
import edu.columbia.dbmi.doc2hpo.service.ACTrieParser;
import edu.columbia.dbmi.doc2hpo.service.MetaMapParser;
import edu.columbia.dbmi.doc2hpo.service.NcboParser;

@Controller
@RequestMapping("/parse")
public class ParseController {

	private MetaMapParser mmp;
	private NcboParser ncbo;
	private ACTrieParser actp;

	@Value("#{configProperties['MetamapBinPath']}")
	private String metamapBinPath;

	@Value("#{configProperties['NcboApiKey']}")
	private String NcboApiKey;
	
	@Value("#{configProperties['Proxy']}")
	private String proxy;
	
	@Value("#{configProperties['Port']}")
	private String port;
	
	@Value("#{configProperties['NcboUrl']}")
	private String ncboUrl;

	@PostConstruct
	public void init() {
		this.mmp = new MetaMapParser();
		this.actp = new ACTrieParser();
		if(this.ncboUrl.trim().toLowerCase().equals("null")) {
			this.ncboUrl = "http://data.bioontology.org"; // default using public ncbo api.
		}
		System.out.println(this.ncboUrl);
		if(this.proxy.trim().toLowerCase().equals("null")) {
			this.ncbo = new NcboParser(this.NcboApiKey,this.ncboUrl);
		}else {
			this.ncbo = new NcboParser(this.NcboApiKey,this.ncboUrl,this.proxy,this.port);
		}
	}

	@RequestMapping("/acdat")
	@ResponseBody
	public Map<String, Object> getTerm2(HttpSession httpSession, @RequestBody ParseJob pj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<ParsingResults> hmName2Id = new ArrayList<ParsingResults>();
			String content = pj.getNote();
			
			hmName2Id = this.actp.parse(this.actp, content);
			
			httpSession.setAttribute("hmName2Id", hmName2Id);
			map.put("hmName2Id", hmName2Id);
			map.put("hpoOption", false);
			return map;
		}catch (Exception e) {
			// TODO Auto-generated catch block
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
			List<ParsingResults> hmName2Id = new ArrayList<ParsingResults>();
			List<String> theOptions = pj.getOption();
			String content = pj.getNote();
			
			hmName2Id = this.ncbo.parse(content, theOptions);
			
			httpSession.setAttribute("hmName2Id", hmName2Id);
			map.put("hmName2Id", hmName2Id);
			map.put("hpoOption", false);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("hmName2Id", "ERROR");
		}
		return map;
	}

	@RequestMapping("/metamap")
	@ResponseBody
	public Map<String, Object> getTerm1(HttpSession httpSession, @RequestBody ParseJob pj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ParsingResults> hmName2Id = new ArrayList<ParsingResults>();
			List<String> theOptions = pj.getOption();
			String content = pj.getNote();
			
			hmName2Id = this.mmp.parse(content, theOptions);
			
			httpSession.setAttribute("hmName2Id", hmName2Id);
			map.put("hmName2Id", hmName2Id);
			map.put("hpoOption", false);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("hmName2Id", "ERROR");
		}
		return map;
		
	}
	
}
