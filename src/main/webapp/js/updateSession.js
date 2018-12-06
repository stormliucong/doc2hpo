function updateTermsInSession(start, length, hpo_id, hpo_term) {
	var basePath = $('input[id=basePath]').val();
	start = parseInt(start);
	length = parseInt(length);
	var formData = {
		'start' : start,
		'length' : length,
		'hpoId' : hpo_id,
		'hpoName' : hpo_term
	};
	console.log("formData before post: " + JSON.stringify(formData));
	$.blockUI({
		message : '<h3><img src="' + basePath
				+ '/img/squares.gif" /> Session Updating...</h3>',
		css : {
			border : '1px solid khaki'
		}
	});
	var t = $(window).scrollTop();

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : basePath + "/session/updateTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			console.log("session updated.\n");
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
				console.log(terms);
				highlight(terms);
				updateTable(terms);
				$('body,html').animate({
					'scrollTop' : t
				}, 200)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
	$('#searchPopup').removeClass('visible').addClass('hidden');


}

function deleteTermsInSession(start, length, hpo_id, hpo_term) {
	var basePath = $('input[id=basePath]').val();
	start = parseInt(start);
	length = parseInt(length);
	var formData = {
		'start' : start,
		'length' : length,
		'hpoId' : hpo_id,
		'hpoName' : hpo_term
	};
	$.blockUI({
		message : '<h3><img src="' + basePath
				+ '/img/squares.gif" /> Term Parsing...</h3>',
		css : {
			border : '1px solid khaki'
		}
	});
	
	var t = $(window).scrollTop();

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : basePath + "/session/deleteTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			console.log("session deleted.\n");
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
				console.log(terms);
				highlight(terms);
				updateTable(terms);
				$('body,html').animate({
					'scrollTop' : t
				}, 200)
				$("#phenolyzer").show();
			}
			return terms

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
	$('#searchPopup').removeClass('visible').addClass('hidden');

}

function addTermsInSession(start, length, hpo_id, hpo_term) {
	var basePath = $('input[id=basePath]').val();
	start = parseInt(start);
	length = parseInt(length);
	var formData = {
		'start' : start,
		'length' : length,
		'hpoId' : hpo_id,
		'hpoName' : hpo_term
	};
	$.blockUI({
		message : '<h3><img src="' + basePath
				+ '/img/squares.gif" /> Term Parsing...</h3>',
		css : {
			border : '1px solid khaki'
		}
	});
	var t = $(window).scrollTop();

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : basePath + "/session/addTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			console.log("session added.\n");
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
				console.log(terms);
				highlight(terms);
				updateTable(terms);
				$('body,html').animate({
					'scrollTop' : t
				}, 200)
				$("#phenolyzer").show();
			}
			return terms

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
	$('#searchPopup').removeClass('visible').addClass('hidden');

}

function addTermsInSessionWithHighlight(start, length, hpo_id, hpo_term) {
	var basePath = $('input[id=basePath]').val();
	start = parseInt(start);
	length = parseInt(length);
	var formData = {
		'start' : start,
		'length' : length,
		'hpoId' : hpo_id,
		'hpoName' : hpo_term
	};
	$.blockUI({
		message : '<h3><img src="' + basePath
				+ '/img/squares.gif" /> Term Parsing...</h3>',
		css : {
			border : '1px solid khaki'
		}
	});
	
	var t = $(window).scrollTop();

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : basePath + "/session/addTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			console.log("session added.\n");
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
				console.log(terms);
				highlight(terms);
				updateTable(terms);
				$('body,html').animate({
					'scrollTop' : t
				}, 200)
				$("#phenolyzer").show();
			}
			return terms

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
	$('#searchPopup').removeClass('visible').addClass('hidden');
}