package edu.columbia.dbmi.doc2hpo.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.columbia.dbmi.doc2hpo.pojo.ParsingResults;
import edu.columbia.dbmi.doc2hpo.pojo.SplittedSentence;
import edu.columbia.dbmi.doc2hpo.tool.CoreNLP;
import edu.columbia.dbmi.doc2hpo.util.NegUtil;
import edu.columbia.dbmi.doc2hpo.util.Obo;
import edu.columbia.dbmi.doc2hpo.util.RunBashCommand;
//import edu.columbia.dbmi.io.FileHelper;
//import edu.columbia.dbmi.pojo.Cphrase;
//import edu.columbia.dbmi.pojo.PhraseLevel;
//import edu.columbia.dbmi.pojo.WordLevel;
import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Negation;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Position;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;

public class MetaMapParser {
	String metamapBinPath;
	private Obo o;
	private HpoCleaner cleaner;
	CoreNLP corenlp;

	public static void main(String[] args) {
		// try {
		// MetaMapParser mmp = new MetaMapParser(metamapBinPath);
		// List<String> theOptions = new ArrayList<String>();
		// theOptions.add(" -I -p -J -K -8 --conj cgab,genf,lbpr,lbtr,patf,dsyn,fndg");
		// String currentDir = System.getProperty("user.dir");
		// File inputFile = new File(currentDir +
		// "/src/main/resources/examples/mm_note.txt");
		// BufferedReader br = null;
		// try {
		// br = new BufferedReader(new InputStreamReader(new
		// FileInputStream(inputFile)));
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// String strLine = null;
		// try {
		// strLine = br.readLine();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// String input = "";
		// while (strLine != null) {
		// input += strLine;
		// try {
		// strLine = br.readLine();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// try {
		// br.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// input = "he was profoundly deaf and had completely lost the ability to
		// speak.";
		//// String mmpResult = mmp.runCmdMetamap(input, theOptions);
		//// System.out.println(mmpResult);
		//// HashMap<String, String>hmCui = mmp.extractCui(mmpResult);
		//// for(String cui : hmCui.keySet()) {
		//// System.out.println(cui+";"+hmCui.get(cui) + "\n");
		//// }
		// theOptions = new ArrayList<String>();
		// theOptions.add("-y");
		// theOptions.add("-8");
		// ArrayList<String[]> cui = mmp.getCUIbyRestrict(input, theOptions);
		// for (String[] c : cui) {
		// System.out.println("h");
		// System.out.println(Arrays.toString(c));
		// }
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		CoreNLP corenlp = new CoreNLP();
		MetaMapParser mmp = new MetaMapParser(corenlp);
		String content = "He denies synophrys. Individual II-1 is a 10 year old boy. He was born at term with normal birth parameters and good APGAR scores (9/10/10). The neonatal period was uneventful, and he had normal motor development during early childhood: he began to look up at 3 months, sit by himself at 5 months, stand up at 11 months, walk at 13 months, and speak at 17 months. He attended a regular kindergarten, without any signs of difference in intelligence, compared to his peers. Starting at age 6, the parents observed ever increasing behavioral disturbance for the boy, manifesting in multiple aspects of life. For example, he can no longer wear clothes by himself, cannot obey instruction from parents/teachers, can no longer hold subjects tightly in hand, which were all things that he could do before 6 years of age. In addition, he no longer liked to play with others; instead, he just preferred to stay by himself, and he sometimes fell down when he walked on the stairs, which had rarely happened at age 5. The proband continued to deteriorate: at age 9, he could not say a single word and had no action or response to any instruction given in clinical exams. Additionally, rough facial features were noted with a flat nasal bridge, a synophrys (unibrow), a long and smooth philtrum, thick lips and an enlarged mouth. He also had rib edge eversion, and it was also discovered that he was profoundly deaf and had completely lost the ability to speak. He also had loss of bladder control. The diagnosis of severe intellectual disability was made, based on Wechsler Intelligence Scale examination. Brain MRI demonstrated cortical atrophy with enlargement of the subarachnoid spaces and ventricular dilatation (Figure 2). Brainstem evoked potentials showed moderate abnormalities. Electroencephalography (EEG) showed abnormal sleep EEG.";
		List<String> theOptions = new ArrayList<String>();
		theOptions.add("-y");
//		List<ParsingResults> pr = mmp.parse(content, theOptions);
		List<ParsingResults> pr = mmp.parseBySentence(corenlp, content, theOptions, true);
	}

