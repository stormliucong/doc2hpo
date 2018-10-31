function highlight(parsingJson) {
	console.log('highlight')
	var note = $("#note").val();
	$("#parsing-results").text(note);
	$("#parsing-results").addClass('entities');
	var context = document.querySelector("#parsing-results");

	// using https://markjs.io/
	console.log(parsingJson);
	rangeArray = []
	for ( var key in parsingJson) {
		subObj = _.pick(parsingJson[key], [ 'start', 'length' ]);
		rangeArray.push(subObj);
	}
	console.log(rangeArray);
	var options = {
		"element" : "mark",
		"className" : "",
		"exclude" : [],
		"iframes" : true,
		"iframesTimeout" : 5000,
		"each" : function(node, range) {
			// node is the marked DOM element
			// range is the corresponding range
			processEachTag(node, range, parsingJson);
		},
		"filter" : function(textNode, range, term, counter) {
			// textNode is the text node which contains the found term
			// range is the found range
			// term is the extracted term from the matching range
			// counter is a counter indicating the number of marks for the found
			// term
			return true; // must return either true or false
		},
		"noMatch" : function(range) {
			// the not found range
		},
		"done" : function(counter) {
			// counter is a counter indicating the total number of all marks
		},
		"debug" : false,
		"log" : window.console
	};
	var instance = new Mark(context);
	instance.markRanges(rangeArray, options);
	// instance.mark('Individual');
	$("#conceptMappingResults").show();

}

function processEachTag(node, range, parsingJson) {
	var start = range.start
	var length = range.length

	for ( var key in parsingJson) {
		if (parsingJson.hasOwnProperty(key)) {
			// here you have access to
			var start = parsingJson[key].start;
			var length = parsingJson[key].length;
			var hpo_id = parsingJson[key].hpo_id;
			var hpo_term = parsingJson[key].hpo_term.toUpperCase();

			if (start == range.start && length == range.length) {
				console.log('start' + start)
				console.log('len' + length)
				var tagId = "id_" + start + "_" + length; 
				$(node).addClass('data-entity')
				$(node).append(
						"<span class='hpo-entity ' id='" + tagId + "'>" + hpo_term + "</span>")

				console.log(note);
				$(node).on('click', function() {
					// alert( "Handler for .click() called." );
					$(this).toggleClass("data-entity");
					$(this).find('.hpo-entity').toggle();
					// TBD
					// toggle shopping cart
					
				});
				
				$(node)
						.find('span.hpo-entity')
						.on(
								'click',
								function(e) {
									console.log("this attrid " + $(this).attr('id'));
									$('#HpoNameEntity').text($(this).attr('id'));
									var hpo_link = 'https://hpo.jax.org/app/browse/term/'
											+ hpo_id;
									var hpo_html = '<a href="' + hpo_link
											+ '" target="_blank">' + hpo_id
											+ ' ' + hpo_term + '</a>';
									$('#termManager').find('.header').html(
											hpo_html);
									$(this).popup({
										popup : $('#searchPopup'),
										on : 'click',
									}).popup('show');
									e.stopPropagation(); // do nothing.
								});
			}
		}
	}
}