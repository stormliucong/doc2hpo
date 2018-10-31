function parseMetamap(note) {
	var basePath = $('input[id=basePath]').val();
	var all_acros_abbrs = $('#all_acros_abbrs').is(':checked');
	var allow_concept_gaps = $('#allow_concept_gaps').is(':checked');
	var ignore_word_order = $('#ignore_word_order').is(':checked');
	var ignore_stop_phrases = $('#ignore_stop_phrases').is(':checked');
	var hpoOutput = $('#hpo').is(':checked');
	var anab = $('#anab').is(':checked');
	var fndg = $('#fndg').is(':checked');
	var cgab = $('#cgab').is(':checked');
	var dsyn = $('#dsyn').is(':checked');
	var genf = $('#genf').is(':checked');
	var mobd = $('#mobd').is(':checked');
	var sosy = $('#sosy').is(':checked');
	var lbtr = $('#lbtr').is(':checked');
	var patf = $('#patf').is(':checked');
	var formData = {
		'note' : note,
		'mmpgeneral' : {
			'aaa' : all_acros_abbrs,
			'acg' : allow_concept_gaps,
			'iwo' : ignore_word_order,
			"isp" : ignore_stop_phrases,
			'ho' : hpoOutput
		},
		'semantic' : {
			'anab' : anab,
			'fndg' : fndg,
			'cgab' : cgab,
			'dsyn' : dsyn,
			'genf' : genf,
			'mobd' : mobd,
			'sosy' : sosy,
			'lbtr' : lbtr,
			'patf' : patf,
		}

	};
	console.log("formData before post: " + JSON.stringify(formData));
	$.blockUI({
		message : '<h3><img src="' + basePath
				+ '/img/squares.gif" /> Term Parsing...</h3>',
		css : {
			border : '1px solid khaki'
		}
	});
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : basePath + "/parse/metamap",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			console.log("parse succuss.\n");
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
				console.log(terms);
				var hpoOption = data["hpoOption"];
				updateTable(terms);
				var t = $(window).scrollTop();
				$('body,html').animate({
					'scrollTop' : t + 1000
				}, 200)
				$("#phenolyzer").show();
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
}

function parseACT(note) {
	var basePath = $('input[id=basePath]').val();
	var formData = {
		'note' : note,
	};
	$.blockUI({
		message : '<h3><img src="' + basePath
				+ '/img/squares.gif" /> Term Parsing...</h3>',
		css : {
			border : '1px solid khaki'
		}
	});
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : basePath + "/parse/acdat",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			console.log("parse succuss.\n");
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
				console.log(terms);
				var hpoOption = data["hpoOption"];
				updateTable(terms);
				var t = $(window).scrollTop();
				$('body,html').animate({
					'scrollTop' : t + 1000
				}, 200)
				$("#phenolyzer").show();
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
}

function parseNcbo(note) {
	var basePath = $('input[id=basePath]').val();
	var longest_only = $('#longest_only').is(':checked');
	var whole_word_only = $('#whole_word_only').is(':checked');
	var exclude_numbers = $('#exclude_numbers').is(':checked');
	var formData = {
		'note' : note,
		'ncbogeneral' : {
			'lo': longest_only,
			'wwo': whole_word_only,
			'en': exclude_numbers
		}
	};
	$.blockUI({
		message : '<h3><img src="' + basePath
				+ '/img/squares.gif" /> Term Parsing...</h3>',
		css : {
			border : '1px solid khaki'
		}
	});
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : basePath + "/parse/ncbo",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			console.log("parse succuss.\n");
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
				console.log(terms);
				var hpoOption = data["hpoOption"];
				updateTable(terms);
				highlight(terms);
				var t = $(window).scrollTop();
				$('body,html').animate({
					'scrollTop' : t + 1000
				}, 200)
				$("#phenolyzer").show();
			}
			return terms

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
}

function parse_bak() {
	$("#phenolyzer").hide();
	refreshTable();

	var basePath = $('#basePath').val();
	console.log(basePath);
	var note = $("#note").val();
	note = removeNonAsc(note);
	if (note.length < 1) {
		alert("Input your note please!");
	} else {
		var e = document.getElementById("parsing-method");
		var radioMmp = e.options[e.selectedIndex].value;

		if (radioMmp == "1") {
			console.log("parse act");
			parseACT(note);
		}
		if (radioMmp == "2") {
			console.log("parse mmp");
			parseMetamap(note);
		}
		if (radioMmp == "3") {
			console.log("parse ncbo");
			parseNcbo(note);
		}

	}
}

function parse() {
	$("#phenolyzer").hide();
	refreshTable();

	var basePath = $('#basePath').val();
	console.log(basePath);
	var note = $("#note").val();
	note = removeNonAsc(note);
	if (note.length < 1) {
		alert("Input your note please!");
	} else {
		var e = document.getElementById("parsing-method");
		var radioMmp = e.options[e.selectedIndex].value;

		if (radioMmp == "1") {
			console.log("parse act");
			parsingJson = parseACT(note);
		}
		if (radioMmp == "2") {
			console.log("parse mmp");
			parsingJson = parseMetamap(note);
		}
		if (radioMmp == "3") {
			console.log("parse ncbo");
			parsingJson = parseNcbo(note);
		}

	}
	console.log("parse()" + parsingJson);
//	parsingJson = [{
//		'start': 5,
//		'length': 10,
//		'hpo_id': 'HP:0003002',
//		'hpo_term': 'Breast cancer'
//	},{
//		'start': 50,
//		'length': 100,
//		'hpo_id': 'HP:0002896',
//		'hpo_term': 'Liver cancer'
//	}]
	// Array of {start,end,hpo_id,hpo_term}
	return parsingJson
}