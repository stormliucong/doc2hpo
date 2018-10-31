function uiSearch() {

//	console.log(tagId);
	$('#hpoSearch').search({
		type : 'standard',
		minCharacters : 3,
		apiSettings : {
			onResponse : function(githubResponse) {
				var response = {
					results : []
				};
				// translate GitHub API response to work with search
				$.each(githubResponse.terms, function(index, item) {
					var maxResults = 20;
					if (index >= maxResults) {
						return false;
					}
					// add result to category
					response.results.push({
						title : item.name,
						description : item.id
					});
				});
				return response;
			},
			url : 'https://hpo.jax.org/api/hpo/search?q={query}'
		},
		onSelect : function(result) {
			$('#selectedResult').val(result.title);
		},
	});
	$('#addHpoTerm').click(function(e) {
		var tagId = $('#searchPopup').find('#HpoNameEntity').text();
		$("#" + tagId).text($('#selectedResult').val());
		// TBD
		// toggle shopping cart
	});
	
	
}

function updateHpoEntity(name) {
	// 
}
