package edu.columbia.dbmi.doc2hpo.pojo;

public class ParsingResults {
	int start;
	int length;
	String hpoId;
	String hpoName;
	boolean negated;
	
	public boolean isNegated() {
		return negated;
	}
	public void setNegated(boolean negated) {
		this.negated = negated;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getHpoId() {
		return hpoId;
	}
	public void setHpoId(String hpoId) {
		this.hpoId = hpoId;
	}
	public String getHpoName() {
		return hpoName;
	}
	public void setHpoName(String hpoName) {
		this.hpoName = hpoName;
	}

}
