package edu.columbia.dbmi.note2gene.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.columbia.dbmi.note2gene.pojo.ParseJob;
import edu.columbia.dbmi.note2gene.util.MetaMapProcess;
import edu.columbia.dbmi.note2gene.util.Obo;

@Controller
@RequestMapping("/parse")
public class parseController {
	Obo o = new Obo();
	
	@RequestMapping("/matmap")
	@ResponseBody
	public Map<String, Object> getTerm(@RequestBody ParseJob pj)
			throws Exception {
		
		HashMap<String, String> hmCui2Hpo = o.hmCui2Hpo;
		HashMap<String, String> hmHpo2Name = o.hmHpo2Name;

		MetaMapProcess mmp=new MetaMapProcess();
		System.out.println(pj.getNote());
		
		List<String> theOptions = pj.getOption();
		System.out.println("Metamap Options:");
		System.out.println(theOptions);
		String note = pj.getNote();
		Boolean hpoOption = pj.getGeneral().getHo();
		ArrayList<String[]> cui=mmp.getCUIbyRestrict(note,theOptions);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> hmName2Id = new HashMap<String, String>();
		for(String[] cuiname: cui) {
			if(cuiname[0].length() > 0) {
				if(hpoOption == true) {
					String cuiId = cuiname[1];
					System.out.println(cuiId);
					if (hmCui2Hpo.get(cuiId) != null) {
						// get hpo id and name.
						String hpoIdStr = hmCui2Hpo.get(cuiId);
						// in case one CUI mapping to multiple HPO.
						String[] hpoIdList = hpoIdStr.split("\\|");
						for(String hid: hpoIdList) {
							String hpoName = hmHpo2Name.get(hid);
							if(hpoName !=null) {
								System.out.println(hid);
								System.out.println(hpoName);
								hmName2Id.put(hpoName,hid);
							}else {
								System.out.println(hid + "not found in hmHpo2Name");
							}
							
						}
					}else {
						System.out.println(cuiId + "not found in hmCui2Hpo");
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
