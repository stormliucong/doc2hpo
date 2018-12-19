function updateTermsInSession(start, length, hpo_id, hpo_term) {
	start = parseInt(start);
	length = parseInt(length);
	var formData = {
		'start' : start,
		'length' : length,
		'hpoId' : hpo_id,
		'hpoName' : hpo_term
	};
	$.blockUI({
		message : '<h3><img src="./img/squares.gif" /> Session Updating...</h3>',
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
		url : "/doc2hpo/session/updateTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
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
	start = parseInt(start);
	length = parseInt(length);
	var formData = {
		'start' : start,
		'length' : length,
		'hpoId' : hpo_id,
		'hpoName' : hpo_term
	};
	$.blockUI({
		message : '<h3><img src="./img/squares.gif" /> Term Parsing...</h3>',
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
		url : "/doc2hpo/session/deleteTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
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
	start = parseInt(start);
	length = parseInt(length);
	var formData = {
		'start' : start,
		'length' : length,
		'hpoId' : hpo_id,
		'hpoName' : hpo_term
	};
	$.blockUI({
		message : '<h3><img src="./img/squares.gif" /> Term Parsing...</h3>',
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
		url : "/doc2hpo/session/addTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
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
	start = parseInt(start);
	length = parseInt(length);
	var formData = {
		'start' : start,
		'length' : length,
		'hpoId' : hpo_id,
		'hpoName' : hpo_term
	};
	$.blockUI({
		message : '<h3><img src="./img/squares.gif" /> Term Parsing...</h3>',
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
		url : "/doc2hpo/session/addTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
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