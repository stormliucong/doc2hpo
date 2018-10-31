function deleteTermsInSession(start, length){
	var formData = {
		'start' : start,
		'length' : length
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

function addTermsInSession(start,length){
	
}