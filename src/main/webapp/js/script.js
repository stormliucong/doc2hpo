// custermized js.

function sendFeedback() {
	var basePath = $('input[id=basePath]').val();
	var email = $("#fbemail").val();
	var content = $("#fbcontent").val();
	$.ajax({
		type : 'POST',
		url : basePath + "cgi/feedback",
		data : {
			'email' : email,
			'content' : content

		},
		dataType : "json",
		success : function(data) {
			alert('Sent Successful!');
			$("#fbemail").val('');
			$("#fbcontent").val('');
		},
		error : function(data) {
			alert('Sent failed!');
		}
	})
	$('#myModal').modal('hide');
}

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

function reset() {
	var basePath = $('input[id=basePath]').val();
	console.log('reset!');
	$("#note").val('');
	$("#terms").val('');
	refreshTable();
	$("#phenolyzer").hide();
}

function phenolyzer() {
	var basePath = $('input[id=basePath]').val();
	console.log('phenolyzer!');
	var terms = $("#terms").val();
	var note = $("#note").val();
	if (terms.length < 1) {
		alert("Input your term please!");
	} else {
		$.blockUI({
			message : '<h3><img src="' + basePath
					+ '/img/squares.gif" /> Phenolyzing...</h3>',
			css : {
				border : '1px solid khaki'
			}
		});
		$.ajax({
			type : 'POST',
			url : basePath + "/gene/phenolyzer",
			data : {
				'terms' : terms,
			},
			dataType : "json",
			success : function(data) {
				console.log("parse succuss.\n");
				// store the object locally and retrieve it in another page.
				localStorage.setItem("terms", JSON.stringify(terms));
				localStorage.setItem("note", JSON.stringify(note));
				/*
				 * console.log(terms);
				 *///
				window.location.href = basePath + "phenolyzerPage.jsp";
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(url);
			}
		});
	}
}

function phenolyzerTmp() {
	window.open('http://phenolyzer.wglab.org/', '_blank');
}

function copyColumn(columnIndex) {
	console.log("clicked");
	var nameTerm = [];
	$('tbody tr').each(
			function() {
				nameTerm.push($(this).find('td:nth-child(' + columnIndex + ')')
						.text());
			})
	var copyString = nameTerm.join(";");
	console.log(copyString);
	copyToClipboard(copyString);
}

function copyToClipboard(text) {
	var dummy = document.createElement("input");
	document.body.appendChild(dummy);
	dummy.setAttribute('value', text);
	dummy.select();
	document.execCommand("copy");
	document.body.removeChild(dummy);
}

function refreshTable() {
	$('.table-add').unbind("click").click(
			function() {
				var $clone = $('#termTable').find('tr.hide').clone(true)
						.removeClass('hide table-line');
				$('#termTable').find('tbody').append($clone);
			});
	$('#termTable tbody').html('');
	$('.table-remove').unbind("click").click(function() {
		var basePath = $('input[id=basePath]').val();
		console.log("remove elements");
		$(this).parents('tr').remove();
	});
	$('#copyName').unbind("click").click(function() {
		copyColumn(1);
	});
	$('#copyId').unbind("click").click(function() {
		copyColumn(2);
	});
}
function updateTable(terms) {
	var basePath = $('input[id=basePath]').val();
	var $TABLE = $('#termTable');
	var pre = '<tr>'
	var pos = '</tr>'
	var cross = '<td><span class="table-remove glyphicon glyphicon-remove"></span></td>';
	Object
			.keys(terms)
			.forEach(
					function(key) {
						var url_base = "#";
						var re = /^HP/i;

						var name = key;
						var id = terms[key];
						var isHpo = re.test(id);
						if (isHpo == true) {
							url_base = 'http://compbio.charite.de/hpoweb/showterm?id=';
						} else {
							url_base = 'http://denote.rnet.ryerson.ca/umlsMap/pairs/indexframe.php?CUI=';
						}
						var idHref = url_base + id;
						var nameTd = '<td>' + name + '</td>';
						var idTd = '<td><a href="' + idHref
								+ '" target="_blank">' + id + '</a></td>';
						var clone = pre + nameTd + idTd + cross + pos;
						$TABLE.find('table').append(clone);
					});
	// have to define table-remove again inside the function.
	$('.table-remove').unbind("click").click(function() {
		var basePath = $('input[id=basePath]').val();
		console.log("remove elements");
		$(this).parents('tr').remove();
	});
}
function phenolyzerConfig() {

}

