
package edu.columbia.dbmi.doc2hpo.service;

import bioc.BioCDocument;
import edu.columbia.dbmi.doc2hpo.pojo.ParsingResults;
import edu.columbia.dbmi.doc2hpo.util.Obo;
import gov.nih.nlm.nls.metamap.document.FreeText;
import gov.nih.nlm.nls.metamap.lite.types.Entity;
import gov.nih.nlm.nls.metamap.lite.types.Ev;
import gov.nih.nlm.nls.ner.MetaMapLite;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import gov.nih.nlm.nls.nlp.nlsstrings.NLSStrings;

/**
 * Example of using MetaMapLite from Java
 */

public class MetaMapLiteParser {
	/** log4j logger instance */
	private static final Logger logger = LogManager.getLogger(MetaMapLiteParser.class);
	private Obo o;
	private HpoCleaner cleaner;
	private MetaMapLite metaMapLiteInst;
	private HashMap<String, String> hmCui2Hpo;
	private HashMap<String, String> hmHpo2Name;

	public MetaMapLiteParser(String metamapliteconfiger) throws FileNotFoundException, IOException, ClassNotFoundException,
			InstantiationException, NoSuchMethodException, IllegalAccessException {
		// Initialization Section
		Properties myProperties = new Properties();

//		myProperties.setProperty("opennlp.models.directory", dataRoot + "/models");
//		myProperties.setProperty("metamaplite.index.directory", dataRoot + "/ivf/2018AB/USAbase/strict");
//		myProperties.setProperty("metamaplite.excluded.termsfile", dataRoot + "/specialterms.txt");
//		myProperties.setProperty("metamaplite.excluded.termsfile", dataRoot + "/specialterms.txt");
//		myProperties.setProperty("metamaplite.negation.detector", "gov.nih.nlm.nls.metamap.lite.NegEx");

		MetaMapLite.expandIndexDir(myProperties);
		// Loading properties file in "config", overriding previously
		// defined properties.
		myProperties.load(new FileReader(metamapliteconfiger));
		this.metaMapLiteInst = new MetaMapLite(myProperties);

		// Processing Section

		// load hpo resource.
		Obo obo = new Obo();
		this.hmCui2Hpo = obo.Cui2Hpo();
		this.hmHpo2Name = obo.Hpo2Name();
		this.cleaner = new HpoCleaner();
	}

	public List<ParsingResults> parse(String content, boolean negex) throws IllegalAccessException, InvocationTargetException, IOException, Exception {
		List<ParsingResults> pResults = new ArrayList<ParsingResults>();
		BioCDocument document = FreeText.instantiateBioCDocument(content);
		List<BioCDocument> documentList = new ArrayList<BioCDocument>();
		documentList.add(document);
		List<Entity> entityList = this.metaMapLiteInst.processDocumentList(documentList);
		for (Entity entity : entityList) {
			boolean isNegated = false;
			for (Ev ev : entity.getEvSet()) {
				if(negex) {
					isNegated = entity.isNegated();
				}
				ParsingResults pr = new ParsingResults();
				String cui = ev.getConceptInfo().getCUI();
				int start = ev.getStart();
				int length = ev.getLength();
				if (this.hmCui2Hpo.containsKey(cui)) {
					String hpo = this.hmCui2Hpo.get(cui);
					String[] hpoL = hpo.split("\\|");

//					for (String Id : hpoL) {
						String Id = hpoL[0];
						if (Id.contains("|")) {
							Id = Id.split("\\|")[0];
						}

						String name = this.hmHpo2Name.get(Id);
						pr.setHpoId(Id);
						pr.setHpoName(name);
						pr.setStart(start);
						pr.setLength(length);
						pr.setNegated(isNegated);
//						System.out.println(ev.getMatchedText() + "\t" + cui + "\t" + Id + "\t" + name + "\t" + start + "\t" + length + "\t" + isNegated);
						pResults.add(pr);

//					}
				}
			}
		}
		pResults = this.cleaner.getPhenotypeOnly(pResults);
		return pResults;
	}

	public static void main(String[] args) throws Exception, IOException, ClassNotFoundException,
			InstantiationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		MetaMapLiteParser mmlp = new MetaMapLiteParser("/Users/cl3720/Projects/sandbox/doc2hpo/properties/metamaplite.properties");
		String content = "He denies behavioral disturbance. Individual II-1 is a 10 year old boy. He was born at term with normal birth parameters and good APGAR scores (9/10/10). The neonatal period was uneventful, and he had normal motor development during early childhood: he began to look up at 3 months, sit by himself at 5 months, stand up at 11 months, walk at 13 months, and speak at 17 months. He attended a regular kindergarten, without any signs of difference in intelligence, compared to his peers. Starting at age 6, the parents observed ever increasing behavioral disturbance for the boy, manifesting in multiple aspects of life. For example, he can no longer wear clothes by himself, cannot obey instruction from parents/teachers, can no longer hold subjects tightly in hand, which were all things that he could do before 6 years of age. In addition, he no longer liked to play with others; instead, he just preferred to stay by himself, and he sometimes fell down when he walked on the stairs, which had rarely happened at age 5. The proband continued to deteriorate: at age 9, he could not say a single word and had no action or response to any instruction given in clinical exams. Additionally, rough facial features were noted with a flat nasal bridge, a synophrys (unibrow), a long and smooth philtrum, thick lips and an enlarged mouth. He also had rib edge eversion, and it was also discovered that he was profoundly deaf and had completely lost the ability to speak. He also had loss of bladder control. The diagnosis of severe intellectual disability was made, based on Wechsler Intelligence Scale examination. Brain MRI demonstrated cortical atrophy with enlargement of the subarachnoid spaces and ventricular dilatation (Figure 2). Brainstem evoked potentials showed moderate abnormalities. Electroencephalography (EEG) showed abnormal sleep EEG.\n" + 
				"";
		mmlp.parse(content, true);
	}

}
