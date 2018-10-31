function uiSearch() {
	$('.ui.search').search({
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
						title : item.id,
						description : item.name,
					});
				});
				return response;
			},
			url : 'https://hpo.jax.org/api/hpo/search?q={query}'
		}
	});
}