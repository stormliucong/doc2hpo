package edu.columbia.dbmi.doc2hpo.service;

import java.awt.print.Printable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie.Hit;

import edu.columbia.dbmi.doc2hpo.util.FileUtil;
import edu.columbia.dbmi.doc2hpo.util.NegUtil;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;


@Service("acdatService")
public class ACTrieParser {
	AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
	HashMap<String,String> hpodic=new HashMap<String,String>();

	public ACTrieParser() {
		try {
			TreeMap<String, String> map = new TreeMap<String, String>();
			File hpoTermFile = ResourceUtils.getFile("classpath:dictionary/hpoterms.txt");
			String cleandic = hpoTermFile.toString();
			String content = FileUtil.Readfile(cleandic);
			String[] keyArray = content.split("\n");
			for (String key : keyArray) {
				String[] t = key.split("\t");
				map.put(t[0].toLowerCase(), t[0].toLowerCase());
				hpodic.put(t[0].toLowerCase(), t[1]);
			}
			acdat.build(map);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
	}

	// public String parse(String par){
	//
	// }
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ACTrieParser rbp = new ACTrieParser();
////		String content=FileUtil.Readfile(args[0]);
		String content = "Final - Signature  Marratta, MD  Columbia University Medical Center  Ft Washington Avenue  New York City 10032 of Present Illness  He had had orthostatic hypotension while visiting in Israel in summer 2013. When he stood up, he could not speak and had bad memory lapses. Slight headaches affected the temple area. When breathing lightly, the headache disappeared.  These complaints continued until he saw Dr Mesoli (hypertension) at Roosevelt, now retried. He thought the last coronary stent was functioning and taking him off beta-blocker would help diminish the orthostatic complaints. It took &quot;a long time&quot; but he has had no headaches, less symptoms of orthostatic hypotension, but on standing he has some discomfort on the chest and needs to use conscious breathing until he feels well. He thinks this breathing decreases his chest discomfort. He remains alert to having to stand for a few secs when he starts to stand up.  He experienced back pain 3 months ago. An orthopedist took x-rays and found 'bad degenerative discs'. On physical therapy, his legs feel stronger, he feels more alert, and then thinks he may be having better blood flow. (He actually has autonomic deconditioning which is improved by his physiotherapy.)  He claims his handwriting his deteriorated slightly. The words he writes may be incomplete. HIs signature has changed. He must concentrate to make his handwriting smooth.  He also thinks he has having episodes of short term memory disturbance,  On return from Israel in 2013 he discovered he had claudication. Seeing George Todd tomorrow, a repeat pf the visit a few months back.Active Problems  Atrial fibrillation (427.31)  Chronic kidney disease (585.9)  Coronary artery stenosis (414.00)  Essential hypertension (401.9)  Gout, joint (274.00)  Hyperlipidemia (272.4)  Orthostatic hypotension (458.0)  Osteoporosis (733.00)  Peripheral neuropathy (356.9)Meds  Aspirin 81 MG Oral Tablet Chewable;  Therapy: 17Jan2013 to Recorded  Atorvastatin Calcium 20 MG Oral Tablet;  Therapy: 25Jun2012 to Recorded  Cordarone 200 MG Oral Tablet (Amiodarone HCl);  Therapy: 24Jan2012 to Recorded  Lovaza 1 GM Oral Capsule (Omega-3-acid Ethyl Esters);  Therapy: 20Feb2012 to Recorded  Norvasc 5 MG Oral Tablet (AmLODIPine Besylate);  Therapy: 26Sep2013 to Recorded  Synthroid 25 MCG Oral Tablet (Levothyroxine Sodium);  Therapy: 08Feb2012 to Recorded  Allergies  No Known Drug Allergies   Vital Signs [Data Includes: Last 3 Instances]    ** Printed in Appendix #1 below. Exam  MENTAL STATUS EXAMINATION: Orientation: Normal Memory: Normal to tests of recent and distant events. Attention: Normal Language: Normal Knowledge: Normal. No evidence of memory disorder, just one of distractibility. Simultaneous synthesis and biparietal function was normal, as assessed by the prompt and analytic description of the Binet-Bobertag broken window picture. Fund of knowledge was tested and normal for current events, personal past history, and vocabulary. In response to spoken requests, the patient pointed out on a US map where we are, where Ronald Reagan is buried, where JFK died and where our patient was when the event occurred, and where JFK Jr died and how.   CRANIAL NERVE EXAMINATION: Cranial nerve II: slight left ptosis; Cranial nerve V: Normal Cranial nerves VII: Normal Cranial nerves VIII: Normal Cranial nerves IX/X: Normal Cranial nerve XI: Normal Cranial nerve XII: Normal   MOTOR EXAMINATION: Appearance: Normal NO sign of cog wheeling or dystonia. Tone: Normal Strength: Normal. COORDINATION: Normal. His handwriting is easily read, has comparable shapes line by line, no micrographic and no language errors.  SENSORY EXAMINATION: blunted ankle vibration.    Imaging/Interpretations  TCDoppler unchanged, including left vertebral occlusion, right vertebral slight acceleration but normal velocities n the basilar.   Doing well especially at age 84.  The neurological complains are all minor, not enough to prompt brain imaging.  The claudication and dependent rubor argue for arterial stenoses, but hope they are not advanced enough to require intervention.  He still has atrial fibrillation. Will copy Dr Alan Schwartz for whether warfarin should be used in place of his aspirin.signed by: JAY MOHR, MD; Sep 30 2014 5:49PM EST (Author)  Appendix #1    Vital Signs [Data Includes: Last 3 Instances]   Patient: HURWITZ, SEYMOUR I; DOB: 1/8/1930 12:00:00 AM; MRN: IDX01313877    Recorded by : MOHR, JAY at 30Sep2014 11:17AMRecorded by : Santobello, Trudi at 12Aug2014 11:49AMRecorded by : Santobello, Trudi at 11Feb2014 10:55AMHeight5 ft 5 in5 ft 6 in5 ft 6 inWeight165 lb 164 lb 164 lb BMI Calculated27.4626.4726.47BSA Calculated1.821.841.84Systolic133, LUE, Sitting140130Diastolic75, LUE, Sitting7070Heart Rate64, L RadialPulse QualityNormal, L RadialRespirationRespiration Quality     Electronically signed by:JAY MOHR MD Sep 30 2014 6:34PM EST Author";
		String results=rbp.parse(rbp, content);
		System.out.println(results);
//		FileUtil.write2File(args[1], results);

	}

