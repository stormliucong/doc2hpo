function uiSearch() {

	$('#hpoSearch').search(
			{
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
					$('#selectedResult').val(
							result.title + ';;;' + result.description);
				},
			});

	$('#addHpoTerm').click(function(e) {
		var tagId = $('#searchPopup').find('#HpoNameEntity').text();
		var tagIdArray = tagId.split('_');
		var start = tagIdArray[0];
		var length = tagIdArray[1];
		var hpoArray = $('#selectedResult').val().split(';;;');
		var hpo_term = hpoArray[0];
		var hpoIdArray = hpoArray[1].split(':');
		var hpo_id = hpoIdArray[0] + "_" + hpoIdArray[1];
		$("#" + tagId).text(hpo_term);
		$("#" + tagId).find('.hpo-entity').attr('hpo_term', hpo_term);
		$("#" + tagId).find('.hpo-entity').attr('hpo_id', hpo_id);
		updateTermsInSession(start, length, hpo_id, hpo_term);

	});
}
