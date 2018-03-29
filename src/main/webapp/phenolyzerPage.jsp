<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//String workDir = (String)session.getAttribute("dir"); 
	String workDir = basePath + "work/asdfghjkl";
%>
<html>
<meta charset="utf-8">
<title>Note2Gene</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="Genetic Phenotype EHR,">
<meta name="author"
	content="Cong Liu,  Chi Yuan, Kai Wang, Chunhua Weng">
<meta name="robots" content="index,follow">

<head>
<!-- Site CSS -->
<!--  <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"> -->
<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
<link href="<%=basePath%>/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=basePath%>/css/site.min.css?v5" rel="stylesheet">
<style>
.job-hot {
	position: absolute;
	color: #d9534f;
	right: 0;
	top: 15px;
}

.col-center-block {
	float: none;
	display: block;
	margin-left: auto;
	margin-right: auto;
}

.eliback {
	color: white;
	background-color: #1E90FF;
	position: relative;
}

.navback {
	background-color: #428bca;
	color: white;
}

.conceptsetbody {
	top: 25px;
}
</style>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<!-- Custom styles for this template -->
<link href="<%=basePath%>css/dashboard.css" rel="stylesheet">
<link href="<%=basePath%>css/ner.css" rel="stylesheet">
<link href="<%=basePath%>css/jquery.cytoscape.js-panzoom-mod.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/bootstrap-switch.min.css" rel="stylesheet">

<link
	href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href="https://static.bootcss.com/www/assets/css/site.min.css?v5"
	rel="stylesheet">
<link rel="stylesheet" href="<%=basePath%>css/bootstrap-table.min.css">
<link rel="stylesheet" href="<%=basePath%>css/bootstrap-select.min.css">
<link rel="stylesheet" href="<%=basePath%>css/phenolyzer.css">


<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="<%=basePath%>js/ie-emulation-modes-warning.js"></script>
<script src="<%=basePath%>js/jquery.min.js"></script>
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="<%=basePath%>js/ie10-viewport-bug-workaround.js"></script>
<script src="<%=basePath%>js/bootstrap-table.min.js"></script>
<script type="text/javascript"
	src="http://malsup.github.io/min/jquery.blockUI.min.js"></script>
<script src="<%=basePath%>js/d3.js" charset="utf-8"></script>
<script src="<%=basePath%>js/d3.layout.cloud.js"></script>
<script src="<%=basePath%>js/d3.wordcloud.js"></script>
<script src="<%=basePath%>js/bootstrap-switch.min.js"></script>
<script src="<%=basePath%>js/cytoscape.min.js" ></script>
<script src="<%=basePath%>js/arbor.js" ></script>
<script src="<%=basePath%>js/jquery.cytoscape.js-panzoom.js"></script>
<script src="<%=basePath%>js/bootstrap-select.min.js"></script>
<script src="<%=basePath%>js/network.js"></script>
<script src="<%=basePath%>js/barplot.js"></script>

</head>

