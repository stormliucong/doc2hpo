package edu.columbia.dbmi.doc2hpo.external;

import java.util.ArrayList;
import java.util.List;

public class PhenolyzerApi {

	public static void main(String[] args) {
		PhenolyzerApi phenolyzer = new PhenolyzerApi();
		List<String> terms = new ArrayList<String>();
		terms.add("Brain");
		terms.add("lung cancer");
		List<String[]> result = phenolyzer.process(terms);
	}

	public static List<String[]> process(List<String> terms) {
		// to be implemented.
		return null;
	}

}
