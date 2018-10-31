package edu.columbia.dbmi.doc2hpo.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NcboParser {
	public static String REST_URL = "http://data.bioontology.org";
    public String API_KEY = "";
    public ObjectMapper mapper = new ObjectMapper();
    
    
    public NcboParser(String apikey) {
		this.API_KEY = apikey;
	}
    
    private String get(String urlToGet) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToGet);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "apikey token=" + this.API_KEY);
            conn.setRequestProperty("Accept", "application/json");
            rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    private String post(String urlToGet, String urlParameters) {
    	System.out.println(urlParameters);
        URL url;
        HttpURLConnection conn;
        
		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;

        String line;
        String result = "";
        try {
            url = new URL(urlToGet);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "apikey token=" + this.API_KEY);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));


            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            conn.disconnect();

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    private JsonNode jsonToNode(String json) {
        JsonNode root = null;
        try {
            root = this.mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
    
//    private void printAnnotations(JsonNode annotations) {
//        for (JsonNode annotation : annotations) {
//            // Get the details for the class that was found in the annotation and print
//            JsonNode classDetails = jsonToNode(get(annotation.get("annotatedClass").get("links").get("self").asText()));
//            System.out.println("Class details");
//            System.out.println("\tid: " + classDetails.get("@id").asText());
//            System.out.println("\tprefLabel: " + classDetails.get("prefLabel").asText());
//            System.out.println("\tontology: " + classDetails.get("links").get("ontology").asText());
//            System.out.println("\n");
//
//            JsonNode hierarchy = annotation.get("hierarchy");
//            // If we have hierarchy annotations, print the related class information as well
//            if (hierarchy.isArray() && hierarchy.elements().hasNext()) {
//                System.out.println("\tHierarchy annotations");
//                for (JsonNode hierarchyAnnotation : hierarchy) {
//                    classDetails = jsonToNode(get(hierarchyAnnotation.get("annotatedClass").get("links").get("self").asText()));
//                    System.out.println("\t\tClass details");
//                    System.out.println("\t\t\tid: " + classDetails.get("@id").asText());
//                    System.out.println("\t\t\tprefLabel: " + classDetails.get("prefLabel").asText());
//                    System.out.println("\t\t\tontology: " + classDetails.get("links").get("ontology").asText());
//                }
//            }
//        }
//    }
    
    public HashMap<String, Object> parse(String content, List<String> theOptions){
    	HashMap<String, Object> idName = new HashMap<String, Object>();
    	String urlParameters = String.join("&", theOptions);
    	System.out.println(urlParameters + "!!!");
        JsonNode annotations;
        String textToAnnotate;
		try {
			textToAnnotate = URLEncoder.encode(content, "ISO-8859-1");
//				String input = "he was profoundly deaf and had completely lost the ability to speak.";
		        urlParameters = "text=" + textToAnnotate + "&" + urlParameters;
//		        urlParameters = "text=" + textToAnnotate + "&ontologies=HP&longest_word=true";

		        annotations = this.jsonToNode(this.post(REST_URL + "/annotator", urlParameters));
		        for (JsonNode annotation : annotations) {
		            // Get the details for the class that was found in the annotation and print
		            JsonNode classDetails = jsonToNode(get(annotation.get("annotatedClass").get("links").get("self").asText()));
		            System.out.println("=="+classDetails.get("@id").asText()+"==");
		            String[] Ids = classDetails.get("@id").asText().split("/");
		            String Id = Ids[Ids.length - 1];
		            String name = classDetails.get("prefLabel").asText();
		            
		            JsonNode annotationDetails = jsonToNode(get(annotation.get("annotations"));
		            
		            idName.put(name, Id);
//		            System.out.println("Class details");
//		            System.out.println("\tid: " + classDetails.get("@id").asText());
//		            System.out.println("\tprefLabel: " + classDetails.get("prefLabel").asText());
//		            System.out.println("\tontology: " + classDetails.get("links").get("ontology").asText());
//		            System.out.println("\n");

//		            JsonNode hierarchy = annotation.get("hierarchy");
		            // If we have hierarchy annotations, print the related class information as well
//		            if (hierarchy.isArray() && hierarchy.elements().hasNext()) {
//		                System.out.println("\tHierarchy annotations");
//		                for (JsonNode hierarchyAnnotation : hierarchy) {
//		                    classDetails = jsonToNode(get(hierarchyAnnotation.get("annotatedClass").get("links").get("self").asText()));
//		                    System.out.println("\t\tClass details");
//		                    System.out.println("\t\t\tid: " + classDetails.get("@id").asText());
//		                    System.out.println("\t\t\tprefLabel: " + classDetails.get("prefLabel").asText());
//		                    System.out.println("\t\t\tontology: " + classDetails.get("links").get("ontology").asText());
//		                }
//		            }
		        }
		        

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idName;

      
		
    }
    
    public static void main(String[] args) throws Exception {
//        String urlParameters;
//        urlParameters = "ontology=HP";
        JsonNode annotations;
//        
        NcboParser ncboparser = new NcboParser("98c881c1-1cdb-4fd2-ad1c-41f446e15f73");
		String input = "Individual II-1 is a 10 year old boy. He was born at term with normal birth parameters and good APGAR scores (9/10/10). The neonatal period was uneventful, and he had normal motor development during early childhood: he began to look up at 3 months, sit by himself at 5 months, stand up at 11 months, walk at 13 months, and speak at 17 months. He attended a regular kindergarten, without any signs of difference in intelligence, compared to his peers. Starting at age 6, the parents observed ever increasing behavioral disturbance for the boy, manifesting in multiple aspects of life. For example, he can no longer wear clothes by himself, cannot obey instruction from parents/teachers, can no longer hold subjects tightly in hand, which were all things that he could do before 6 years of age. In addition, he no longer liked to play with others; instead, he just preferred to stay by himself, and he sometimes fell down when he walked on the stairs, which had rarely happened at age 5. The proband continued to deteriorate: at age 9, he could not say a single word and had no action or response to any instruction given in clinical exams. Additionally, rough facial features were noted with a flat nasal bridge, a synophrys (unibrow), a long and smooth philtrum, thick lips and an enlarged mouth. He also had rib edge eversion, and it was also discovered that he was profoundly deaf and had completely lost the ability to speak. He also had loss of bladder control. The diagnosis of severe intellectual disability was made, based on Wechsler Intelligence Scale examination. Brain MRI demonstrated cortical atrophy with enlargement of the subarachnoid spaces and ventricular dilatation (Figure 2). Brainstem evoked potentials showed moderate abnormalities. Electroencephalography (EEG) showed abnormal sleep EEG.";

        String textToAnnotate = URLEncoder.encode(input, "ISO-8859-1");
        
        // Get just annotations
//        urlParameters = "text=" + textToAnnotate;
//        annotations = ncboparser.jsonToNode(ncboparser.get(REST_URL + "/annotator?" + urlParameters));
//        ncboparser.printAnnotations(annotations);

        // Annotations with hierarchy
//        urlParameters = "max_level=3&text=" + textToAnnotate;
//        annotations = ncboparser.jsonToNode(ncboparser.get(REST_URL + "/annotator?" + urlParameters));
//        ncboparser.printAnnotations(annotations);

        // Annotations using POST (necessary for long text)
//        urlParameters = "text=" + textToAnnotate + "&ontology=HP&longest_only=true";
        
//        annotations = ncboparser.jsonToNode(ncboparser.post(REST_URL + "/annotator", urlParameters));
//        ncboparser.printAnnotations(annotations);
        List<String> theOptions = new ArrayList<String>();
        theOptions.add("exclude_numbers=true");
        theOptions.add("whole_word_only=true");
        theOptions.add("longest_only=false");
        theOptions.add("ontologies=HP");

        HashMap <String, Object> hm = ncboparser.parse(input,theOptions);

        // Get labels, synonyms, and definitions with returned annotations
//        urlParameters = "include=prefLabel,synonym,definition&text=" + textToAnnotate;
//        annotations = ncboparser.jsonToNode(ncboparser.get(REST_URL + "/annotator?" + urlParameters));
//        for (JsonNode annotation : annotations) {
//            System.out.println(annotation.get("annotatedClass").get("prefLabel").asText());
//        }
    }

    
}