<body>
	<div class="navbar navbar-inverse navbar-fixed-top navback">
		<div class="container">
			<div class="navbar-header">
				<button class="navbar-toggle collapsed" type="button"
					data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand hidden-sm" href="<%=basePath%>"
					style="color: white"
					onclick="_hmt.push(['_trackEvent', 'navbar', 'click', 'navbar-mainpage'])">Note2Gene</a>
			</div>
			<div class="navbar-collapse collapse" role="navigation">
				<ul class="nav navbar-nav">
				</ul>
			</div>
		</div>
	</div>

	<div class="container projects">
		<div class="page-header projects-header">Gene report</div>
		<div class="container tab-result">
			<div class="col-lg-11">
				<ul class="nav nav-tabs" role="tablist">
					<li class="active"><a href="#summary" role="tab"
						data-toggle="tab">Summary</a></li>
					<li><a href="#network" role="tab" data-toggle="tab">Network</a></li>
					<li><a href="#barplot" role="tab" data-toggle="tab">Barplot</a></li>
					<li><a href="#wordcloud" role="tab" data-toggle="tab">WordCloud</a></li>
					<li><a href="#details" role="tab" data-toggle="tab">Details</a></li>
				</ul>

				<div class="tab-content">
					<div id="summary" class="tab-pane fade in active">
						<h3>Summary</h3>
						<li class="list-group-item" id="length_of_report"></li>
						<li class="list-group-item" id="length_of_term"></li>
						<li class="list-group-item" id="work_dir"></li>

					</div>
					<div id="network" class="tab-pane fade">
						<h3>Network</h3>
						<div class="form-group">
							<button type="button" class="btn btn-default btn-success"
								id="save_photo">
								<span class="glyphicon glyphicon-picture"></span> Save Photo
							</button>
							<button type="button" class="btn btn-primary" id="tooltips">
								<span class="glyphicon glyphicon-list"></span> Tooltips
							</button>
						</div>
						<div id="cy"></div>
						<br>
						<div id="network_control" class="panel panel-primary">
							<label class="text-primary" for="disease_on">Disease</label> <input
								checked type="checkbox" id="disease_on"> <label
								class="text-primary" for="gene_on">Gene</label> <input checked
								type="checkbox" id="gene_on"> <label
								class="text-primary" for="show_gene_names">Gene Name</label> <input
								type="checkbox" checked id="show_gene_names"> <label
								class="text-primary" for="show_disease_names">Disease
								Name</label> <input type="checkbox" id="show_disease_names">
						</div>

						<div class="panel panel-primary" id="network_control_2">
							<div class="form-group">
								<label class="col-lg-2 result control-labe text-primary"
									for="show_edges">Interactions:</label>
								<div class="col-lg-3">
									<select id="show_edges"
										class="selectpicker show-menu-arrow" name="show_edges">
										<option value="all">All</option>
										<option selected value="HPRD">Protein Interaction</option>
										<option value="Biosystem">In the same Biosystem</option>
										<option value="GeneFamily">In the same Gene Family</option>
										<option value="TranscriptionInteraction">Transcription
											Interaction</option>
									</select>
								</div>

								<label class="col-lg-1 result control-label text-primary"
									for="adjust_layout">Layout: </label>
								<div class="col-lg-2">
									<select id="adjust_layout" name="adjust_layout"
										class="selectpicker show-menu-arrow">
										<option value="force">Force</option>
										<option value="circle">Circle</option>
										<option selected value="grid">Grid</option>
										<option value="concentric">Concentric</option>
									</select>
								</div>
							</div>
						</div>
					</div>

					<div id="barplot" class="tab-pane fade">
						<h3>Barplot</h3>
						
					</div>
					<div id="wordcloud" class="tab-pane fade">
						<h3>Word Cloud</h3>
						<div id="wc"></div>
					</div>
					<div id="details" class="tab-pane fade">
						<h3>Details</h3>
						<p>Eaque ipsa quae ab illo inventore veritatis et quasi
							architecto beatae vitae dicta sunt explicabo.</p>
					</div>

					<div id="accordion"></div>
				</div>
			</div>
		</div>
	</div>

	<div	 style="display:none" id="workdir"><%=workDir%></div>


	<script>
		$(function() {
			var word = localStorage.getItem("terms");
			var note = localStorage.getItem("note");
			summary();
		});
		function summary() {
			/* var word_length = lengthOfString(word);
			var note_length = lengthOfString(note); */
			var note_length = 1234;
			var word_length = 12;
			var work_dir = $('#workdir').text();
			$('#work_dir').text('working dir is ' + work_dir);
			$('#length_of_report').text(
					'the length of the description is ' + note_length);
			$('#length_of_term').text(
					'the number of terms detected is ' + word_length);			
			
		}
		function lengthOfString(str) {
			var arr = str.split();
			return (arr);
		}
		function addSize(words, size) {
			var words_size = [];
			for ( var i in words) {
				words_size.push({
					'text' : words[i],
					'size' : size
				})
			}
			return (words_size);
		}
		function drawWordCloud(words, note) {
			var term_words = words.trim().split(" ");
			var note_words = note.trim().split("\n");
			console.log("width: " + $('#wc').width());
			term_size = addSize(term_words, 40);
			note_size = addSize(term_words, 10);
			word_size = term_size.concat(note_size);
			d3.wordcloud().size(
					[ $('#wc').width(), $('#wc').height() ])
					.selector('#wc').words(word_size).start();
		}
	</script>
</body>
</html>
