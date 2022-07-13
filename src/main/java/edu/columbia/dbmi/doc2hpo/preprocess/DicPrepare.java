package edu.columbia.dbmi.doc2hpo.preprocess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.columbia.dbmi.doc2hpo.util.FileUtil;


public class DicPrepare {

	public static void main(String[] args) {
		String voca=FileUtil.Readfile("/Users/cy2465/Downloads/hp.obo.txt");
		//System.out.println("voca="+voca);
		String[] terms=voca.split("\\[Term\\]");
		StringBuffer sb=new StringBuffer();
		List<HPOTerm> hpotlist=new ArrayList<HPOTerm>();
		for(String t:terms){
			String[] lines=t.split("\n");
			HPOTerm hpo=new HPOTerm();
			Set<String> xrefs=new HashSet<String>();
			Set<String> synonyms=new HashSet<String>();
			for(String l:lines){
				if(l.startsWith("id:")){
					hpo.id=l.split("id:")[1];
				}
				else if(l.startsWith("name:")){
					hpo.name=l.split("name:")[1];
				}
				else if(l.startsWith("def:")){
					hpo.def=l.split("def:")[1];
				}
				else if(l.startsWith("synonym:")){
					String cansyn=l.split("synonym:")[1];
					String[] psyn=cansyn.split("\"");
					synonyms.add(psyn[1]);
				}
				else if(l.startsWith("xref:")){
					xrefs.add(l.split("xref:")[1]);
				}
				hpo.synonyms=synonyms;
				hpo.xrefs=xrefs;	
			}
			hpotlist.add(hpo);
		}
		for(HPOTerm hpt:hpotlist){
			System.out.println(hpt.id+"\t"+hpt.name);
			if(hpt.name!=null && hpt.id!=null){
			sb.append(hpt.name.trim()+"\t"+hpt.id.trim()+"\n");
			}
			for(String syn:hpt.synonyms){
				System.out.println("syn="+syn);
				sb.append(syn.trim()+"\t"+hpt.id.trim()+"\n");
			}
		}
		//System.out.println(sb.toString());
		FileUtil.write2File("/Users/cy2465/Downloads/hpoterms.txt", sb.toString());
	}

}
