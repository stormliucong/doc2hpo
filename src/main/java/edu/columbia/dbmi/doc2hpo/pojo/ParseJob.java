package edu.columbia.dbmi.doc2hpo.pojo;

import java.util.ArrayList;
import java.util.List;

public class ParseJob {
	private String note;
	private boolean negex;
	private boolean allowPartial;
	private NcboGeneral ncbogeneral;
	private Semantic semantic;
	
	public boolean isAllowPartial() {
		return allowPartial;
	}
	public void setAllowPartial(boolean allowPartial) {
		this.allowPartial = allowPartial;
	}
	public boolean isNegex() {
		return negex;
	}
	public void setNegex(boolean negex) {
		this.negex = negex;
	}

	public String getNote() {
		return note;
	}



	


	public void setNote(String note) {
		this.note = note;
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