function testController() {
	var basePath = $('#basePath').val();
	var search = {
		pName : "bhanu",
		lName : "prasad"
	}
	console.log(JSON.stringify(search));
	$.ajax({
		type : "POST",
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},

		dataType : 'json',
		url : basePath + "/test/test1",
		data : JSON.stringify(search), // Note it is important
		success : function(result) {
			// do what ever you want with data
		}
	})
}

function tryAnExample() {
	var example = "Individual II-1 is a 10 year old boy. He was born at term with normal birth parameters and good APGAR scores (9/10/10). The neonatal period was uneventful, and he had normal motor development during early childhood: he began to look up at 3 months, sit by himself at 5 months, stand up at 11 months, walk at 13 months, and speak at 17 months. He attended a regular kindergarten, without any signs of difference in intelligence, compared to his peers. Starting at age 6, the parents observed ever increasing behavioral disturbance for the boy, manifesting in multiple aspects of life. For example, he can no longer wear clothes by himself, cannot obey instruction from parents/teachers, can no longer hold subjects tightly in hand, which were all things that he could do before 6 years of age. In addition, he no longer liked to play with others; instead, he just preferred to stay by himself, and he sometimes fell down when he walked on the stairs, which had rarely happened at age 5. The proband continued to deteriorate: at age 9, he could not say a single word and had no action or response to any instruction given in clinical exams. Additionally, rough facial features were noted with a flat nasal bridge, a synophrys (unibrow), a long and smooth philtrum, thick lips and an enlarged mouth. He also had rib edge eversion, and it was also discovered that he was profoundly deaf and had completely lost the ability to speak. He also had loss of bladder control. The diagnosis of severe intellectual disability was made, based on Wechsler Intelligence Scale examination. Brain MRI demonstrated cortical atrophy with enlargement of the subarachnoid spaces and ventricular dilatation (Figure 2). Brainstem evoked potentials showed moderate abnormalities. Electroencephalography (EEG) showed abnormal sleep EEG.";
	$("#note").val(removeNonAsc(example));
}

function removeNonAsc(rawString) {
	return rawString.replace(/[^\x00-\x7F]/g, "");
}

function showOptions() {
	var e = document.getElementById("parsing-method");
	var radioMmp = e.options[e.selectedIndex].value;
	console.log("radioMmp:", radioMmp);
	if (radioMmp == "1"){
		document.getElementById('mmp-option').style.display = "none";
		document.getElementById('ncbo-option').style.display = "none";
	}
	if (radioMmp == "2"){
		document.getElementById('mmp-option').style.display = "inline";
		document.getElementById('ncbo-option').style.display = "none";
	}
	if (radioMmp == "3") {
		document.getElementById('mmp-option').style.display = "none";
		document.getElementById('ncbo-option').style.display = "inline";
	}
	
	//	document.getElementById('semantic-option').style.display = vis;

}

function showNcbo() {
	var e = document.getElementById("parsing-method");
	var radioMmp = e.options[e.selectedIndex].value;
	console.log("radioMmp:", radioMmp);
	var vis = "none";
	if (radioMmp == "3") {
		console.log("3333");
		vis = "inline";

	} else {
		vis = "none";
	}
	document.getElementById('ncbo-option').style.display = vis;
	document.getElementById('semantic-option').style.display = vis;

}