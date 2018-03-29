package edu.columbia.dbmi.note2gene.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.columbia.dbmi.note2gene.pojo.ParseJob;
import edu.columbia.dbmi.note2gene.util.MetaMapProcess;
import edu.columbia.dbmi.note2gene.util.Obo;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;
import uts.rest.samples.content.Hpoterm;

@Controller
@RequestMapping("/parse")
public class parseController {
	@RequestMapping("/matmap")
	@ResponseBody
	public Map<String, Object> getTerm(@RequestBody ParseJob pj)
			throws Exception {
		
//		System.out.println(pj.getNote());
		MetaMapProcess mmp=new MetaMapProcess();
		System.out.println(pj.getNote());
		HashMap<String, String> hmCui2Hpo = new HashMap<String, String>();
		HashMap<String, String> hmHpo2Name = new HashMap<String, String>();
		List<String> theOptions = pj.getOption();
		System.out.println("Metamap Options:");
		System.out.println(theOptions);
		String note = pj.getNote();
		Boolean hpoOption = pj.getGeneral().getHo();
		if (hpoOption == true) {
//			String fileName = "dictionary/hpo.obo";
//			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//			File obo = new File(classLoader.getResource(fileName).getFile());
			File obo = ResourceUtils.getFile("classpath:dictionary/hpo.obo");
			//File is found
			System.out.println("File Found : " + obo.exists());
			
			Obo o = new Obo(obo);
			hmCui2Hpo = o.Cui2Hpo();
			hmHpo2Name = o.Hpo2Name();
		}
		ArrayList<String[]> cui=mmp.getCUIbyRestrict(note,theOptions);
		Map<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> hmName2Id = new HashMap<String, String>();
		Set<String> terms = new HashSet<String>();
		for(String[] cuiname: cui) {
			if(cuiname[0].length() > 0) {
				if(hpoOption == true) {
					String cuiId = cuiname[1];
					System.out.println(cuiId);
					if (hmCui2Hpo.get(cuiId) != null) {
						// get hpo id and name.
						String hpoId = hmCui2Hpo.get(cuiId);
						String hpoName = hmHpo2Name.get(hpoId);
						hmName2Id.put(hpoName,hpoId);
					}else {
						System.out.println(cuiId + "not found in hmCui2Hpo");
						String cuiName = cuiname[0];
						cuiId = cuiname[1];
					}
				}else {
					String cuiName = cuiname[0];
					String cuiId = cuiname[1];
					hmName2Id.put(cuiName, cuiId);
				}
			}
		}
		map.put("hmName2Id", hmName2Id);
		map.put("hpoOption", hpoOption);
		return map;
	}
}
