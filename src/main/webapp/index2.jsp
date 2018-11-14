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
<meta name="keywords" content="Genetic Phenotype EHR,">
<meta name="author"
	content="Cong Liu,  Chi Yuan, Kai Wang, Chunhua Weng">
<meta name="robots" content="index,follow">

<!-- Site CSS -->
<link
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">
<%-- <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
 --%>
<link href="<%=basePath%>/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=basePath%>/css/site.min.css?v5" rel="stylesheet">
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<!-- Custom styles for this template -->
<link href="<%=basePath%>/css/dashboard.css" rel="stylesheet">
<link href="<%=basePath%>css/font-awesome.min.css" rel="stylesheet">
<link href="<%=basePath%>css/site.min.css?v5" rel="stylesheet">
<link rel="stylesheet" href="<%=basePath%>/css/bootstrap-table.min.css">
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">

<link rel="stylesheet" href="<%=basePath%>/css/styles.css">
<link rel="stylesheet" href="<%=basePath%>/css/ent-display.css">


<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="<%=basePath%>/js/ie-emulation-modes-warning.js"></script>
<script src="<%=basePath%>/js/jquery.min.js"></script>
<script src="<%=basePath%>/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="<%=basePath%>/js/ie10-viewport-bug-workaround.js"></script>
<script src="<%=basePath%>/js/bootstrap-table.min.js"></script>
<script src="<%=basePath%>/js/lodash.min.js"></script>
<script type="text/javascript"
	src="http://malsup.github.io/min/jquery.blockUI.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/6.0.1/jquery.mark.es6.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/8.11.1/mark.es6.min.js"></script>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"
	integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.js"></script>
<script src="<%=basePath%>/js/jquery-textrange.js"></script>

</head>





