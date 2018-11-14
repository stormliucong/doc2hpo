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

<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
<link rel="stylesheet" href="<%=basePath%>/css/styles.css">
<link rel="stylesheet" href="<%=basePath%>/css/ent-display.css">


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
	src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/1.11.8/semantic.min.js"></script>

</head>





<body>
	<div class="ui main text container">
		<h1 class="ui header">Doc2Hpo</h1>
	</div>

	<div class="ui borderless main menu">
		<div class="ui text container">
			<div class="header item">
				<img class="logo" src="<%=basePath%>/img/doc2hpo.png">
				Doc2Hpo: A webservice to extract hpo terms from clinical notes
			</div>
		</div>
	</div>

	<div class="html ui top attached segment">
		<div class="ui top attached label">Patient Description</div>
		<div class="ui form">
			<div class="field">
				<label>
					<div class="ui labeled icon mini button">
						<i class="external icon"></i> Try an example
					</div>
				</label>
				<textarea
					placeholder="Tips: feel free to use free-text to input the patient phenotype description"></textarea>
			</div>

			<div class="ui accordion field">
				<div class="title">
					<i class="icon dropdown"></i> Configurations
				</div>
				<div class="content field">
					<label class="transition hidden">Select a parsing engine:</label> <select
						class="ui fluid dropdown">
						<option value="act">Fast string match</option>
						<option value="mmp">Metamap</option>
						<option value="ncbo">Ncbo annotator</option>
					</select>
				</div>
				<!-- option for Metamap -->
				<div class="ui celled relaxed list transition hidden">
					<div class="inline field">
						<div class="ui checkbox">
							<input type="checkbox" tabindex="0" class="hidden"> <label>Checkbox1</label>
						</div>
					</div>
					<div class="inline field">
						<div class="ui checkbox hidden">
							<input type="checkbox" tabindex="0" class="hidden"> <label>Checkbox2</label>
						</div>
					</div>
					<div class="inline field">
						<div class="ui checkbox">
							<input type="checkbox" tabindex="0" class="hidden"> <label>Checkbox3</label>
						</div>
					</div>
					<div class="inline field">
						<div class="ui checkbox">
							<input type="checkbox" tabindex="0" class="hidden"> <label>Checkbox4</label>
						</div>
					</div>
				</div>

				<!-- Ncbo annotator -->
				<div class="ui celled relaxed list transition hidden">
					<div class="inline field">
						<div class="ui checkbox">
							<input type="checkbox" tabindex="0" class="hidden"> <label>Checkbox1</label>
						</div>
					</div>
					<div class="inline field">
						<div class="ui checkbox hidden">
							<input type="checkbox" tabindex="0" class="hidden"> <label>Checkbox2</label>
						</div>
					</div>
					<div class="inline field">
						<div class="ui checkbox">
							<input type="checkbox" tabindex="0" class="hidden"> <label>Checkbox3</label>
						</div>
					</div>
					<div class="inline field">
						<div class="ui checkbox">
							<input type="checkbox" tabindex="0" class="hidden"> <label>Checkbox4</label>
						</div>
					</div>
				</div>
				<div class="ui secondary submit button">Submit</div>
			</div>
		</div>
	</div>

	<div class="html ui top attached segment">
		<div class="ui top attached label">Parsing Results</div>
		<div class="ui two column very relaxed stackable divided grid">
			<div class="ten wide column">
				<div class="ui raised segment">
					<p>Pellentesque habitant morbi tristique senectus et netus et
						malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat
						vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit
						amet quam egestas semper. Aenean ultricies mi vitae est. Mauris
						placerat eleifend leo.</p>
				</div>
			</div>
			<div class="six wide column">
				<table class="ui compact celled definition table">
					<thead>
						<tr>
							<th></th>
							<th>HPO Name</th>
							<th>HPO Id</th>
							<th>count</th>
						</tr>
					</thead>
					<tbody>
						<tr class="transition hidden" id="template">
							<td class="collapsing">
								<div class="ui fitted slider checkbox">
									<input type="checkbox"> <label></label>
								</div>
							</td>
							<td>HPO Name</td>
							<td>HPO Id</td>
							<td>0</td>
						</tr>
					</tbody>
					<tfoot class="full-width">
						<tr>
							<th></th>
							<th colspan="4">
								<div class="ui right floated small primary labeled icon button">
									<i class="user icon"></i> Add HPO terms
								</div>
								<div class="ui small button">Approve change</div>
							</th>
						</tr>
					</tfoot>
				</table>
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

	<!-- /.hidden for js only -->
	<input style="display: none;" id="basePath" name="basePath"
		value="<%=basePath%>" />
	<script>
		$('.ui.dropdown').dropdown();
		$('.ui.accordion').accordion();
	</script>
</body>

<script type="text/javascript" src="<%=basePath%>/js/parsing.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/shoppingCart.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/highlight.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/hpo-search.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/script.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/updateSession.js"></script>

</html>
