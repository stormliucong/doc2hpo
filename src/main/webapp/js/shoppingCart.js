function updateTable(parsingJson) {
	responseObj = termCounting(parsingJson);
	var table = $('#shoppingCart')
			.DataTable(
					{
						"data" : responseObj,
						"columns" : [

								{
									"data" : "hponame"
								},
								{
									"data" : "hpoid",
									"render" : function(data, type, row, meta) {
										if (type === 'display') {
											var url_base = 'https://hpo.jax.org/app/browse/term/';
											data = '<a href="' + url_base
													+ data
													+ '" target="_blank">'
													+ data + '</a>';
										}

										return data;
									}
								}, {
									"data" : "is_negated"
								}, {
									"data" : "count"
								},

						],
						responsive : true,
						lengthChange : true,
						buttons : [ 'excel', 'pdf' ],
						searching : false,
						paging : false,
						info : false,
						"bDestroy" : true
					});

	table.buttons().container().appendTo($("#buttonGroups"));

	$('#shoppingCart tbody').on(
			'click',
			'tr',
			function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
				} else {
					$("#shoppingCart").DataTable().$('tr.selected')
							.removeClass('selected');
					$(this).addClass('selected');
				}
			});

	$('.buttons-excel').attr('data-tooltip',
			'Download above table as an excel file');
	$('.buttons-pdf')
			.attr('data-tooltip', 'Download above table as a pdf file');
	$('#shoppingCart').removeAttr('style');

}

function copyColumn() {
	var table = $('#shoppingCart').DataTable();
	var indexes = table.rows().eq( 0 ).filter( function (rowIdx) {
	    return table.cell( rowIdx, 2 ).data() == "false" ? true : false;
	} );
	var data = table.cells(indexes,0).data();
	var copyString = data.join(";");
	console.log(copyString);
	copyToClipboard(copyString);
}

function copyColumn2() {
	var table = $('#shoppingCart').DataTable();
	var indexes = table.rows().eq( 0 ).filter( function (rowIdx) {
	    return table.cell( rowIdx, 2 ).data() == "false" ? true : false;
	} );
	var data = table.cells(indexes,1).data();
	var copyString = data.join(",");
	console.log(copyString);
	copyToClipboard(copyString);
}

function addToTable() {
	// TBD
}

function deleteRow() {
	// TBD
}

function termCounting(parsingJson) {
	var p_countTerms = {}
	var n_countTerms = {}

	for (key in parsingJson) {
		if (parsingJson.hasOwnProperty(key)) {
			var val = parsingJson[key];
			var hpoName = val['hpoName'];
			var is_negated = val['negated']

			if (hpoName != "click to search hpos...") {
				var hpoId = val['hpoId'];
				if (is_negated == false) {
					if (!(hpoId in p_countTerms)) {
						var countObj = {
							'hpoName' : hpoName,
							'count' : 1
						}
						p_countTerms[hpoId] = countObj
					} else {
						p_countTerms[hpoId]['count'] += 1;
					}
				} else {
					if (!(hpoId in n_countTerms)) {
						var countObj = {
							'hpoName' : hpoName,
							'count' : 1
						}
						n_countTerms[hpoId] = countObj
					} else {
						n_countTerms[hpoId]['count'] += 1;
					}
				}
			}
		}
	}
	var result = [];

	for ( var hpoId in p_countTerms) {
		result.push({
			"hpoid" : hpoId,
			"hponame" : p_countTerms[hpoId]['hpoName'],
			'is_negated' : 'false',
			"count" : p_countTerms[hpoId]['count']
		});
	}
	for ( var hpoId in n_countTerms) {
		result.push({
			"hpoid" : hpoId,
			"hponame" : n_countTerms[hpoId]['hpoName'],
			'is_negated' : 'true',
			"count" : n_countTerms[hpoId]['count']
		});
	}
	return result;
}