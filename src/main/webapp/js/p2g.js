function p2g() {
	// show gene panel.
	$("#genePanel").show();

	// shoppingCart
	var table = $('#shoppingCart').DataTable();
	var indexes = table.rows().eq(0).filter(function(rowIdx) {
		return table.cell(rowIdx, 2).data() == "false" ? true : false;
	});
	var data = table.cells(indexes, 1).data();
	var copyString = data.join(";");
	$
			.blockUI({
				message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">System is processing...It may take up to 5 minutes. Restart if no response returned after 5 minutes. You may want to cut the size of the input in that case.</div><p></p><p></p><p></p><p></p></div></div>',
				css : {
					border : 'none',
					'-webkit-border-radius' : '40px',
					'-moz-border-radius' : '40px',
					opacity : .5,
				},
			});
	$
			.ajax({
				// headers : {
				// 	'Accept' : 'application/json',
				// 	'Content-Type' : 'application/json'
				// }, 
				// Add hearders will cause CORS issue.
				type : 'GET',
				url : "https://phen2gene.wglab.org/api",
				data : {'HPO_list': copyString},
				dataType : "json",
				success : function(data) {
					tableData = data['results']
					$('#geneCart').DataTable( {
				        data: tableData,
//				        dom: 'Bfrtip',
//				        buttons: [
//				            'copy', 'csv', 'excel', 'pdf', 'print'
//				        ],
				        "columns": [
				            { "data": "Gene" },
				            { "data": "Rank" },
				            { "data": "Score" },
				            { "data": "Status" }
				        ],
				        "bDestroy": true
				    } );
				}
					
			});

}