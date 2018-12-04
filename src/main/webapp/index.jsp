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
<link rel="stylesheet" href="<%=basePath%>/css/styles.css">
<link rel="stylesheet" href="<%=basePath%>/css/ent-display.css">

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
		<div class="header item">
			<img class="logo" src="<%=basePath%>/img/doc2hpo2.png"
				style="width: 120px; height: 50px;"> A webservice to extract
			human phenotype ontology terms from clinical notes
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
					<label class="transition hidden">Select a parsing engine:</label> <select
						class="ui fluid dropdown" id="parsingEngine">
						<option value="act">Fast string match</option>
						<option value="mmp">Metamap</option>
						<option value="ncbo">Ncbo annotator</option>
					</select>
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
				<div class="ten wide column">
					<div class="ui raised segment">
						<p id="parsingResults" style="white-space: pre-wrap">Pellentesque habitant morbi tristique
							senectus et netus et malesuada fames ac turpis egestas.
							Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit
							amet, ante. Donec eu libero sit amet quam egestas semper. Aenean
							ultricies mi vitae est. Mauris placerat eleifend leo.</p>
					</div>
				</div>
				<div class="six wide column">
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

							<button class="ui button" id="copyColumnButton">Copy
								terms</button>
							<div class="or"></div>
						</div>
					</div>
					<div class="row">
						<div class="ui blue labeled icon button" id="phenolyzerButton">
							Next <i class="right arrow icon"></i>
						</div>

					</div>


				</div>
			</div>
		</div>
	</div>


	<!--  POP UP -->
	<div class="ui custom popup top left transition hidden"
		id="searchPopup">
		<div style="display: none" id="HpoNameEntity"></div>
		<div class="ui fluid search" id='hpoSearch'>
			<input class="prompt" type="text" placeholder="Search HPO...">
			<div style="display: none" id="selectedResult"></div>
			<i class="plus circle icon" id="addHpoTerm"></i>
		</div>

	</div>
	<div class="ui inverted vertical footer segment form-page">
		<div class="ui center aligned container">Chunhua Weng Lab 2018.
			All Rights Reserved</div>
	</div>
	<!-- /.hidden for js only -->
	<input style="display: none;" id="basePath" name="basePath"
		value="<%=basePath%>">

</body>

<script type="text/javascript" src="<%=basePath%>/js/helper.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/parsing.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/shoppingCart.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/highlight.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/hpo-search.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/script.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/updateSession.js"></script>

</html>
