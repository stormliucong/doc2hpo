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
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">updating...</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
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
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">updating...</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
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
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">updating...</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
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
	var t = $(window).scrollTop();
	sessionTerms = $.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'GET',
		url : "/doc2hpo/session/getTerms",
		dataType : "json",
		async: false, // to avoid null repsonse.
		success : function(data) {
			terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			}
			return terms
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	}).responseJSON;
	return sessionTerms;
}
