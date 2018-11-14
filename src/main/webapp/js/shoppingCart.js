function updateTable(parsingJson) {
	responseObj = termCounting(parsingJson);
	console.log(responseObj);
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
											var url_base = 'http://compbio.charite.de/hpoweb/showterm?id=';
											data = '<a href="' + url_base
													+ data
													+ '" target="_blank">'
													+ data + '</a>';
										}

										return data;
									}
								}, {
									"data" : "count"
								},

						],
						lengthChange: false,
				        buttons: ['excel', 'pdf'],
						searching : false,
						paging : false,
						info : false,
						"bDestroy" : true
					});
	
	 table.buttons().container()
     .appendTo($("#buttonGroups"));
	 
	$('#shoppingCart tbody').on('click', 'tr', function() {
		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');
		} else {
			$("#shoppingCart").DataTable().$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
	});

}

function copyColumn(){
	var table = $('#shoppingCart').DataTable();
	var data = table.column( 0 ).data();
	var copyString = data.join(";");
	console.log(copyString);
	copyToClipboard(copyString);
}


function addToTable() {
//TBD
}

function deleteRow() {
//TBD
}

function termCounting(listOfTerms) {
	var countTerms = {}
	for (key in listOfTerms) {
		if (listOfTerms.hasOwnProperty(key)) {
			var val = listOfTerms[key];
			var hpoName = val['hpoName'];
			if(hpoName != "add"){
				var hpoId = val['hpoId'];
				if (!(hpoId in countTerms)) {
					var countObj = {
						'hpoName' : hpoName,
						'count' : 1
					}
					countTerms[hpoId] = countObj
				} else {
					countTerms[hpoId]['count'] += 1;
				}
			}
		}
	}
	var result = [];

	for ( var hpoId in countTerms) {
		result.push({
			"hpoid" : hpoId,
			"hponame" : countTerms[hpoId]['hpoName'],
			"count" : countTerms[hpoId]['count']
		});
	}
	console.log(result);
	return result;
}