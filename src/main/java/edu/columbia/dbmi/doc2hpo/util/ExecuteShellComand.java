package edu.columbia.dbmi.doc2hpo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

public class ExecuteShellComand {
	public static void main(String[] args) {
		
//		String perl = "perl ";
//		String toJson = "transform_to_json.pl";
//		String command = perl + toJson;
//		List<String> cmd = new ArrayList<String>(Arrays.asList(command.split(" ")));
//		ExecuteShellComand esc = new ExecuteShellComand();
//		String out = esc.executeCommand2(cmd,theDir);		
//		List<String> cmd = new ArrayList<String>(Arrays.asList(command.split(" ")));
		
//		File theDir = new File("/Users/congliu/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/doc2hpo/WEB-INF/work/asdfghjkl/");
//		File phenolyzerDir = new File("/Users/congliu/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/doc2hpo/WEB-INF/scripts/");
//
//		String perl = "perl ";
//		String toJsonBin = phenolyzerDir + "/transform_to_json.pl";
//		String command = perl + toJsonBin;
//		List<String> cmd = new ArrayList<String>(Arrays.asList(command.split(" ")));
//		ExecuteShellComand esc = new ExecuteShellComand();
//		System.out.println(command);
//		String out = esc.executeCommand2(cmd,theDir);

//		String command = phenolyzerBin + input + parameters + output;
//		String command = "ls";
//		List<String> command = new ArrayList<String>();
//		command.add("ls");
//		command.add("-lh");
//				
//		File dir = new File("/Users/congliu/Project/tools_and_tests/d3-wordcloud");
//		System.out.println(dir);
//		System.out.println(command);
//
//		String cmd = obj.executeCommand(command);
//		String cmd = obj.executeCommand2(command, dir);
//
//		System.out.println(cmd);

	}

	public String executeCommand(String command) {
		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            System.out.println(reader.readLine());
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}
	
	public String executeCommand2(List<String> command, File dir) {
		StringBuffer output = new StringBuffer();

		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.directory(dir);
			Process p = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            System.out.println(reader.readLine());
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}

}
