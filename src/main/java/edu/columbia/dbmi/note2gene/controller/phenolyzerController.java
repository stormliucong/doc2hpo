package edu.columbia.dbmi.note2gene.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import edu.columbia.dbmi.note2gene.util.ExecuteShellComand;
import edu.columbia.dbmi.note2gene.util.MetaMapProcess;
import edu.columbia.dbmi.note2gene.util.PhenolyzerProcess;

@Controller
@RequestMapping("/gene")
public class phenolyzerController {
	@RequestMapping("/phenolyzer")
	@ResponseBody
	public Map<String, Object> diseaseAnnotator(HttpSession httpSession, HttpServletRequest request, String terms)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

//		String sessionId = request.getSession().getId().toString();
		
		// for test purpose.
		String sessionId = "asdfghjkl";
		// for test purpose.
		
		ServletContext context = request.getServletContext();
		File webinfFolder = new File(context.getRealPath("/WEB-INF/work"));
		String workDir = webinfFolder + "/" + sessionId;
		System.out.println(workDir);
		File phenolyzerDir = new File(context.getRealPath("/WEB-INF/scripts"));
		File theDir = new File(workDir);
		
		boolean result = false;
		try {
			if (theDir.exists()) {
				FileUtils.forceDelete(theDir);
				System.out.println("delete directory: " + theDir.getName());
			}
			theDir.mkdir();
			System.out.println("creating directory: " + theDir.getName());
			
		} catch(SecurityException se){
			
		}
		if(result) {
			System.out.println("workdir created");
		}
		
		result = false;
		try {
			String fileName = workDir + "/input_terms.txt";
			System.out.println(fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(terms);
			writer.close();
			result = true;
		} catch (SecurityException se) {
			// handle it
		}
		if (result) {
			System.out.println("input file created");
		}
		
		result = false;
		try {
			String input = "input_terms.txt ";
			String output = "-out out ";
			String phenolyzerBin = phenolyzerDir + "/disease_annotation.pl ";
			String parameters = "-f -p -ph -logistic -addon DB_DISGENET_GENE_DISEASE_SCORE,DB_GAD_GENE_DISEASE_SCORE -addon_weight 0.25 ";
			String command = phenolyzerBin + input + parameters + output;
//			String command = "ls ";
			System.out.println(command);
			
			List<String> cmd = new ArrayList<String>(Arrays.asList(command.split(" ")));
			
			ExecuteShellComand esc = new ExecuteShellComand();
			String out = esc.executeCommand2(cmd,theDir);
			result = true;
			
		} catch (SecurityException se) {
			
		}
		if (result) {
			System.out.println("phenolyzer finished");
		}
		
		result = false;
		try {
			String perl = "perl ";
			String toJsonBin = phenolyzerDir + "/transform_to_json.pl";
			String command = perl + toJsonBin;
			System.out.println(command);
			List<String> cmd = new ArrayList<String>(Arrays.asList(command.split(" ")));
			ExecuteShellComand esc = new ExecuteShellComand();
			String out = esc.executeCommand2(cmd,theDir);
			result = true;
		} catch (SecurityException se) {
			
		}
		if (result) {
			System.out.println("json file created");
		}
		
		// System.out.print(sessionId);
		map.put("sessionId", sessionId);
		httpSession.setAttribute("dir", workDir);
		return map;
	}
	
	@RequestMapping("/phenolyzer2")
	@ResponseBody
	public Map<String, Object> phenolyzerAPI(HttpSession httpSession, HttpServletRequest request, String terms)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PhenolyzerProcess ph=new PhenolyzerProcess();
		return map;
		
	}

}