	public String parse(ACTrieParser rbp, String text2) throws Exception {
		StringBuffer sb=new StringBuffer();
		text2 = rbp.filterStopWords(text2.toLowerCase());
		List<AhoCorasickDoubleArrayTrie.Hit<String>> wordList = acdat.parseText(text2);
		Integer last_start = 0;
		Integer last_end = 0;
		List<AhoCorasickDoubleArrayTrie.Hit<String>> longest = new ArrayList<AhoCorasickDoubleArrayTrie.Hit<String>>();
		for (Hit<String> s : wordList) {
			if ((s.begin <= last_start) && (s.end >= last_end)) {
				if (longest.size() > 0) {
					longest.remove(longest.size() - 1);
				}
			} else if (s.begin >= last_start && s.end <= last_end) {
				continue;
			}
			longest.add(s);
			last_start = s.begin;
			last_end = s.end;
		}
		NegUtil nrt = new NegUtil();
		for (Hit<String> s : longest) {
			//System.out.println(s.value + "\t" + s.begin + "," + s.end + "\t" + nrt.negCheck(text2, s.value, true));
			if(text2.contains(" "+s.value+" ")||text2.contains(" "+s.value+".")||text2.contains(" "+s.value+",")){
				sb.append(s.value + "\t" + hpodic.get(s.value)+ "\t" + nrt.negCheck(text2, s.value, true)+"\n");
				//System.out.println(s.value + "\t" + hpodic.get(s.value)+ "\t" + nrt.negCheck(text2, s.value, true));
			}
		}
		//System.out.println("text2="+text2);
		return sb.toString();
	}

	public String filterStopWords(String sent) throws FileNotFoundException {
		File stopwordsFile = ResourceUtils.getFile("classpath:dictionary/stopwords.txt");
		String stopwordsPath = stopwordsFile.toString();
		String c = FileUtil.Readfile(stopwordsPath);
		String[] arr = c.split(", ");
		Set<String> stopwords = new HashSet<String>();
		for (String a : arr) {
			stopwords.add(a.substring(1, a.length() - 1));
		}
		String[] words = sent.split(" ");
		StringBuffer sb = new StringBuffer();
		for (String w : words) {
			if (stopwords.contains(w)) {
				continue;
			}
			sb.append(w);
			sb.append(" ");
		}
		return sb.toString();
	}

	public String[] sentenceDetect(String paragraph) throws Exception {
		// refer to model file "en-sent,bin", available at link
		// http://opennlp.sourceforge.net/models-1.5/
		File enSentBinFile = ResourceUtils.getFile("classpath:dictionary/en-sent.bin");
		String enSentBinFilePath = enSentBinFile.toString();
		InputStream is = new FileInputStream(enSentBinFilePath);
		SentenceModel model = new SentenceModel(is);
		// feed the model to SentenceDetectorME class
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		// detect sentences in the paragraph
		String sentences[] = sdetector.sentDetect(paragraph);
		is.close();
		return sentences;
	}

}
