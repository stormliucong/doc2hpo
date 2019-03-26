package edu.columbia.dbmi.doc2hpo.tool;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.columbia.dbmi.doc2hpo.pojo.SplittedSentence;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.TypesafeMap;

public class CoreNLP {
	public static Properties props;
	public StanfordCoreNLP pipeline;

	public static void main(String[] args) {
		String input = "He is a kids.   He know how to play \n   He will run";
		CoreNLP snlp = new CoreNLP();
		List<SplittedSentence> ss = snlp.splitInputSimple(input);
	}

	public CoreNLP() {
//		this.props = new Properties();
//		this.props.setProperty("annotators", "tokenize,ssplit");
//		this.pipeline = new StanfordCoreNLP(props);
	}

	public List<SplittedSentence> splitInputSimple(String input) {
		List<SplittedSentence> ss = new ArrayList<SplittedSentence>();
		Document doc = new Document(input);
		for (Sentence sentence : doc.sentences()) {
			SplittedSentence s = new SplittedSentence();
			s.setStart(sentence.characterOffsetBegin(0));
			s.setEnd(sentence.characterOffsetEnd(sentence.length() - 1));
			s.setSetence(sentence.toString());
			ss.add(s);
		}
		return ss;
	}

	public void splitInput(String input) {
		Annotation document = new Annotation(input);
		this.pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			System.out.println(sentence);
		}
	}

}