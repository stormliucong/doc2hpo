function uiSearch() {

	$('#hpoSearch').search(
			{
				type : 'standard',
				minCharacters : 3,
				apiSettings : {
					onResponse : function(res) {
						var response = {
							results : []
						};
						var url_base = 'https://hpo.jax.org/app/browse/term/';
						// translate GitHub API response to work with search
						$.each(res.terms, function(index, item) {
							var maxResults = 20;
							if (index >= maxResults) {
								return false;
							}
							// * result to category
							var href = url_base + item.id;
							response.results.push({
								title : item.name + '<div class="ui right floated basic icon button" onClick="window.open(\'' + href + '\', \'_blank\', \'location=yes,height=600,width=600,scrollbars=yes,status=yes\')" ><i class="zoom icon"></i></div>',
								description : item.id 
								
						
//								url : 'https://hpo.jax.org/app/browse/term/' + item.id
							});
						});

						return response;
					},
					url : 'https://hpo.jax.org/api/hpo/search?q={query}'
				},
				fields: {
			        results: 'results',
			        title: 'title',
			        actionURL: "url"
			    },
				onSelect : function(result) {
					result.title = result.title.replace(/^(.+?)<.+?>$/,'$1')
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
		if(hpoArray[0] === 'NORESULTS'){
			// won't change the annotation if no results returned.
			$('#searchPopup').removeClass('visible').addClass('hidden');
		}else{
			var hpo_term = hpoArray[0];
			var hpoIdArray = hpoArray[1].split(':');
			var hpo_id = hpoIdArray[0] + "_" + hpoIdArray[1];
			$("#" + tagId).text(hpo_term);
			$("#" + tagId).find('.hpo-entity').attr('hpo_term', hpo_term);
			$("#" + tagId).find('.hpo-entity').attr('hpo_id', hpo_id);
			updateTermsInSession(start, length, hpo_id, hpo_term);
		}
	});
	$('#closeAddHpoTerm').click(function(e) {
		$('#searchPopup').removeClass('visible').addClass('hidden');
	});
}
