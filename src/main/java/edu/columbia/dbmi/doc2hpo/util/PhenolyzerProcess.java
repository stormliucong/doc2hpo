package edu.columbia.dbmi.doc2hpo.util;

import java.util.ArrayList;
import java.util.List;

import edu.columbia.dbmi.doc2hpo.external.PhenolyzerApi;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;

public class PhenolyzerProcess {
	public static void main(String[] args) {
		try {
			PhenolyzerProcess ph=new PhenolyzerProcess();
//			String str=FileHelper.Readfile("/Users/cy2465/Documents/project/3_Questionnaire/terms.txt");
//			String[] rows=str.split("\n");
//			StringBuffer sb=new StringBuffer();
//			for(String r:rows){
//				String[] att=r.split("\t");
//				String[] cui=mmp.getCUI2(att[0]);
//				System.out.println("cui="+cui[0]+"\t"+cui[1]+"\t"+cui[2]);
//				sb.append(r+"\t"+cui[0]+"\t"+cui[1]+"\t"+cui[2]+"\n");
//			}
			//FileHelper.write2File("/Users/cy2465/Documents/project/3_Questionnaire/terms-mapped.txt", sb.toString());
			List<String> theOptions = new ArrayList<String>();
			theOptions.add("-y");
			List<String> terms = new ArrayList<String>();
			terms.add("Brain");
			terms.add("lung cancer");
			ArrayList<String[]> geneInfo=ph.getFinalGene(terms,theOptions);
			for(String[] gene : geneInfo) {
				System.out.println("gene="+gene[0]+"\t"+gene[1]+"\t"+gene[2] + "\n");
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<String[]> getFinalGene(List<String> terms, List<String> theOptions) {
		PhenolyzerApi api = new PhenolyzerApi();
		return null;
	}
}
