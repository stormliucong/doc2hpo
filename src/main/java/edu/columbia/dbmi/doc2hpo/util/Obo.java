package edu.columbia.dbmi.doc2hpo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.python.antlr.PythonParser.print_stmt_return;
import org.springframework.util.ResourceUtils;

import com.jayway.jsonpath.internal.Path;

import jnr.ffi.Struct.id_t;
import jnr.ffi.Struct.in_addr_t;

public class Obo {
	public static void main (String[] args) throws IOException {
		Obo o = new Obo();
		HashMap<String, String> hmCui2Hpo = o.hmCui2Hpo;
		System.out.println(hmCui2Hpo);


	}
	
	public HashMap<String, String> hmCui2Hpo = new HashMap<String, String>();
	public HashMap<String, String> hmHpo2Name = new HashMap<String, String>();


	File oboFile;

	public Obo () {
		
		try {
			this.oboFile = ResourceUtils.getFile("classpath:dictionary/hpo.obo");
			this.hmCui2Hpo = Cui2Hpo();
			this.hmHpo2Name = Hpo2Name();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public HashMap<String, String> Cui2Name () throws IOException{
		HashMap<String, String> hmCui2Name = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.oboFile)));
		String newLine = br.readLine();
		String name = null;
		String Id = null;
		while(newLine != null) {
			newLine = newLine.trim();
			String namePattern = "^name: (.+?)$";
			String idPattern = "^xref: UMLS:(.+?)$";
			Pattern pName = Pattern.compile(namePattern, Pattern.CASE_INSENSITIVE);
			Pattern pId = Pattern.compile(idPattern, Pattern.CASE_INSENSITIVE);
			Matcher mName = pName.matcher(newLine);
			Matcher mId = pId.matcher(newLine);

			if (mName.matches()) {
				name = mName.group(1).trim();
			}
			if (mId.matches()) {
				Id = mId.group(1).trim();
				String test = hmCui2Name.get(Id);
				if(test == null) {
					hmCui2Name.put(Id, name);
				}else {
					name = test + "|" + name;
					hmCui2Name.put(Id, name);
					//System.out.println("WARNING: " + Id + " occurred more than once!");
				}
			}
			newLine = br.readLine();
		}
		br.close();
		return hmCui2Name;
		
	}
	
	public HashMap<String, String> Cui2Hpo () throws IOException{
		HashMap<String, String> hmCui2Hpo = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.oboFile)));
		String newLine = br.readLine();
		String cui = null;
		String hpo = null;
		while(newLine != null) {
			newLine = newLine.trim();
			String hpoPattern = "^id: (.+?)$";
			String cuiPattern = "^xref: UMLS:(.+?)$";
			Pattern pHpo = Pattern.compile(hpoPattern, Pattern.CASE_INSENSITIVE);
			Pattern pCui = Pattern.compile(cuiPattern, Pattern.CASE_INSENSITIVE);
			Matcher mHpo = pHpo.matcher(newLine);
			Matcher mCui = pCui.matcher(newLine);

			if (mHpo.matches()) {
				hpo = mHpo.group(1).trim();
			}
			if (mCui.matches()) {
				cui = mCui.group(1).trim();
				String test = hmCui2Hpo.get(cui);
				if(test == null) {
					hmCui2Hpo.put(cui, hpo);
				}else {
					hpo = test + "|" + hpo;
					hmCui2Hpo.put(cui, hpo);
					System.out.println("WARNING: " + cui + " occurred more than once!");
				}
			}
			newLine = br.readLine();
		}
		br.close();
		return hmCui2Hpo;
		
	}
	
	public HashMap<String, String> Hpo2Name () throws IOException {
		HashMap<String, String> hmHpo2Name = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.oboFile)));
		String newLine = br.readLine();
		String name = null;
		String Id = null;
		while(newLine != null) {
			newLine = newLine.trim();
			String idPattern = "^id: (.+?)$";
			String namePattern = "^name: (.+?)$";
			Pattern pName = Pattern.compile(namePattern, Pattern.CASE_INSENSITIVE);
			Pattern pId = Pattern.compile(idPattern, Pattern.CASE_INSENSITIVE);
			Matcher mName = pName.matcher(newLine);
			Matcher mId = pId.matcher(newLine);

			if (mId.matches()) {
				Id = mId.group(1).trim();
//				System.out.println(Id);
			}
			if (mName.matches()) {
				name = mName.group(1).trim();
//				System.out.println(name);
				String test = hmHpo2Name.get(Id);
				if(test == null) {
					hmHpo2Name.put(Id, name);
				}else {
					name = test + "|" + name;
					hmHpo2Name.put(Id, name);
					//System.out.println("WARNING: " + Id + " occurred more than once!");
				}
			}
			newLine = br.readLine();
		}
		br.close();

		return hmHpo2Name;
	}
	
	public HashMap<String, String> Syn2Name () throws IOException {
		HashMap<String, String> hmSyn2Name = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.oboFile)));
		String newLine = br.readLine();
		String name = null;
		String sName = null;
		while(newLine != null) {
			newLine = newLine.trim();
			String namePattern = "^name: (.+?)$";
			String synPattern = "^synonym: (.+?)$";
			Pattern pName = Pattern.compile(namePattern, Pattern.CASE_INSENSITIVE);
			Pattern pSyn = Pattern.compile(synPattern, Pattern.CASE_INSENSITIVE);
			Matcher mName = pName.matcher(newLine);
			Matcher mSyn = pSyn.matcher(newLine);

			if (mName.matches()) {
				name = mName.group(1).trim();
			}
			if (mSyn.matches()) {
				sName = mSyn.group(1).trim();
				String test = hmSyn2Name.get(sName);
				if(test == null) {
					hmSyn2Name.put(sName, name);
				}else {
					name = test + "|" + name;
					hmSyn2Name.put(sName, name);
					//System.out.println("WARNING: " + sName + " occurred more than once!");
				}
			}
			newLine = br.readLine();
		}
		br.close();

		return hmSyn2Name;
	}
	
	public HashMap<String, String> Name2Hpo () throws IOException {
		
		HashMap<String, String> hmHpo2Name = new HashMap<String, String>();
		hmHpo2Name = this.Hpo2Name();
		HashMap<String, String> hmName2Hpo = new HashMap<String, String>();
		for(String hpo : hmHpo2Name.keySet()) {
			String name = hmHpo2Name.get(hpo);
			if(hmName2Hpo.get(name) != null) {
				//System.out.println("WARNING: " + name + " occurred more than once!");
			}else {
				hmName2Hpo.put(name, hpo);
			}
		}
		return hmName2Hpo;
	}
	
}
	