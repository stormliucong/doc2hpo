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
<!-- Add IntroJs styles -->
<link href="./css/introjs.css" rel="stylesheet">

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


	<div class='pusher' style="padding: 100px">
		<div class="full height">
			<div class="ui container">
				<h1 class="ui header">
					<span class="library"> Doc2Hpo </span>

				</h1>
				<span> A webservice to extract human phenotype ontology terms
					from free-text based clinical notes or literatures. </span>
				<div class="ui hidden divider"></div>
			</div>
		</div>
		<div class="ui container">
			<div class="ui vertical segments">
				<div class="ui segment">
					<p>
						<img src="./img/step1.gif">
					</p>
				</div>
				<div class="ui segment">
					<div class="completed step">

						<div class="content">
							<div class="title">
								<i class="info icon"></i>STEP 1: Click "Example" to load
								examples. You can also type in free-text.
							</div>
							<div class="description"></div>
						</div>
					</div>

				</div>

			</div>
			<div class="ui vertical segments">
				<div class="ui segment">
					<p>
						<img src="./img/step2.gif">
					</p>
				</div>
				<div class="ui segment">
					<div class="completed step">

						<div class="content">
							<div class="title">
								<i class="info icon"></i>STEP 2: Select and configure the
								parsing engines. (read details from publication...)
							</div>
							<div class="description"></div>
						</div>
					</div>
				</div>

			</div>
			<div class="ui vertical segments">
				<div class="ui segment">
					<p>
						<img src="./img/step3.gif">
					</p>
				</div>
				<div class="ui segment">
					<div class="completed step">

						<div class="content">
							<div class="title">
								<i class="info icon"></i>STEP 3: Hold keyboard button <img
									src="./img/letter-q-key-keyboard-512.png"
									style="height: 1.5em; width: auto"> and use mouse to
								select text you want to annotate.
							</div>
							<div class="description"></div>
						</div>
					</div>
				</div>
			</div>
		<div class="ui vertical segments">
			<div class="ui segment">
				<p>
					<img src="./img/step4.gif">
				</p>
			</div>
			<div class="ui segment">
				<div class="completed step">
					<div class="content">
						<div class="title">
							<i class="info icon"></i>STEP 4: click the dark green button to
							search HPO terms.
						</div>
						<div class="description"></div>
					</div>
				</div>
			</div>

		</div>
		<div class="ui vertical segments">
			<div class="ui segment">
				<p>
					<img src="./img/step5.gif">
				</p>
			</div>
			<div class="ui segment">
				<div class="completed step">
					<div class="content">
						<div class="title">
							<i class="info icon"></i>STEP 5: Double click the highlight text
							to remove annotation.
						</div>
						<div class="description"></div>
					</div>
				</div>
			</div>

		</div>
		<div class="ui vertical segments">
			<div class="ui segment">
				<p>
					<img src="./img/step6.gif">
				</p>
			</div>
			<div class="ui segment">
				<div class="completed step">
					<div class="content">
						<div class="title">
							<i class="info icon"></i>STEP 6: Change HPO terms by clicking
							dark green box.
						</div>
						<div class="description"></div>
					</div>
				</div>
			</div>

		</div>
		<div class="ui vertical segments">
			<div class="ui segment">
				<p>
					<img src="./img/step7.gif">
				</p>
			</div>
			<div class="ui segment">
				<div class="completed step">
					<div class="content">
						<div class="title">
							<i class="info icon"></i>STEP 7: Save sentence level annotation
							as JSON output, which includes start and end for each tag.
						</div>
						<div class="description"></div>
					</div>
				</div>
			</div>

		</div>
		<div class="ui vertical segments">
			<div class="ui segment">
				<p>
					<img src="./img/step8.gif">
				</p>
			</div>
			<div class="ui segment">
				<div class="completed step">
					<div class="content">
						<div class="title">
							<i class="info icon"></i>STEP 8: Save document level extraction
							as excel or pdf.
						</div>
						<div class="description"></div>
					</div>
				</div>
			</div>

		</div>
		<div class="ui vertical segments">
			<div class="ui segment">

				<p>
					<img src="./img/step9.gif">
				</p>
			</div>
			<div class="ui segment">
				<div class="completed step">
					<div class="content">
						<div class="title">
							<i class="info icon"></i>STEP 9: Copy terms and paste them into
							Phenolyzer.
						</div>
						<div class="description"></div>
					</div>
				</div>
			</div>

		</div>
	</div>

	<div class="ui inverted vertical footer segment form-page">
		<div class="ui center aligned container">Columbia University
			&copy 2018. All Rights Reserved</div>
	</div>
	<!-- /.hidden for js only -->
	<input style="display: none;" id="basePath" name="basePath" value=".">
</body>
</html>
