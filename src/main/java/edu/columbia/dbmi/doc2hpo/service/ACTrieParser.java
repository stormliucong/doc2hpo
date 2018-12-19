package edu.columbia.dbmi.doc2hpo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import edu.columbia.dbmi.doc2hpo.pojo.ParsingResults;
import edu.columbia.dbmi.doc2hpo.util.FileUtil;
import edu.columbia.dbmi.doc2hpo.util.NegUtil;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;


@Service("acdatService")
public class ACTrieParser {
	AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
	HashMap<String,String> hpodic=new HashMap<String,String>();
	private HpoCleaner cleaner;
	
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
		cleaner = new HpoCleaner();

	}

	// public String parse(String par){
	//
	// }
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ACTrieParser rbp = new ACTrieParser();
////		String content=FileUtil.Readfile(args[0]);
		String content = "Individual II-1 is a 10 year old boy. He was born at term with normal birth parameters and good APGAR scores (9/10/10). The neonatal period was uneventful, and he had normal motor development during early childhood: he began to look up at 3 months, sit by himself at 5 months, stand up at 11 months, walk at 13 months, and speak at 17 months. He attended a regular kindergarten, without any signs of difference in intelligence, compared to his peers. Starting at age 6, the parents observed ever increasing behavioral disturbance for the boy, manifesting in multiple aspects of life. For example, he can no longer wear clothes by himself, cannot obey instruction from parents/teachers, can no longer hold subjects tightly in hand, which were all things that he could do before 6 years of age. In addition, he no longer liked to play with others; instead, he just preferred to stay by himself, and he sometimes fell down when he walked on the stairs, which had rarely happened at age 5. The proband continued to deteriorate: at age 9, he could not say a single word and had no action or response to any instruction given in clinical exams. Additionally, rough facial features were noted with a flat nasal bridge, a synophrys (unibrow), a long and smooth philtrum, thick lips and an enlarged mouth. He also had rib edge eversion, and it was also discovered that he was profoundly deaf and had completely lost the ability to speak. He also had loss of bladder control. The diagnosis of severe intellectual disability was made, based on Wechsler Intelligence Scale examination. Brain MRI demonstrated cortical atrophy with enlargement of the subarachnoid spaces and ventricular dilatation (Figure 2). Brainstem evoked potentials showed moderate abnormalities. Electroencephalography (EEG) showed abnormal sleep EEG.";
		List<ParsingResults> results=rbp.parse(rbp, content);
		for(ParsingResults r : results) {
			System.out.println(r.getHpoId() + "\t" + r.getHpoName() + "\t" + r.getStart() + "\t" + r.getLength() + "\n");
		}
//		FileUtil.write2File(args[1], results);

	}

	public List<ParsingResults> parse(ACTrieParser rbp, String text2) throws Exception {
		List<ParsingResults> pResults = new ArrayList<ParsingResults>();
		
		text2 = text2.toLowerCase();
		StringBuffer sb=new StringBuffer();
//		text2 = rbp.filterStopWords(text2.toLowerCase());
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
			
			System.out.println(s.value + "\t" + s.begin + "," + s.end + "\t" + nrt.negCheck(text2, s.value, true));
//			if(text2.contains(" "+s.value+" ")||text2.contains(" "+s.value+".")||text2.contains(" "+s.value+",")){
				ParsingResults pr = new ParsingResults();
				pr.setHpoId(hpodic.get(s.value));
		    	pr.setHpoName(s.value);
		    	pr.setStart(s.begin);
		    	pr.setLength(s.end - s.begin);
		    	pResults.add(pr);
				sb.append(s.value + "\t" + hpodic.get(s.value)+ "\t" + nrt.negCheck(text2, s.value, true)+"\n");
				System.out.println(s.value + "\t" + hpodic.get(s.value)+ "\t" + nrt.negCheck(text2, s.value, true));
//			}
		}
		//System.out.println("text2="+text2);
		pResults = cleaner.getPhenotypeOnly(pResults);
		pResults = cleaner.removeAcrny(pResults);
		return pResults;
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
