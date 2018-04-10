package edu.columbia.dbmi.note2gene.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import edu.columbia.dbmi.io.FileHelper;
//import edu.columbia.dbmi.pojo.Cphrase;
//import edu.columbia.dbmi.pojo.PhraseLevel;
//import edu.columbia.dbmi.pojo.WordLevel;
import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Negation;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Position;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;

public class MetaMapProcess {
	String metamapBinPath;
	public static void main(String[] args) {
		try {
			MetaMapProcess mmp=new MetaMapProcess();
			List<String> theOptions = new ArrayList<String>();
			theOptions.add(" -I -p -J -K -8 --conj cgab,genf,lbpr,lbtr,patf,dsyn,fndg");
			String currentDir = System.getProperty("user.dir");
			File inputFile = new File(currentDir + "/src/main/resources/examples/mm_note.txt");
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String strLine = null;
			try {
				strLine = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String input = "";
			while(strLine!=null) {
				input += strLine;
				try {
					strLine = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			input = "he was profoundly deaf and had completely lost the ability to speak.";
//			String mmpResult = mmp.runCmdMetamap(input, theOptions);
//			System.out.println(mmpResult);
//			HashMap<String, String>hmCui = mmp.extractCui(mmpResult);
//			for(String cui : hmCui.keySet()) {
//				System.out.println(cui+";"+hmCui.get(cui) + "\n");
//			}
			theOptions = new ArrayList<String>();
			theOptions.add("-y");
			ArrayList<String[]> cui = mmp.getCUIbyRestrict(input, theOptions);
			for(String[] c: cui) {
				System.out.println("h");
				System.out.println(Arrays.toString(c));
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MetaMapProcess() {
		
	}
	
	public MetaMapProcess(String mmpBin) {
		this.metamapBinPath = mmpBin;
	}
	public String runCmdMetamap(String input, List<String> theOptions) {
		String optionStr = "";
		theOptions.add("-I"); // with cui.
		for (String s : theOptions)
		{
			optionStr += s + " ";
		}

		// specifiy metamap bin.
//		String currentDir = System.getProperty("user.dir");
		//File metamapDir = new File("/Users/congliu/public_mm/bin");		
		String command = metamapBinPath+"/metamap16 " + optionStr;
		System.out.println(command);
		RunBashCommand rbc = new RunBashCommand();
		String resultByName = rbc.runCommand(command,input);
		System.out.println(resultByName);
		return resultByName; 
	}
	
	public HashMap<String,String> extractCui(String output){
		HashMap<String,String> hmCui = new HashMap<String,String>();
		String[] outputLine = output.split("\n");
		for(String l: outputLine) {
			String regexTerm1 = "^Processing.*"; 
			String regexTerm2 = "^Meta Mapping.*"; 
			String regexTerm3 = ".*metamap.*"; 
			String regexTerm4 = ".*:.*"; 

			Pattern pTerm1 = Pattern.compile(regexTerm1, Pattern.CASE_INSENSITIVE);
			Pattern pTerm2 = Pattern.compile(regexTerm2, Pattern.CASE_INSENSITIVE);
			Pattern pTerm3 = Pattern.compile(regexTerm3, Pattern.CASE_INSENSITIVE);
			Pattern pTerm4 = Pattern.compile(regexTerm4, Pattern.CASE_INSENSITIVE);

			Matcher mTerm1 = pTerm1.matcher(l);
			Matcher mTerm2 = pTerm2.matcher(l);
			Matcher mTerm3 = pTerm3.matcher(l);
			Matcher mTerm4 = pTerm4.matcher(l);

			if(mTerm1.matches() || mTerm2.matches() || mTerm3.matches()) {
				continue;
			}
			if(!mTerm4.matches()) {
				continue;
			}
			
			System.out.println(l);
			String[] tempStr = l.split(":");
			String[] cuiStr = tempStr[0].split(" ");
			String cui = cuiStr[cuiStr.length-1];
			System.out.println(tempStr[0]);
			System.out.println(tempStr[1]);
			String[] cuiNameStr = tempStr[1].split("\\[");
			String[] cuiNameArray = Arrays.copyOfRange(cuiNameStr, 0, cuiNameStr.length-1);
			String cuiName = String.join(" ", cuiNameArray);
			if(hmCui.get(cui)==null) {
				hmCui.put(cui,cuiName);
			}

		}
		return hmCui;
		
	}
	public static String machineOutput(String input) {
		MetaMapApi api = new MetaMapApiImpl();
		api.setOptions("-A");
		api.setOptions("-b");
		List<Result> resultList = api.processCitationsFromString(input);
		Result result = resultList.get(0);
		String machineOutput = result.getMachineOutput();
		return machineOutput;
		
	}
	
	public static String testMetamap(String str) throws Exception {
		MetaMapApi api = new MetaMapApiImpl();
		api.setOptions("-y");
		List<Result> resultList = api.processCitationsFromString(str);
		for (int i = 0; i < resultList.size(); i++) {
			System.out.println(resultList.get(i));
		}
		Result result = resultList.get(0);
		System.out.println("result=" + result);
		List<Negation> negList = result.getNegations();
		if (negList.size() > 0) {
			System.out.println("Negations:");
			for (Negation e : negList) {
				System.out.println("type: " + e.getType());
				System.out.print("Trigger: " + e.getTrigger() + ": [");
				for (Position pos : e.getTriggerPositionList()) {
					System.out.print(pos + ",");
				}
				System.out.println("]");
				System.out.print("ConceptPairs: [");
				
				System.out.println("]");
				System.out.print("ConceptPositionList: [");
				for (Position pos : e.getConceptPositionList()) {
					System.out.print(pos + ",");
				}
				System.out.println("]");
			}
		} else {
			System.out.println(" None.");
		}

		List<AcronymsAbbrevs> aaList = result.getAcronymsAbbrevs();
		if (aaList.size() > 0) {
			System.out.println("Acronyms and Abbreviations:");
			for (AcronymsAbbrevs e : aaList) {
				System.out.println("Acronym: " + e.getAcronym());
				System.out.println("Expansion: " + e.getExpansion());
				System.out.println("Count list: " + e.getCountList());
				System.out.println("CUI list: " + e.getCUIList());
			}
		} else {
			System.out.println(" None.");
		}
		return "";
	}
	
	public static ArrayList<String[]> getCUIbyRestrict(String input, List<String> theOptions) throws Exception{
		
	    ArrayList<String[]> al = new ArrayList<String[]>();

		MetaMapApi api = new MetaMapApiImpl(0);

		for(String opt: theOptions) {
			api.setOptions(opt);
		}

		List<Result> resultList = api.processCitationsFromString(input);

		Result result = resultList.get(0);
		for (Utterance utterance : result.getUtteranceList()) {
//			System.out.println("Utterance:");
//			System.out.println(" Id: " + utterance.getId());
//			System.out.println(" Utterance text: " + utterance.getString());
//			System.out.println(" Position: " + utterance.getPosition());

			for (PCM pcm : utterance.getPCMList()) {
//				System.out.println("Phrase:");
//				System.out.println(" text: " + pcm.getPhrase().getPhraseText());
//				System.out.println("Mappings:");
				for (Mapping map : pcm.getMappingList()) {
//					System.out.println(" Mapping:");
//					System.out.println(" Map Score: " + map.getScore());
					for (Ev mapEv : map.getEvList()) {
						String[] cuiname = new String[4];
//						
						cuiname[0] = mapEv.getConceptName();
						cuiname[1] = mapEv.getConceptId();
						cuiname[2] = mapEv.getSemanticTypes().toString();
						cuiname[3] = mapEv.getSources().toString();
//
						al.add(cuiname);
//						System.out.println(" Score: " + mapEv.getScore());
//						System.out.println(" Concept Id: " + mapEv.getConceptId());
//						System.out.println(" Concept Name: " + mapEv.getConceptName());
//						System.out.println(" Preferred Name: " + mapEv.getPreferredName());
//						System.out.println(" Matched Words: " + mapEv.getMatchedWords());
//						System.out.println(" Semantic Types: " + mapEv.getSemanticTypes());
//						System.out.println(" is Head?: " + mapEv.isHead());
//						System.out.println(" is Overmatch?: " + mapEv.isOvermatch());
//						System.out.println(" Sources: " + mapEv.getSources());
//						System.out.println(" Positional Info: " + mapEv.getPositionalInfo());
					}
				}
			}
		}
		api.getSession().disconnect();
		return al;
		// return null;
	}

	public static String[] getCUI2(String input) throws Exception {
		String[] cuiname = new String[3];
		cuiname[0] = "unmapped";
		cuiname[1] = "unmapped";
		cuiname[2] = "unmapped";

		MetaMapApi api = new MetaMapApiImpl(0);
		api.setOptions("-y");
		List<Result> resultList = api.processCitationsFromString(input);
		// System.out.println("input=" + input);
		// for (Result result: resultList) {
		Result result = resultList.get(0);
		for (Utterance utterance : result.getUtteranceList()) {
//			System.out.println("Utterance:");
//			System.out.println(" Id: " + utterance.getId());
//			System.out.println(" Utterance text: " + utterance.getString());
//			System.out.println(" Position: " + utterance.getPosition());

			for (PCM pcm : utterance.getPCMList()) {
//				System.out.println("Phrase:");
//				System.out.println(" text: " + pcm.getPhrase().getPhraseText());
//				System.out.println("Mappings:");
				for (Mapping map : pcm.getMappingList()) {
//					System.out.println(" Mapping:");
//					System.out.println(" Map Score: " + map.getScore());
					for (Ev mapEv : map.getEvList()) {
						cuiname[0] = mapEv.getConceptName();
						cuiname[1] = mapEv.getConceptId();
						cuiname[2] = mapEv.getSemanticTypes().toString();
//						System.out.println(" Score: " + mapEv.getScore());
//						System.out.println(" Concept Id: " + mapEv.getConceptId());
//						System.out.println(" Concept Name: " + mapEv.getConceptName());
//						System.out.println(" Preferred Name: " + mapEv.getPreferredName());
//						System.out.println(" Matched Words: " + mapEv.getMatchedWords());
//						System.out.println(" Semantic Types: " + mapEv.getSemanticTypes());
//						System.out.println(" is Head?: " + mapEv.isHead());
//						System.out.println(" is Overmatch?: " + mapEv.isOvermatch());
//						System.out.println(" Sources: " + mapEv.getSources());
//						System.out.println(" Positional Info: " + mapEv.getPositionalInfo());
					}
				}
			}
		}
		api.getSession().disconnect();
		return cuiname;
		// return null;

	}
	public static String getCUI(String input) throws Exception{
		String cui=new String();
		MetaMapApi api = new MetaMapApiImpl(0);
		List<Result> resultList = api.processCitationsFromString(input);
		//for (Result result: resultList) {
		Result result = resultList.get(0);
		for (Utterance utterance : result.getUtteranceList()) {
			System.out.println("Utterance:");
			System.out.println(" Id: " + utterance.getId());
			System.out.println(" Utterance text: " + utterance.getString());
			System.out.println(" Position: " + utterance.getPosition());

			for (PCM pcm : utterance.getPCMList()) {
				System.out.println("Phrase:");
				System.out.println(" text: " + pcm.getPhrase().getPhraseText());
				System.out.println("Candidates:");
				for (Ev ev : pcm.getCandidateList()) {
					System.out.println("  Score: " + ev.getScore());
					System.out.println("  Concept Id: " + ev.getConceptId());
					System.out.println("  Concept Name: " + ev.getConceptName());
					System.out.println("  Preferred Name: " + ev.getPreferredName());
					System.out.println("  Matched Words: " + ev.getMatchedWords());
					System.out.println("  Semantic Types: " + ev.getSemanticTypes());
					System.out.println("  MatchMap: " + ev.getMatchMap());
					System.out.println("  MatchMap alt. repr.: " + ev.getMatchMapList());
					System.out.println("  is Head?: " + ev.isHead());
					System.out.println("  is Overmatch?: " + ev.isOvermatch());
					System.out.println("  Sources: " + ev.getSources());
					System.out.println("  Positional Info: " + ev.getPositionalInfo());
				}
				System.out.println("Mappings:");
				for (Mapping map : pcm.getMappingList()) {
					System.out.println(" Mapping:");
					System.out.println(" Map Score: " + map.getScore());
					for (Ev mapEv : map.getEvList()) {
						
						cui = mapEv.getConceptId();
						
						System.out.println(" Score: " + mapEv.getScore());
						System.out.println(" Concept Id: " + mapEv.getConceptId());
						System.out.println(" Concept Name: " + mapEv.getConceptName());
						System.out.println(" Preferred Name: " + mapEv.getPreferredName());
						System.out.println(" Matched Words: " + mapEv.getMatchedWords());
						System.out.println(" Semantic Types: " + mapEv.getSemanticTypes());
						System.out.println(" is Head?: " + mapEv.isHead());
						System.out.println(" is Overmatch?: " + mapEv.isOvermatch());
						System.out.println(" Sources: " + mapEv.getSources());
						System.out.println(" Positional Info: " + mapEv.getPositionalInfo());
					}
				}
			
		}
		}
		api.getSession().disconnect();

		return cui;
		// return null;
	}

}
