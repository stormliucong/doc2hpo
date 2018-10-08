package edu.columbia.dbmi.doc2hpo.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public class Test {
	@Value("#{configProperties['metamapBinPath']}")
    private String metamapBinPath;
	
	public static void main (String[] args) {
		String x = "abc";
		String[] mList = x.split("\\|");
		for(String m: mList) {
			System.out.println(m);
		}
	}
	String a;
	String b;
	public Test(){
		a = "1";
		b = "2";
	}
	
}
