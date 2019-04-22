<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>

<head>

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="Genetic Phenotype EHR doc2hpo">
<meta name="author"
	content="Cong Liu,  Chi Yuan, Kai Wang, Chunhua Weng">
<meta name="robots" content="index,follow">


<!-- Site CSS -->
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.css">
<link rel="stylesheet" href="./css/styles.css">


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.14/semantic.js"></script>



</head>


<body>
	<div class="ui fixed menu">
		<div class="ui container">
			<a href="#" class="header item"> <img class="logo"
				src="./img/doc2hpo2.png" style="width: 120px; height: 50px;"></a>
			<a href='./index.jsp' class='header item'><i class="home icon"></i>
				Home </a> <a href='./tutorial.jsp' class='header item'> <i
				class="info icon"> </i>Tutorial
			</a> <a href='./faq.jsp' class='header item'> <i
				class="question circle icon"> </i>FAQ
			</a> <a href='https://github.com/stormliucong/doc2hpo' target="_blank"
				class='header item'><i class="github icon"></i> GitHub </a> <a
				href='http://people.dbmi.columbia.edu/~chw7007/' target="_blank"
				class='header item'><i class="envelope icon"></i> About Us </a>
		</div>
	</div>


	<div class="full height">
		<div class="ui container" style="padding-top: 100px;">
			<h1 class="ui header">
				<span class="library"> Doc2Hpo </span>

			</h1>
			<span> A webservice to extract human phenotype ontology terms
				from free-text based clinical notes or literatures. </span>
			<div class="ui hidden divider"></div>
		</div>
	</div>

	<div class="ui container">
		<div class="ui styled fluid accordion">
			<div class="active title">
				<i class="dropdown icon"></i> Which parsing engine should I use?
			</div>
			<div class="active content">
				<p>Speed ranking (fast to slow): string-based (default) >
					MetaMap Lite > MetaMap = NCBO annotator > Ensemble</p>
				<p>Recall rate (hige to low): Ensemble > MetaMap > MetaMap Lite
					= NCBO annotator > string-based (default)</p>
				<p>In general, if the input is long (more than 1 WORD page), we
					suggest using default method or MetaMap Lite. If users want a
					better performance, please use the ensemble method</p>
			</div>
			<div class="title">
				<i class="dropdown icon"></i> What is the performance of the full
				automated recognition?
			</div>
			<div class="content">
				<p>Without negation function turn on, the precision is ~0.4 and the recall is ~0.7.</p>
				<p>By turnning on the negation detection, the precision is ~0.7.</p>
			</div>
			<div class="title">
				<i class="dropdown icon"></i> How does the negation detection work?
			</div>
			<div class="content">
				<p>Negation detection is available by using Wendy Chapman's
					NegEx, which is a rule and keyword based method</p>
			</div>
			<div class="title">
				<i class="dropdown icon"></i> How are the overlapped and repeated
				annotations processed?
			</div>
			<div class="content">
				<p>If two annotations overlapped in the text, only the longest
					one will be used in display. But both will be stored in the JSON
					output.</p>
				<p>If two annotations repeated, a random one will be picked. But
					both will be stored in the JSON output.</p>
			</div>
			<div class="title">
				<i class="dropdown icon"></i> What is the ensemble method?
			</div>
			<div class="content">
				<p>Ensemble method union the results generated from other
					parsinge engines.</p>
			</div>
			<div class="title">
				<i class="dropdown icon"></i> What is the string-based method?
			</div>
			<div class="content">
				<p>String-based method leveraging the Aho–Corasick algorithm for
					speedy concept extraction. The full input will be treated as one
					single string and search against all HPO terms and their synonyms
					under ‘phenotypic abnormality’ (HP:0000118).</p>

				<p>by unchecking 'allow partial search', post-processing rules
					will be added to remove partial match, such as 'tic' in 'genetics'</p>
			</div>
			<div class="title">
				<i class="dropdown icon"></i> What is the MetaMap method?
			</div>
			<div class="content">
				<p>It splits the input into sentences, and then feed each
					sentence to a locally configured MetaMap server via the Java API.</p>
				<p>MetaMap first identifies candidate clinical terms through
					lexical and syntactic analysis and maps them to standard UMLS
					concepts.</p>
				<p>
					The UMLS concepts are then mapped to HPO concepts following the
					mapping at <a href="http://purl.obolibrary.org/obo/hp.obo">here</a>.
				</p>
			</div>
			<div class="title">
				<i class="dropdown icon"></i> What is the MetaMapLite method?
			</div>
			<div class="content">
				<p>MetaMap is a fast version of MetaMap. It provide a near
					real-time named-entity recognizer which is not a rigorous as
					MetaMap but is much faster</p>
				<p>Currenty, MetaMapLite does not support dynamic variant
					generation. Named Entities are found using longest match.</p>
			</div>
			<div class="title">
				<i class="dropdown icon"></i> What is the NCBO annotator method?
			</div>
			<div class="content">
				<p>It employs the online NCBO Annotator API for HPO concept
					recognition. Different options for NCBO Annotator are exposed to
					users via the Doc2Hpo interface to customize the parsing.</p>
				<p>When the network is not good, it will cause certain delay if
					the input size is big</p>
			</div>
		</div>
	</div>
	<div class="ui hidden divider"></div>

	<div class="ui inverted vertical footer segment form-page">
		<div class="ui center aligned container">Columbia University
			&copy 2018. All Rights Reserved</div>
	</div>
	<!-- /.hidden for js only -->
	<input style="display: none;" id="basePath" name="basePath" value=".">
	<script type="text/javascript">
		$(document).ready(function() {
			$('.ui.accordion').accordion();
		});
	</script>
</body>

</html>
