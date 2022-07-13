package edu.columbia.dbmi.doc2hpo.pojo;

public class SplittedSentence {
	public String setence;
	public String getSetence() {
		return setence;
	}
	public void setSetence(String setence) {
		this.setence = setence;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int start;
	public int end;

}
