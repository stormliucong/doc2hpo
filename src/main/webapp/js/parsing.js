function parse() {
	var note = $("#note").val();
	note = formatText(note);
	var value = "";
	// default engine is act.
	if ($("#parsingEngine").dropdown('get value') == "") {
		value = "act";
	} else {
		value = $("#parsingEngine").dropdown('get value');
	}

	if (value == "act") {
		parseACT(note);
	}
	if (value == "mmp") {
		parseMetamap(note);
	}
	if (value == "ncbo") {
		parseNcbo(note);
	}
	if (value == "mmlp") {
		parseMetamaplite(note);
	}
	if (value == "esmb") {
		parseEnsemble(note);
	}
}

function parseMetamap(note) {
	var mmp_negex = $('#mmp_negex').is(':checked');
	var all_acros_abbrs = $('#all_acros_abbrs').is(':checked');
	var allow_concept_gaps = $('#allow_concept_gaps').is(':checked');
	var ignore_word_order = $('#ignore_word_order').is(':checked');
	var ignore_stop_phrases = $('#ignore_stop_phrases').is(':checked');
	var formData = {
		'note' : note,
		'negex' : mmp_negex,
		'mmpgeneral' : {
			'aaa' : all_acros_abbrs,
			'acg' : allow_concept_gaps,
			'iwo' : ignore_word_order,
			"isp" : ignore_stop_phrases,
		},
	};
	$
			.blockUI({
				message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">System is processing...It may take up to few minutes.</div><p></p><p></p><p></p><p></p></div></div>',
				css : {
					border : 'none',
					'-webkit-border-radius' : '40px',
					'-moz-border-radius' : '40px',
					opacity : .5,
				},
			});
	$
			.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				url : "/doc2hpo/parse/metamap",
				data : JSON.stringify(formData),
				dataType : "json",
				success : function(data) {
					var terms = data["hmName2Id"];
					terms=terms.filter(Boolean)
					if (terms == 'ERROR') {
						alert("ERROR: Something wrong with act engine. Please check the configuration on server end.");
					} else {
						if (!Array.isArray(terms) || !terms.length) {
							alert("No HPO terms found!");
						} else {
							highlight(terms);
							updateTable(terms);
							var t = $(window).scrollTop();
							$('body,html').animate({
								'scrollTop' : t + 1000
							}, 200)
							$("#phenolyzer").show();

						}

					}

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(url);
				}
			});
}

function parseACT(note) {
	var actp_negex = $('#actp_negex').is(':checked');
	var actp_allow_partial = $('#actp_allow_partial').is(':checked');

	var formData = {
		'note' : note,
		'allowPartial' : actp_allow_partial,
		'negex' : actp_negex
	};
	$
			.blockUI({
				message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">System is processing...It may take up to few minutes.</div><p></p><p></p><p></p><p></p></div></div>',
				css : {
					border : 'none',
					'-webkit-border-radius' : '40px',
					'-moz-border-radius' : '40px',
					opacity : .5,
				},
			});
	$
			.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				url : "/doc2hpo/parse/acdat",
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
						} else {
							highlight(terms);
							updateTable(terms);
							var t = $(window).scrollTop();
							$('body,html').animate({
								'scrollTop' : t + 1000
							}, 200)
							$("#phenolyzer").show();

						}

					}

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(url);
				}
			});
}

function parseMetamaplite(note) {
	var mmlp_negex = $('#mmlp_negex').is(':checked');
	var formData = {
		'note' : note,
		'negex' : mmlp_negex
	};
	$
			.blockUI({
				message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">System is processing...It may take up to few minutes.</div><p></p><p></p><p></p><p></p></div></div>',
				css : {
					border : 'none',
					'-webkit-border-radius' : '40px',
					'-moz-border-radius' : '40px',
					opacity : .5,
				},
			});
	$
			.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				url : "/doc2hpo/parse/metamaplite",
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
						} else {
							highlight(terms);
							updateTable(terms);
							var t = $(window).scrollTop();
							$('body,html').animate({
								'scrollTop' : t + 1000
							}, 200)
							$("#phenolyzer").show();

						}

					}

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(url);
				}
			});
}

function parseEnsemble(note) {
	var esmb_negex = $('#esmb_negex').is(':checked');
	var formData = {
		'note' : note,
		'negex' : esmb_negex
	};
	$
			.blockUI({
				message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">System is processing...It may take up to few minutes.</div><p></p><p></p><p></p><p></p></div></div>',
				css : {
					border : 'none',
					'-webkit-border-radius' : '40px',
					'-moz-border-radius' : '40px',
					opacity : .5,
				},
			});
	$
			.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				url : "/doc2hpo/parse/ensemble",
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
						} else {
							highlight(terms);
							updateTable(terms);
							var t = $(window).scrollTop();
							$('body,html').animate({
								'scrollTop' : t + 1000
							}, 200)
							$("#phenolyzer").show();

						}

					}

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(url);
				}
			});
}

function parseNcbo(note) {
	var longest_only = $('#longest_only').is(':checked');
	var whole_word_only = $('#whole_word_only').is(':checked');
	var exclude_numbers = $('#exclude_numbers').is(':checked');
	var ncbo_negex = $('#ncbo_negex').is(':checked');
	var formData = {
		'note' : note,
		'negex' : ncbo_negex,
		'ncbogeneral' : {
			'lo' : longest_only,
			'wwo' : whole_word_only,
			'en' : exclude_numbers
		}
	};
	$
			.blockUI({
				message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">System is processing...It may take up to few minutes.</div><p></p><p></p><p></p><p></p></div></div>',
				css : {
					border : 'none',
					'-webkit-border-radius' : '40px',
					'-moz-border-radius' : '40px',
					opacity : .5,
				},
			});
	$
			.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				url : "/doc2hpo/parse/ncbo",
				data : JSON.stringify(formData),
				dataType : "json",
				success : function(data) {
					var terms = data["hmName2Id"];
					terms=terms.filter(Boolean)
					if (terms == 'ERROR') {
						alert("ERROR: Something wrong with act engine. Please check the configuration on server end.");
					} else {
						if (!Array.isArray(terms) || !terms.length) {
							alert("No HPO terms found!");
						} else {
							highlight(terms);
							updateTable(terms);
							var t = $(window).scrollTop();
							$('body,html').animate({
								'scrollTop' : t + 1000
							}, 200)
							$("#phenolyzer").show();

						}

					}

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(url);
				}
			});
}

function testController() {
	var search = {
		pName : "bhanu",
		lName : "prasad"
	}
	$.ajax({
		type : "POST",
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},

		dataType : 'json',
		url : "/doc2hpo/test/test1",
		data : JSON.stringify(search), // Note it is important
		success : function(result) {
			// do what ever you want with data
		}
	})
}