<body>
	<div class="navbar navbar-inverse navbar-fixed-top navback">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" style="padding: 3px"> <img
					src="<%=basePath%>/img/doc2hpo.png" style="height: 100%"></a>
			</div>
		</div>
	</div>
	<div class="container projects">
		<div class="projects-header"></div>


		<!-------------------------------------------------------------------------------------------------------------------------------------->
		<!----------------------------------------------------------TEXTINPUT PANEL START------------------------------------------------------->
		<!-------------------------------------------------------------------------------------------------------------------------------------->
		<div class="col-sm-12 col-md-12 col-lg-12 col-xl-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="#collapseOne"><span
							class="glyphicon glyphicon-pencil"></span> Patient Description </a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="form-group">
							<span class="help-block">Tips: feel free to use free-text
								to input the patient phenotype description</span>
							<button type="button" class="btn btn-default" id="textExample">Try
								an example</button>
							<textarea class="form-control" rows="12" id="note"></textarea>
							<p class="masthead-button-links">
								<a class="btn btn-success" id="parse" target="_blank">&nbsp;&nbsp;Parse&nbsp;&nbsp;</a>
								<a class="btn btn-info" id="reset">&nbsp;&nbsp;&nbsp;Reset&nbsp;&nbsp;&nbsp;</a>

							</p>
						</div>




					</div>
				</div>
			</div>
		</div>

		<!-------------------------------------------------------------------------------------------------------------------------------------->
		<!-----------------------------------------------------------TEXTINPUT PANEL END-------------------------------------------------------->
		<!-------------------------------------------------------------------------------------------------------------------------------------->



		<!-------------------------------------------------------------------------------------------------------------------------------------->
		<!---------------------------------------------------------- CONFIG PANEL START -------------------------------------------------------->
		<!-------------------------------------------------------------------------------------------------------------------------------------->

		<div class="col-sm-12 col-md-12 col-lg-12 col-xl-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="#collapseConfig" class="collapsed"><span
							class="glyphicon glyphicon-cog"></span> Configuration </a>
					</h4>
				</div>
				<div id="collapseConfig" class="panel-collapse collapse"
					style="height: 0px;">
					<div class="panel-body">


						<div class="form-group col-sm-12 col-md-12 col-lg-12">
							<label>Select a parsing method:</label> <select
								class="form-control" id="parsing-method"
								onchange="showOptions()">
								<option value="1">fast string match</option>
								<option value="2">metamap</option>
								<option value="3">ncbo annotator</option>
							</select>
						</div>

						<div id="mmp-option"
							class="form-group col-sm-12 col-md-12 col-lg-12"
							style="display: none">
							<label>mmp general configuration</label>
							<form class="form-inline">
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="allow acronym variants"
											id="all_acros_abbrs" type="checkbox" checked='checked'>
											allow acronym variants
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="allow concept gaps"
											id="allow_concept_gaps" type="checkbox" checked='checked'>
											allow concept gaps
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="ignore word order"
											id="ignore_word_order" type="checkbox" checked>
											ignore word order
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="ignore stop phrases"
											id="ignore_stop_phrases" type="checkbox" checked>
											ignore stop phrases
										</label>
									</div>
								</div>
							</form>
						</div>

						<div id="ncbo-option"
							class="form-group col-sm-12 col-md-12 col-lg-12"
							style="display: none">
							<label>ncbo general configuration</label>
							<form class="form-inline">
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="match longest only"
											id="longest_only" type="checkbox" checked='checked'>
											Match longest only
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="whole word only"
											id="whole_word_only" type="checkbox"> Whole word only
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="exclude numbers"
											id="exclude_numbers" type="checkbox"> Exclude numbers
										</label>
									</div>
								</div>
							</form>
						</div>

						<div id="semantic-option"
							class="form-group col-sm-12 col-md-12 col-lg-12"
							style="display: none">
							<label>Select semantic types</label>
							<form class="form-inline">
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="Anatomical Abnormality"
											id="anab" type="checkbox"> Anatomical Abnormality
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="Finding" id="fndg"
											type="checkbox"> Finding
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="Congenital Abnormality"
											id="cgab" type="checkbox"> Congenital Abnormality
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="Disease or Syndrome" id="dsyn"
											type="checkbox"> Disease or Syndrome
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="Genetic Function" id="genf"
											type="checkbox"> Genetic Function
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input
											title="Mental or Behavioral Dysfunction" id="mobd"
											type="checkbox"> Mental or Behavioral Dysfunction
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="Sign or Symptom" id="sosy"
											type="checkbox"> Sign or Symptom
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="Laboratory or Test Result"
											id="lbtr" type="checkbox"> Laboratory or Test Result
										</label>
									</div>
								</div>
								<div class="form-group col-sm-12 col-md-12 col-lg-12">
									<div class="checkbox">
										<label> <input title="Pathologic Function" id="patf"
											type="checkbox"> Pathologic Function
										</label>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-------------------------------------------------------------------------------------------------------------------------------------->
		<!---------------------------------------------------------- CONFIG PANEL END ---------------------------------------------------------->
		<!-------------------------------------------------------------------------------------------------------------------------------------->



		<!-------------------------------------------------------------------------------------------------------------------------------------->
		<!---------------------------------------------------------- MAPPING RESULTS PANEL START ----------------------------------------------->
		<!-------------------------------------------------------------------------------------------------------------------------------------->

		<div id="conceptMappingResults" class="col-sm-12 col-md-6 col-lg-6"
			style="display: none">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="#collapseThree"><span
							class="glyphicon glyphicon-shopping-cart"></span> Phenotype Terms
						</a>
					</h4>
				</div>
				<div id="collapseThree" class="panel-collapse collapse in">
					<div class="panel-body context">
						<p id='parsing-results'
							class="d-content l-wrapper u-b-lg l-wrapper--page u-t-md"></p>
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


		<!-------------------------------------------------------------------------------------------------------------------------------------->
		<!---------------------------------------------------------- MAPPING RESULTS PANEL END ------------------------------------------------->
		<!-------------------------------------------------------------------------------------------------------------------------------------->


		<!-------------------------------------------------------------------------------------------------------------------------------------->
		<!---------------------------------------------------------- SHOPPING CART PANEL START ------------------------------------------------->
		<!-------------------------------------------------------------------------------------------------------------------------------------->

		<div id="shoppingCart" class="col-sm-12 col-md-12 col-lg-6 col-xl-6"
			style="display: none">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="#collapseTwo"><span
							class="glyphicon glyphicon-shopping-cart"></span> Phenotype Terms
						</a>
					</h4>
				</div>
				<div id="collapseTwo" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="form-group">
							<span class="help-block">Tips: Don't like it? Provide your
								own phenotypes</span>
							<div id="termTable" class="table-editable">
								<table class="table">
									<thead>
										<tr>
											<th>Name <span
												title="Tips: copy the column and paste it in phenolyzer query box"
												id="copyName" class="glyphicon glyphicon-share"></span></th>
											<th>Id <span
												title="Tips: copy the column and paste it in phenolyzer query box"
												id="copyId" class="glyphicon glyphicon-share"></span></th>
											<th>count</th>
											<th class="text-center"><span
												class="table-add glyphicon glyphicon-plus"></span></th>
										</tr>
										<!-- This is our clonable table line -->
										<tr class="hide">
											<td contenteditable="true">Untitled</td>
											<td contenteditable="true">undefined</td>
											<td class="text-center"><span
												class="table-remove glyphicon glyphicon-remove"></span></td>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-------------------------------------------------------------------------------------------------------------------------------------->
		<!---------------------------------------------------------- SHOPPING CART PANEL END --------------------------------------------------->
		<!-------------------------------------------------------------------------------------------------------------------------------------->

		<div class="col-sm-12 col-md-12 col-lg-12">
			<p class="masthead-button-links" id="phenolyzer"
				style="display: none">
				<a class="btn btn-primary">&nbsp;&nbsp;Next Step&nbsp;&nbsp;</a> <span
					class="glyphicon glyphicon-play"></span> Next step will direct to
				the phenolyzer page

			</p>
		</div>
	</div>

	<!-- /.container -->
	<!-- container -->
	<div class="container">
		<div class="footer col-lg-offset-1">
			<p class="text-muted">
				All Rights Reserved @Chunhua Weng Lab 2010-
				<script type="text/javascript">
					document.write(new Date().getFullYear());
				</script>
			</p>
		</div>
	</div>


	<!-- /.hidden for js only -->
	<input style="display: none;" id="basePath" name="basePath"
		value="<%=basePath%>" />

</body>

<script type="text/javascript" src="<%=basePath%>/js/parsing.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/shoppingCart.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/highlight.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/hpo-search.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/script.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/updateSession.js"></script>

</html>
