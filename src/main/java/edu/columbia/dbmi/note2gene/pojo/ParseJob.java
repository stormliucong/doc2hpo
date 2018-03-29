package edu.columbia.dbmi.note2gene.pojo;

import java.util.ArrayList;
import java.util.List;

public class ParseJob {
	private String note;
	private General general;
	private Semantic semantic;

	
	
	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public General getGeneral() {
		return general;
	}



	public void setGeneral(General general) {
		this.general = general;
	}



	public Semantic getSemantic() {
		return semantic;
	}



	public void setSemantic(Semantic semantic) {
		this.semantic = semantic;
	}



	public List<String> getOption(){
		List<String> options = new ArrayList<String>();
		options.add("-y");
		options.add("-8");

		if(this.general.getAaa() == true) {
			options.add("-a");
		}
		if(this.general.getAcg() == true) {
			options.add("-g");
		}
		if(this.general.getIsp() == true) {
			options.add("-K");
		}
		if(this.general.getIwo() == true) {
			options.add("-i");
		}
		if(this.general.getHo() == true) {
			options.add("-R HPO");
		}
		List<String> semanticStr = new ArrayList<String>();
		Boolean semantionTurnOn = false;
		if(this.semantic.getAnab() == true) {
			semanticStr.add("anab");
			semantionTurnOn = true;
		}
		if(this.semantic.getFndg() == true) {
			semanticStr.add("fndg");
			semantionTurnOn = true;
		}
		if(this.semantic.getCgab() == true) {
			semanticStr.add("cgab");
			semantionTurnOn = true;
		}
		if(this.semantic.getDsyn() == true) {
			semanticStr.add("dsyn");
			semantionTurnOn = true;
		}
		if(this.semantic.getGenf() == true) {
			semanticStr.add("genf");
			semantionTurnOn = true;
		}
		if(this.semantic.getMobd() == true) {
			semanticStr.add("mobd");
			semantionTurnOn = true;
		}
		if(this.semantic.getSosy() == true) {
			semanticStr.add("sosy");
			semantionTurnOn = true;
		}
		if(this.semantic.getLbtr() == true) {
			semanticStr.add("lbtr");
			semantionTurnOn = true;
		}
		if(this.semantic.getPatf() == true) {
			semanticStr.add("patf");
			semantionTurnOn = true;
		}
		
		if(semantionTurnOn == true) {
			options.add("-J " + String.join(",", semanticStr));
		}
		return options;
	}
}
