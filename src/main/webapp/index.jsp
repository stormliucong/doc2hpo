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
<script type="text/javascript"
	src="http://malsup.github.io/min/jquery.blockUI.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/6.0.1/jquery.mark.es6.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/8.11.1/mark.es6.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/0.10.0/lodash.min.js"></script>

</head>


<body>
	<div class="ui fixed menu">
		<div class="ui header item">
			<img class="logo" src="./img/doc2hpo2.png"
				style="width: 120px; height: 50px;"> A webservice to extract
			human phenotype ontology terms from clinical notes or literatures.
		</div>
	</div>
	<div class="html ui top attached segment" style="margin: 100px auto 0;">
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
									class="description">a string-based method leveraging the
									Aho–Corasick algorithm </span>
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
							<input type="checkbox" id="allow_concept_gaps" checked> <label>allow
								concept gaps</label>
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

		<div class="row">
			<div class="ui two column very relaxed stackable divided grid">
				<div class="eleven wide column">
					<div class="ui three top attached steps">
						<div class="step">
							<i class="mouse pointer icon"></i>
							<div class="content">
								<div class="title">Highlight</div>
								<div class="description">Press q and use mouse to select
									the context</div>
							</div>
						</div>
						<div class="step">
							<i class="hand pointer outline icon"></i>
							<div class="content">
								<div class="title">Cancel</div>
								<div class="description">double click to cancel the
									highlighted context</div>
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
					<div class="ui segment">
						<p id="parsingResults" style="white-space: pre-wrap">Pellentesque
							habitant morbi tristique senectus et netus et malesuada fames ac
							turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies
							eget, tempor sit amet, ante. Donec eu libero sit amet quam
							egestas semper. Aenean ultricies mi vitae est. Mauris placerat
							eleifend leo.</p>
					</div>
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
				<div class="five wide column">
					<div class="container" style="padding-bottom: 20px">
						<table class="ui very basic collapsing celled table"
							id="shoppingCart" style="width: 100%">
							<thead>
								<tr>
									<th>Term</th>
									<th>Id</th>
									<th>Count</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="row" style="padding-bottom: 20px">
						<div class="ui buttons" id="buttonGroups">
							<div class="ui button" id="jsonDownloadButton"
								data-tooltip="Download the full annotation with position information as a json file">
								JSON</i>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="ui buttons" id="buttonGroups">

							<button class="ui button" id="copyColumnButton"
								data-tooltip="Copy the first column as Phenolyzer Input">Copy
								terms</button>
							<div class="ui blue labeled icon button" id="phenolyzerButton"
								data-tooltip="Redirect to Phenolyzer interface">
								Next <i class="right arrow icon"></i>
							</div>
						</div>

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
				3. If you want to install your internal Doc2Hpo please visit the <a
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

	<div class="ui inverted vertical footer segment form-page">
		<div class="ui center aligned container">Chunhua Weng Lab 2018.
			All Rights Reserved</div>
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
