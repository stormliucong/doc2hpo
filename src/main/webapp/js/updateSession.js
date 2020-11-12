function updateTermsInSession(start, length, hpo_id, hpo_term,is_negated) {
	start = parseInt(start);
	length = parseInt(length);
	var formData = {
		'start' : start,
		'length' : length,
		'hpoId' : hpo_id,
		'hpoName' : hpo_term,
		'negated' : is_negated
	};
	$.blockUI({
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">updating...</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
	});
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : "session/updateTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var parsingJson = data["hmName2Id"];
			var terms = longestParsingJson(parsingJson);
			terms=terms.filter(Boolean)
			if (terms == 'ERROR') {
				alert("ERROR: Something wrong with act engine. Please check the configuration on server end.");
			} else {
				if (!Array.isArray(terms) || !terms.length) {
					alert("No HPO terms found!");
					$('#genePanel').hide();
					$("#parsingResultsPanel").hide();
					$("#outputPanel").hide();
					$("#externalPanel").hide();
				} else {
					$('#genePanel').hide();
					highlight(terms);
					updateTable(terms);
				}

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
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">updating...</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
	});
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : "session/deleteTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var parsingJson = data["hmName2Id"];
			var terms = longestParsingJson(parsingJson);
			terms=terms.filter(Boolean)
			if (terms == 'ERROR') {
				alert("ERROR: Something wrong with act engine. Please check the configuration on server end.");
			} else {
				if (!Array.isArray(terms) || !terms.length) {
					alert("No HPO terms found!");
					$('#genePanel').hide();
					$("#parsingResultsPanel").hide();
					$("#outputPanel").hide();
					$("#externalPanel").hide();
				} else {
					$('#genePanel').hide();
					highlight(terms);
					updateTable(terms);
				}

			}

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
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">updating...</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
	});

	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : "session/addTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var parsingJson = data["hmName2Id"];
			var terms = longestParsingJson(parsingJson);
			terms=terms.filter(Boolean)
			if (terms == 'ERROR') {
				alert("ERROR: Something wrong with act engine. Please check the configuration on server end.");
			} else {
				if (!Array.isArray(terms) || !terms.length) {
					alert("No HPO terms found!");
					$('#genePanel').hide();
					$("#parsingResultsPanel").hide();
					$("#outputPanel").hide();
					$("#externalPanel").hide();
				} else {
					$('#genePanel').hide();
					highlight(terms);
					updateTable(terms);

				}

			}

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
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">updating...</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
	});
	
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : "session/addTerms",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var parsingJson = data["hmName2Id"];
			var terms = longestParsingJson(parsingJson);
			terms=terms.filter(Boolean)
			if (terms == 'ERROR') {
				alert("ERROR: Something wrong with act engine. Please check the configuration on server end.");
			} else {
				if (!Array.isArray(terms) || !terms.length) {
					alert("No HPO terms found!");
					$('#genePanel').hide();
					$("#parsingResultsPanel").hide();
					$("#outputPanel").hide();
					$("#externalPanel").hide();
				} else {
					$('#genePanel').hide();
					highlight(terms);
					updateTable(terms);
				}

			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
	$('#searchPopup').removeClass('visible').addClass('hidden');
}

function getTermsInSession() {
	var sessionTerms;
	$.blockUI({
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">updating...</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
	});
	sessionTerms = $.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		url : "session/getTerms",
		dataType : "json",
		async: false, // to avoid null repsonse.
		success : function(data) {
			var parsingJson = data["hmName2Id"];
			var terms = longestParsingJson(parsingJson);
			terms=terms.filter(Boolean)
			if (terms == 'ERROR') {
				alert("ERROR: Something wrong with act engine. Please check the configuration on server end.");
			} else {
				if (!Array.isArray(terms) || !terms.length) {
					alert("No HPO terms found!");
					$('#genePanel').hide();
					$("#parsingResultsPanel").hide();
					$("#outputPanel").hide();
					$("#externalPanel").hide();
				} else {
					$('#genePanel').hide();
					highlight(terms);
					updateTable(terms);
				}

			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	}).responseJSON;
	return sessionTerms;
}
