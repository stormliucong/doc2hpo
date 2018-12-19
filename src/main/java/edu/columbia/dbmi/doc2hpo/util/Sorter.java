package edu.columbia.dbmi.doc2hpo.util;

import java.util.*;


// Utility class to sort the negation rules by length in descending order.
// Rules need to be matched by longest first because there is overlap between the
// RegEx of the rules.
// 

// Author: Imre Solti
// solti@u.washington.edu
// Date: 10/20/2008

public class Sorter {

	public ArrayList<String> sortRules(ArrayList<String> unsortedRules) {

		try {
			for (int i = 0; i < unsortedRules.size() - 1; i++) {
				for (int j = i + 1; j < unsortedRules.size(); j++) {
					String a = (String) unsortedRules.get(i);
					String b = (String) unsortedRules.get(j);
					if (a.trim().length() < b.trim().length()) {
						// Sorting into descending order by lebgth of string.
						unsortedRules.set(i, b);
						unsortedRules.set(j, a);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return unsortedRules;
	}
}
