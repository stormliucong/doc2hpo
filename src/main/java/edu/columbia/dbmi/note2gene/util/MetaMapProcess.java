package edu.columbia.dbmi.note2gene.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import edu.columbia.dbmi.io.FileHelper;
//import edu.columbia.dbmi.pojo.Cphrase;
//import edu.columbia.dbmi.pojo.PhraseLevel;
//import edu.columbia.dbmi.pojo.WordLevel;
import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.ConceptPair;
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
	public static void main(String[] args) {
		try {
			MetaMapProcess mmp=new MetaMapProcess();
			List<String> theOptions = new ArrayList<String>();
			theOptions.add("-y");
			ArrayList<String[]> cui=mmp.getCUIbyRestrict("The proband is a 4-yr-old female presenting with idiopathic epilepsy (10 to 15 seizures per day), cortical blindness, and developmental regression (Figs. 1 and 2; Supplemental Video 1). Her medical records show that she has no language or motor skills, is fed through a G tube, and has recurrent fevers and osteopenia. She had acute fractures of her distal radius and distal ulna bilaterally as well as on the left distal tibia and left distal fibula while on a ketogenic diet. Table 1 outlines her phenotypic features using Human Phenotype Ontology (HPO) terms. Before whole-exome sequencing (WES), an infantile epileptic encephalopathy panel that did not include SCN8A was ordered for this proband. The results showed a variant of uncertain significance in exon 3 of the GRIN2A gene that encodes the glutamate ionotropic receptor N-methyl-D-aspartate (NMDA)-type subunit 2A, resulting in an amino acid change from phenylalanine to isoleucine at position 183. Parents of the proband were tested for this variant and the unaffected father was found to carry this variant. This proband had a high-resolution whole-genome single-nucleotide polymorphism (SNP)/copy-number microarr",theOptions);
			for(String[] cuiname : cui) {
				System.out.println("cui="+cuiname[0]+"\t"+cuiname[1]+"\t"+cuiname[2] + "\n");
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
	    ArrayList<String[]> al = new ArrayList();

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
						cuiname[0] = mapEv.getConceptName();
						cuiname[1] = mapEv.getConceptId();
						cuiname[2] = mapEv.getSemanticTypes().toString();
						cuiname[3] = mapEv.getSources().toString();
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
