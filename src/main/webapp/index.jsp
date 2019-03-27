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
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css">
<link rel="stylesheet" type="text/css"
	href="	https://cdn.datatables.net/buttons/1.5.2/css/buttons.semanticui.min.css">
<link rel="stylesheet" href="./css/styles.css">
<link rel="stylesheet" href="./css/ent-display.css">

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf8"
	src="	https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="	https://cdn.datatables.net/buttons/1.5.2/js/buttons.semanticui.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.colVis.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.print.min.js"></script>

<script
	src="https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.14/semantic.js"></script>
<script type="text/javascript" src="./js/jquery.blockUI.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/6.0.1/jquery.mark.es6.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/8.11.1/mark.es6.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/0.10.0/lodash.min.js"></script>

</head>


<body>
	<div class="ui fixed menu">
		<div class="ui container">
			<a href="#" class="header item"> <img class="logo"
				src="./img/doc2hpo2.png" style="width: 120px; height: 50px;"></a>
			<a href='./index.jsp' class='header item'><i class="home icon"></i>
				Home </a> <a href='./tutorial.jsp' class='header item'> <i
				class="info icon"> </i>Tutorial
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
		<div class="html ui top attached segment" style="margin: 10px auto 0;">
			<div class="ui top attached label">Patient Description</div>
			<div class="ui form" id="inputForm">
				<div class="ui labeled icon mini basic button" id="exampleButton">
					<i class="external icon"></i> Example
				</div>
				<div class="field">
					<label></label>
					<textarea
						placeholder="Tips: feel free to use free-text to input the patient phenotype description"
						id="note"></textarea>
				</div>

				<div class="ui accordion field">
					<div class="title">
						<i class="icon dropdown"></i> Configurations
					</div>
					<div class="content field">
						<label class="transition hidden">Select a parsing engine:</label>
						<!-- <select
						class="ui fluid dropdown selection" id="parsingEngine">
						<option value="act">Fast string match</option>
						<option value="mmp">Metamap</option>
						<option value="ncbo">Ncbo annotator</option>
					</select> -->
						<div class="ui selection dropdown" id="parsingEngine">
							<input type="hidden" name="parsingEngine"> <i
								class="dropdown icon"></i>
							<div class="default text">Select a parsing engine</div>
							<div class="ui menu">
								<div class="item" data-value="act">
									<span class="text">String Search (default) </span> <span
										class="description">a string-based method leveraging
										the Aho–Corasick algorithm </span>
								</div>
								<div class="item" data-value="mmlp">
									<span class="text">MetaMap Lite </span> <span
										class="description">a fast version of MetaMap </span>
								</div>
								<div class="item" data-value="mmp">
									<span class="text">Metamap </span> <span class="description">using
										a local metamap java API to extract UMLS CUI terms and then
										mapped to HPO terms </span>
								</div>
								<div class="item" data-value="ncbo">
									<span class="text">NCBO Annotator </span><span
										class="description">call online or local NCBO annotator
										api</span>
								</div>

							</div>

						</div>

					</div>
					<!-- option for Metamap lite -->
					<div class="ui celled relaxed list" style="display: none"
						id="mmlpOptions">
						<div class="inline field">
							<div class="ui checkbox">
								<input type="checkbox" id="mmlp_negex" checked> <label>negation
									detection</label>
							</div>
						</div>
					</div>

					<!-- option for Metamap -->
					<div class="ui celled relaxed list" style="display: none"
						id="mmpOptions">
						<div class="inline field">
							<div class="ui checkbox">
								<input type="checkbox" id="all_acros_abbrs" checked> <label>allow
									acronym variants</label>
							</div>
						</div>
						<div class="inline field">
							<div class="ui checkbox hidden">
								<input type="checkbox" id="allow_concept_gaps" checked>
								<label>allow concept gaps</label>
							</div>
						</div>
						<div class="inline field">
							<div class="ui checkbox">
								<input type="checkbox" id="ignore_word_order" checked> <label>ignore
									word order</label>
							</div>
						</div>
						<div class="inline field">
							<div class="ui checkbox">
								<input type="checkbox" id="ignore_stop_phrases" checked>
								<label>ignore stop phrases</label>
							</div>
						</div>
					</div>

					<!-- Ncbo annotator -->
					<div class="ui celled relaxed list" style="display: none"
						id="ncboOptions">
						<div class="inline field">
							<div class="ui checkbox">
								<input type="checkbox" id="longest_only" checked> <label>Match
									longest only</label>
							</div>
						</div>
						<div class="inline field">
							<div class="ui checkbox hidden">
								<input type="checkbox" id="whole_word_only"> <label>Whole
									word only</label>
							</div>
						</div>
						<div class="inline field">
							<div class="ui checkbox">
								<input type="checkbox" id="exclude_numbers"> <label>Exclude
									numbers</label>
							</div>
						</div>
					</div>
					<div class="ui secondary submit button" id="parsingButton">Submit</div>
					<div class="ui error message"></div>
				</div>
			</div>
		</div>

		<div class="html ui top attached segment" id="parsingResultsPanel"
			style="display: none">
			<div class="ui top attached label">Parsing Results</div>
			<div class="ui segment">
				<div class="ui three top attached steps">
					<div class="step">
						<i class="mouse pointer icon"></i>
						<div class="content">
							<div class="title">Highlight</div>
							<div class="description">Hold keyboard letter 'Q' and use
								mouse to select the text</div>
						</div>
					</div>
					<div class="step">
						<i class="hand pointer outline icon"></i>
						<div class="content">
							<div class="title">Cancel</div>
							<div class="description">double click to cancel the
								highlighted text</div>
						</div>
					</div>
					<div class="step">
						<i class="plus square outline icon"></i>
						<div class="content">
							<div class="title">Search</div>
							<div class="description">click dark green box to search
								desired HPO terms</div>
						</div>
					</div>
				</div>
			</div>

			<div class="ui segment">
				<p id="parsingResults" style="white-space: pre-wrap">Pellentesque
					habitant morbi tristique senectus et netus et malesuada fames ac
					turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies
					eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas
					semper. Aenean ultricies mi vitae est. Mauris placerat eleifend
					leo.</p>
			</div>
			<!--  POP UP -->
			<div class="ui custom popup top left transition flowing hidden "
				id="searchPopup">
				<div style="display: none" id="HpoNameEntity"></div>
				<div class="ui fluid search" id='hpoSearch'>
					<input class="prompt" type="text" placeholder="Search HPO...">
					<span><i class="large plus square outline icon"
						id="addHpoTerm"></i> <i class="large red window close link icon"
						id="closeAddHpoTerm"></i></span>
					<div style="display: none" id="selectedResult"></div>

				</div>
			</div>
		</div>

		<div class="html ui top attached segment" id="outputPanel"
			style="display: none">
			<div class="ui top attached label">Output</div>
			<table class="ui table" id="shoppingCart">
				<thead>
					<tr>
						<th>Term</th>
						<th>Id</th>
						<th>Is_negated</th>
						<th>Count</th>
					</tr>
				</thead>
			</table>

			<div class="ui cards three stackable">

				<div class="card">
					<div class="content">

						<div class="header">Physicians</div>
						<div class="meta">Automated EHR-driven gene analysis</div>
						<div class="description">By clicking ‘Phenolyzer’, a
							Phenolyzer-compatible phenotype list will be generated in the
							clipboard and the users will be directed to Phenolyzer page</div>
					</div>
					<div class="extra content">
						<div class="ui list">
							<div class="item" id="copyColumnButton">
								<a href="http://phenolyzer.wglab.org/" target="_blank">
									Phenolyzer</a>
							</div>
							<div class="item" id="copyColumnButton">
								<a href="http://compbio.charite.de/phenomizer/" target="_blank">
									Phenomizer</a>
							</div>
							<div class="item" id="copyColumnButton2">
								<a href="http://compbio.charite.de/PhenIX/" target="_blank">
									PhenIX</a>
							</div>
							<div class="item" id="copyColumnButton">
								<a href="http://weatherby.genetics.utah.edu/phevor2/index.html"
									target="_blank"> Phevor 2</a>
							</div>
							<div class="item" id="copyColumnButton">
								<a
									href="https://monarch-exomiser-web-dev.monarchinitiative.org/exomiser/submit?"
									target="_blank"> Exomizer</a>
							</div>
						</div>
					</div>
				</div>
				<div class="card">
					<div class="content">
						<div class="header">Data Curators</div>
						<div class="meta">Standardized phenotypes collection</div>
						<div class="description">You could download the
							document-level collection of standardized phenotypes</div>
					</div>
					<div class="extra content">
						<div class="ui buttons" id="buttonGroups"></div>
					</div>
				</div>
				<div class="card">
					<div class="content">
						<div class="header">Annotators</div>
						<div class="meta">NLP-Assisted Textual Annotation</div>
						<div class="description">You could download a standard-off
							annotation file identifies the start and end position of the text
							it applies to with concrete standardized HPO concepts recognized.</div>
					</div>
					<div class="extra content">
						<div class="ui basic button" id="jsonDownloadButton"
							data-tooltip="Download the full annotation with position information as a json file">
							JSON</div>
					</div>

				</div>
			</div>


		</div>

	</div>
	<!-- agreement  -->

	<div class="ui modal" id="phiAgreement">
		<div class="header">Doc2Hpo Agreement</div>
		<div class="content">
			<p>1. Only de-identified notes could be uploaded</p>
			<p>2. Doc2Hpo don't take any responsibility for protecting PHI
				data</p>
			<p>
				3. This website uses cookies as well as similar tools and
				technologies to understand visitors' experiences. By continuing to
				use this website, you consent to Columbia University's usage of
				cookies and similar technologies, in accordance with the <a
					href="https://cuit.columbia.edu/content/columbia-university-website-cookie-notice">
					Columbia University Website Cookie Notice.</a>
			</p>
			<p>
				4. If you want to install your internal Doc2Hpo please visit the <a
					href="https://github.com/stormliucong/doc2hpo"> source </a>
			</p>

		</div>
		<div class="actions">
			<div class="ui black deny button" id="rejectAgreement">Reject</div>
			<div class="ui positive right labeled icon button">
				Accept<i class="checkmark icon"></i>
			</div>
		</div>
	</div>
	<div class="ui divider"></div>

	<div class="ui inverted vertical footer segment form-page">
		<div class="ui center aligned container">Columbia University
			&copy 2018. All Rights Reserved</div>
	</div>
	<!-- /.hidden for js only -->
	<input style="display: none;" id="basePath" name="basePath" value=".">
</body>

<script type="text/javascript" src="./js/helper.js"></script>
<script type="text/javascript" src="./js/parsing.js"></script>
<script type="text/javascript" src="./js/shoppingCart.js"></script>
<script type="text/javascript" src="./js/highlight.js"></script>
<script type="text/javascript" src="./js/hpo-search.js"></script>
<script type="text/javascript" src="./js/script.js"></script>
<script type="text/javascript" src="./js/updateSession.js"></script>

</html>
