package edu.columbia.dbmi.doc2hpo.pojo;

import java.util.ArrayList;
import java.util.List;

public class ParseJob {
	private String note;
	private MmpGeneral mmpgeneral;
	private NcboGeneral ncbogeneral;
	private Semantic semantic;


	public String getNote() {
		return note;
	}






	public void setNote(String note) {
		this.note = note;
	}






	public MmpGeneral getMmpgeneral() {
		return mmpgeneral;
	}






	public void setMmpgeneral(MmpGeneral mmpgeneral) {
		this.mmpgeneral = mmpgeneral;
	}






	public NcboGeneral getNcbogeneral() {
		return ncbogeneral;
	}






	public void setNcbogeneral(NcboGeneral ncbogeneral) {
		this.ncbogeneral = ncbogeneral;
	}






	public Semantic getSemantic() {
		return semantic;
	}






	public void setSemantic(Semantic semantic) {
		this.semantic = semantic;
	}






	public List<String> getOption(){
		List<String> options = new ArrayList<String>();
		if(this.mmpgeneral!=null) {
			if(this.mmpgeneral.getAaa() == true) {
				options.add("-a");
			}
			if(this.mmpgeneral.getAcg() == true) {
				options.add("-g");
			}
			if(this.mmpgeneral.getIsp() == true) {
				options.add("-K");
			}
			if(this.mmpgeneral.getIwo() == true) {
				options.add("-i");
			}
			if(this.mmpgeneral.getHo() == true) {
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
		}
		
		if(this.ncbogeneral!=null) {
			options.add("ontologies=HP");
			if(this.ncbogeneral.getLo() == true) {
				options.add("longest_only=true");
			}else {
				options.add("longest_only=false");
			}
			if(this.ncbogeneral.getWwo() == true) {
				options.add("whole_word_only=true");
			}else {
				options.add("whole_word_only=false");
			}
			if(this.ncbogeneral.getEn() == true) {
				options.add("exclude_numbers=true");
			}else {
				options.add("exclude_numbers=false");

			}
		}
		return options;
	}
}
