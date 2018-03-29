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
function parse() {
	$("#phenolyzer").hide();
	refreshTable();
	
	var basePath = $('#basePath').val();
	console.log(basePath);
	var note = $("#note").val();
	if (note.length < 1) {
		alert("Input your note please!");
	} else {
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
			'general' : {
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
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			type : 'POST',
			url : basePath + "/parse/matmap",
			data : JSON.stringify(formData),
			dataType : "json",
			success : function(data) {
				console.log("parse succuss.\n");
				var terms = data["hmName2Id"];
				if(jQuery.isEmptyObject(terms)){
					alert("No UMLS or HPO terms found!");
				}else{
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

}
function reset() {
	var basePath = $('input[id=basePath]').val();
	console.log('reset!');
	$("#note").val('');
	$("#terms").val('');
	refreshTable();
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

function copyColumn(columnIndex){
	console.log("clicked");
	var nameTerm = [];
	$('tbody tr').each(function(){
		nameTerm.push($(this).find('td:nth-child(' + columnIndex + ')').text());
	})
	var copyString = nameTerm.join(";");
	console.log(copyString);
	copyToClipboard(copyString);
}

function copyToClipboard(text){
    var dummy = document.createElement("input");
    document.body.appendChild(dummy);
    dummy.setAttribute('value', text);
    dummy.select();
    document.execCommand("copy");
    document.body.removeChild(dummy);
}

function refreshTable(){
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
	$('#copyName').unbind("click").click(function(){
		copyColumn(1);
	});
	$('#copyId').unbind("click").click(function(){
		copyColumn(2);
	});
}
function updateTable(terms) {
	var basePath = $('input[id=basePath]').val();
	var $TABLE = $('#termTable');
	var pre = '<tr>'
	var pos = '</tr>'
	var cross = '<td><span class="table-remove glyphicon glyphicon-remove"></span></td>';
	Object.keys(terms).forEach(
			function(key) {
				var url_base = "#";
				var re = /^HP/i;

				var name = key;
				var id = terms[key];
				var isHpo = re.test(id);
				if(isHpo == true){
					url_base = 'http://compbio.charite.de/hpoweb/showterm?id=';
				}else{
					url_base = 'http://denote.rnet.ryerson.ca/umlsMap/pairs/indexframe.php?CUI=';
				}
				var idHref = url_base + id;
				var nameTd = '<td>' + name + '</td>';
				var idTd = '<td><a href="' + idHref + '" target="_blank">' + id
						+ '</a></td>';
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
		headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		
		dataType : 'json',
		url : basePath + "/test/test1",
		data : JSON.stringify(search), // Note it is important
		success : function(result) {
			// do what ever you want with data
		}
	})
}