	public List<ParsingResults> parseBySentence(CoreNLP corenlp, String content, List<String> theOptions,
			boolean negex) {
		// TODO Auto-generated method stub
		List<SplittedSentence> ss = corenlp.splitInputSimple(content);
		MetaMapApi api = new MetaMapApiImpl(0);
		if (theOptions.size() > 0) {
			api.resetOptions();
			api.setOptions(theOptions);
		}
		List<ParsingResults> pResults = new ArrayList<ParsingResults>();

		for (SplittedSentence s : ss) {

			// TODO Auto-generated method stub
			List<Result> resultList = api.processCitationsFromString(s.getSetence());
			Result result = resultList.get(0);
			try {
				for (Utterance utterance : result.getUtteranceList()) {
					for (PCM pcm : utterance.getPCMList()) {
						for (Mapping map : pcm.getMappingList()) {
							for (Ev mapEv : map.getEvList()) {
								String[] cuiname = new String[4];
								cuiname[0] = mapEv.getConceptName();
								cuiname[1] = mapEv.getConceptId();
								cuiname[2] = mapEv.getSemanticTypes().toString();
								cuiname[3] = mapEv.getSources().toString();
								//
								if (o.hmCui2Hpo.containsKey(cuiname[1])) {
									ParsingResults pr = new ParsingResults();
									String Id = o.hmCui2Hpo.get(cuiname[1]);
									if (Id.contains("|")) {
										Id = Id.split("\\|")[0];
									}
									String name = o.hmHpo2Name.get(Id);
									pr.setNegated(false);
									pr.setHpoId(Id);
									pr.setHpoName(name);
									List<Position> position = mapEv.getPositionalInfo();
									int[] pos = getLongestPosition(position);
									pr.setStart(pos[0] + s.getStart());
									pr.setLength(pos[1]);
									if (negex) {
										if (mapEv.getNegationStatus() == 1) {
											pr.setNegated(true);
										}
									}
									pResults.add(pr);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		pResults = cleaner.getPhenotypeOnly(pResults);
//		for (ParsingResults pr : pResults) {
//			System.out.println(pr.getHpoName() + "\t" + pr.getHpoId() + "\t" + pr.getStart() + "\t" + pr.getLength()
//					+ "\t" + pr.isNegated());
//		}
		return pResults;
	}

	public MetaMapParser(CoreNLP corenlp) {
		// System.out.println(mmpBin);
		// this.metamapBinPath = mmpBin;
		o = new Obo();
		cleaner = new HpoCleaner();
		this.corenlp = corenlp;

	}

	public MetaMapParser() {
		// System.out.println(mmpBin);
		// this.metamapBinPath = mmpBin;
		o = new Obo();
		cleaner = new HpoCleaner();

	}

	public String runCmdMetamap(String input, List<String> theOptions) {
		String optionStr = "";
		theOptions.add("-I"); // with cui.
		for (String s : theOptions) {
			optionStr += s + " ";
		}

		// specifiy metamap bin.
		// String currentDir = System.getProperty("user.dir");
		// File metamapDir = new File("/Users/congliu/public_mm/bin");
		String command = metamapBinPath + "/metamap16 " + optionStr;
		System.out.println(command);
		RunBashCommand rbc = new RunBashCommand();
		String resultByName = rbc.runCommand(command, input);
		System.out.println(resultByName);
		return resultByName;
	}

	public HashMap<String, String> extractCui(String output) {
		HashMap<String, String> hmCui = new HashMap<String, String>();
		String[] outputLine = output.split("\n");
		for (String l : outputLine) {
			String regexTerm1 = "^Processing.*";
			String regexTerm2 = "^Meta Mapping.*";
			String regexTerm3 = ".*metamap.*";
			String regexTerm4 = ".*:.*";

			Pattern pTerm1 = Pattern.compile(regexTerm1, Pattern.CASE_INSENSITIVE);
			Pattern pTerm2 = Pattern.compile(regexTerm2, Pattern.CASE_INSENSITIVE);
			Pattern pTerm3 = Pattern.compile(regexTerm3, Pattern.CASE_INSENSITIVE);
			Pattern pTerm4 = Pattern.compile(regexTerm4, Pattern.CASE_INSENSITIVE);

			Matcher mTerm1 = pTerm1.matcher(l);
			Matcher mTerm2 = pTerm2.matcher(l);
			Matcher mTerm3 = pTerm3.matcher(l);
			Matcher mTerm4 = pTerm4.matcher(l);

			if (mTerm1.matches() || mTerm2.matches() || mTerm3.matches()) {
				continue;
			}
			if (!mTerm4.matches()) {
				continue;
			}

			System.out.println(l);
			String[] tempStr = l.split(":");
			String[] cuiStr = tempStr[0].split(" ");
			String cui = cuiStr[cuiStr.length - 1];
			System.out.println(tempStr[0]);
			System.out.println(tempStr[1]);
			String[] cuiNameStr = tempStr[1].split("\\[");
			String[] cuiNameArray = Arrays.copyOfRange(cuiNameStr, 0, cuiNameStr.length - 1);
			String cuiName = String.join(" ", cuiNameArray);
			if (hmCui.get(cui) == null) {
				hmCui.put(cui, cuiName);
			}

		}
		return hmCui;

	}

	public static String machineOutput(String input) {
		MetaMapApi api = new MetaMapApiImpl();
		api.setOptions("-A");
		api.setOptions("-b");
		List<Result> resultList = api.processCitationsFromString(input);
		Result result = resultList.get(0);
		String machineOutput = result.getMachineOutput();
		return machineOutput;

	}

	public static String testMetamap(String str) throws Exception {
		MetaMapApi api = new MetaMapApiImpl();
		api.setOptions("-y");
		List<Result> resultList = api.processCitationsFromString(str);
		for (int i = 0; i < resultList.size(); i++) {
			System.out.println(resultList.get(i));
		}
		Result result = resultList.get(0);
		System.out.println("result=" + result);
		List<Negation> negList = result.getNegations();
		if (negList.size() > 0) {
			System.out.println("Negations:");
			for (Negation e : negList) {
				System.out.println("type: " + e.getType());
				System.out.print("Trigger: " + e.getTrigger() + ": [");
				for (Position pos : e.getTriggerPositionList()) {
					System.out.print(pos + ",");
				}
				System.out.println("]");
				System.out.print("ConceptPairs: [");

				System.out.println("]");
				System.out.print("ConceptPositionList: [");
				for (Position pos : e.getConceptPositionList()) {
					System.out.print(pos + ",");
				}
				System.out.println("]");
			}
		} else {
			System.out.println(" None.");
		}

		List<AcronymsAbbrevs> aaList = result.getAcronymsAbbrevs();
		if (aaList.size() > 0) {
			System.out.println("Acronyms and Abbreviations:");
			for (AcronymsAbbrevs e : aaList) {
				System.out.println("Acronym: " + e.getAcronym());
				System.out.println("Expansion: " + e.getExpansion());
				System.out.println("Count list: " + e.getCountList());
				System.out.println("CUI list: " + e.getCUIList());
			}
		} else {
			System.out.println(" None.");
		}
		return "";
	}

	public static ArrayList<String[]> getCUIbyRestrict(String input, List<String> theOptions) throws Exception {

		ArrayList<String[]> al = new ArrayList<String[]>();

		MetaMapApi api = new MetaMapApiImpl(0);

		for (String opt : theOptions) {
			api.setOptions(opt);
		}

		List<Result> resultList = api.processCitationsFromString(input);

		Result result = resultList.get(0);
		for (Utterance utterance : result.getUtteranceList()) {
			// System.out.println("Utterance:");
			// System.out.println(" Id: " + utterance.getId());
			// System.out.println(" Utterance text: " + utterance.getString());
			// System.out.println(" Position: " + utterance.getPosition());

			for (PCM pcm : utterance.getPCMList()) {
				// System.out.println("Phrase:");
				// System.out.println(" text: " + pcm.getPhrase().getPhraseText());
				// System.out.println("Mappings:");
				for (Mapping map : pcm.getMappingList()) {
					// System.out.println(" Mapping:");
					// System.out.println(" Map Score: " + map.getScore());
					for (Ev mapEv : map.getEvList()) {
						String[] cuiname = new String[4];
						//
						cuiname[0] = mapEv.getConceptName();
						cuiname[1] = mapEv.getConceptId();
						cuiname[2] = mapEv.getSemanticTypes().toString();
						cuiname[3] = mapEv.getSources().toString();
						//
						al.add(cuiname);
						// System.out.println(" Score: " + mapEv.getScore());
						// System.out.println(" Concept Id: " + mapEv.getConceptId());
						// System.out.println(" Concept Name: " + mapEv.getConceptName());
						// System.out.println(" Preferred Name: " + mapEv.getPreferredName());
						// System.out.println(" Matched Words: " + mapEv.getMatchedWords());
						// System.out.println(" Semantic Types: " + mapEv.getSemanticTypes());
						// System.out.println(" is Head?: " + mapEv.isHead());
						// System.out.println(" is Overmatch?: " + mapEv.isOvermatch());
						// System.out.println(" Sources: " + mapEv.getSources());
						// System.out.println(" Positional Info: " + mapEv.getPositionalInfo());
					}
				}
			}
		}
		api.getSession().disconnect();
		return al;
		// return null;
	}

	public static String[] getCUI2(String input) throws Exception {
		String[] cuiname = new String[3];
		cuiname[0] = "unmapped";
		cuiname[1] = "unmapped";
		cuiname[2] = "unmapped";

		MetaMapApi api = new MetaMapApiImpl(0);
		api.setOptions("-y");
		List<Result> resultList = api.processCitationsFromString(input);
		// System.out.println("input=" + input);
		// for (Result result: resultList) {
		Result result = resultList.get(0);
		for (Utterance utterance : result.getUtteranceList()) {
			// System.out.println("Utterance:");
			// System.out.println(" Id: " + utterance.getId());
			// System.out.println(" Utterance text: " + utterance.getString());
			// System.out.println(" Position: " + utterance.getPosition());

			for (PCM pcm : utterance.getPCMList()) {
				// System.out.println("Phrase:");
				// System.out.println(" text: " + pcm.getPhrase().getPhraseText());
				// System.out.println("Mappings:");
				for (Mapping map : pcm.getMappingList()) {
					// System.out.println(" Mapping:");
					// System.out.println(" Map Score: " + map.getScore());
					for (Ev mapEv : map.getEvList()) {
						cuiname[0] = mapEv.getConceptName();
						cuiname[1] = mapEv.getConceptId();
						cuiname[2] = mapEv.getSemanticTypes().toString();
						// System.out.println(" Score: " + mapEv.getScore());
						// System.out.println(" Concept Id: " + mapEv.getConceptId());
						// System.out.println(" Concept Name: " + mapEv.getConceptName());
						// System.out.println(" Preferred Name: " + mapEv.getPreferredName());
						// System.out.println(" Matched Words: " + mapEv.getMatchedWords());
						// System.out.println(" Semantic Types: " + mapEv.getSemanticTypes());
						// System.out.println(" is Head?: " + mapEv.isHead());
						// System.out.println(" is Overmatch?: " + mapEv.isOvermatch());
						// System.out.println(" Sources: " + mapEv.getSources());
						// System.out.println(" Positional Info: " + mapEv.getPositionalInfo());
					}
				}
			}
		}
		api.getSession().disconnect();
		return cuiname;
		// return null;

	}

	public static String getCUI(String input) throws Exception {
		String cui = new String();
		MetaMapApi api = new MetaMapApiImpl(0);
		List<Result> resultList = api.processCitationsFromString(input);
		// for (Result result: resultList) {
		Result result = resultList.get(0);
		for (Utterance utterance : result.getUtteranceList()) {
			System.out.println("Utterance:");
			System.out.println(" Id: " + utterance.getId());
			System.out.println(" Utterance text: " + utterance.getString());
			System.out.println(" Position: " + utterance.getPosition());

			for (PCM pcm : utterance.getPCMList()) {
				System.out.println("Phrase:");
				System.out.println(" text: " + pcm.getPhrase().getPhraseText());
				System.out.println("Candidates:");
				for (Ev ev : pcm.getCandidateList()) {
					System.out.println("  Score: " + ev.getScore());
					System.out.println("  Concept Id: " + ev.getConceptId());
					System.out.println("  Concept Name: " + ev.getConceptName());
					System.out.println("  Preferred Name: " + ev.getPreferredName());
					System.out.println("  Matched Words: " + ev.getMatchedWords());
					System.out.println("  Semantic Types: " + ev.getSemanticTypes());
					System.out.println("  MatchMap: " + ev.getMatchMap());
					System.out.println("  MatchMap alt. repr.: " + ev.getMatchMapList());
					System.out.println("  is Head?: " + ev.isHead());
					System.out.println("  is Overmatch?: " + ev.isOvermatch());
					System.out.println("  Sources: " + ev.getSources());
					System.out.println("  Positional Info: " + ev.getPositionalInfo());
				}
				System.out.println("Mappings:");
				for (Mapping map : pcm.getMappingList()) {
					System.out.println(" Mapping:");
					System.out.println(" Map Score: " + map.getScore());
					for (Ev mapEv : map.getEvList()) {

						cui = mapEv.getConceptId();

						System.out.println(" Score: " + mapEv.getScore());
						System.out.println(" Concept Id: " + mapEv.getConceptId());
						System.out.println(" Concept Name: " + mapEv.getConceptName());
						System.out.println(" Preferred Name: " + mapEv.getPreferredName());
						System.out.println(" Matched Words: " + mapEv.getMatchedWords());
						System.out.println(" Semantic Types: " + mapEv.getSemanticTypes());
						System.out.println(" is Head?: " + mapEv.isHead());
						System.out.println(" is Overmatch?: " + mapEv.isOvermatch());
						System.out.println(" Sources: " + mapEv.getSources());
						System.out.println(" Positional Info: " + mapEv.getPositionalInfo());
					}
				}

			}
		}
		api.getSession().disconnect();

		return cui;
		// return null;
	}

	public List<ParsingResults> parse(String content, List<String> theOptions) {
		List<ParsingResults> pResults = new ArrayList<ParsingResults>();

		// TODO Auto-generated method stub
		MetaMapApi api = new MetaMapApiImpl(0);
		if (theOptions.size() > 0) {
			api.resetOptions();
			api.setOptions(theOptions);
		}

		List<Result> resultList = api.processCitationsFromString(content);

		Result result = resultList.get(0);
		try {
			for (Utterance utterance : result.getUtteranceList()) {

				// System.out.println("Utterance:");
				// System.out.println(" Id: " + utterance.getId());
				// System.out.println(" Utterance text: " + utterance.getString());
				// System.out.println(" Position: " + utterance.getPosition());

				for (PCM pcm : utterance.getPCMList()) {
					// System.out.println("Phrase:");
					// System.out.println(" text: " + pcm.getPhrase().getPhraseText());
					// System.out.println("Mappings:");
					for (Mapping map : pcm.getMappingList()) {
						// System.out.println(" Mapping:");
						// System.out.println(" Map Score: " + map.getScore());
						for (Ev mapEv : map.getEvList()) {
							String[] cuiname = new String[4];
							//
							cuiname[0] = mapEv.getConceptName();
							cuiname[1] = mapEv.getConceptId();
							cuiname[2] = mapEv.getSemanticTypes().toString();
							cuiname[3] = mapEv.getSources().toString();
							//
							if (o.hmCui2Hpo.containsKey(cuiname[1])) {
								ParsingResults pr = new ParsingResults();
//
//								System.out.println("Utterance:");
//								System.out.println(" Id: " + utterance.getId());
//								System.out.println(" Utterance text: " + utterance.getString());
//								System.out.println(" Position: " + utterance.getPosition());
//								System.out.println("Phrase:");
//								System.out.println(" text: " + pcm.getPhrase().getPhraseText());
//								System.out.println("Mappings:");
								String Id = o.hmCui2Hpo.get(cuiname[1]);
								if (Id.contains("|")) {
									Id = Id.split("\\|")[0];
								}
								String name = o.hmHpo2Name.get(Id);
								pr.setHpoId(Id);
								pr.setHpoName(name);
								List<Position> position = mapEv.getPositionalInfo();
								int[] pos = getLongestPosition(position);
								pr.setStart(pos[0]);
								pr.setLength(pos[1]);
								pResults.add(pr);
							}
							// System.out.println(" Score: " + mapEv.getScore());
							// System.out.println(" Concept Id: " + mapEv.getConceptId());
							// System.out.println(" Concept Name: " + mapEv.getConceptName());
							// System.out.println(" Preferred Name: " + mapEv.getPreferredName());
							// System.out.println(" Matched Words: " + mapEv.getMatchedWords());
							// System.out.println(" Semantic Types: " + mapEv.getSemanticTypes());
							// System.out.println(" is Head?: " + mapEv.isHead());
							// System.out.println(" is Overmatch?: " + mapEv.isOvermatch());
							// System.out.println(" Sources: " + mapEv.getSources());
							// System.out.println(" Positional Info: " + mapEv.getPositionalInfo());
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pResults = cleaner.getPhenotypeOnly(pResults);
		return pResults;
	}

	private int[] getLongestPosition(List<Position> position) {
		int[] posArray = new int[2];
		int start = 999999999;
		int end = -1;
		for (Position p : position) {
			int start_tmp = p.getX();
			int end_tmp = p.getX() + p.getY();
			if (start_tmp < start) {
				start = start_tmp;
			}
			if (end_tmp > end) {
				end = end_tmp;
			}

		}
		posArray[0] = start;
		posArray[1] = end - start;
		return posArray;
	}

}
