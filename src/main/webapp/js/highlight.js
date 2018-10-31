function subset(obj, propList) {
	return propList.reduce(function(newObj, prop) {
		obj.hasOwnProperty(prop) && (newObj[prop] = obj[prop]);
		return newObj;
	}, {});
}

function highlight(parsingJson) {
	console.log('highlight')
	var note = $("#note").val();
	$("#parsing-results").text(note);
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
		"className" : "termTagger",
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
	console.log(start)
	console.log(length)
	for ( var key in parsingJson) {
		if (parsingJson.hasOwnProperty(key)) {
			// here you have access to
			var start = parsingJson[key].start;
			var length = parsingJson[key].length;
			var hpo_id = parsingJson[key].hpo_id;
			var hpo_term = '(HPO:' + parsingJson[key].hpo_term + ')';

			if (start == range.start && length == range.length) {
				$(node).addClass("ic");
				var hpo_finder = '<span class="hpo">'
						+ hpo_term + '</span>';
				$(node).append(hpo_finder);
				$(node).tooltip({
					
				})
				$(node).click(function() {
					// alert( "Handler for .click() called." );
					var title = $(node).text();
					console.log(title)
					console.log(node)
					$('#termManangeTitle').text(title);
					$('#termManager').modal('show');
				});
			}
		}
	}
}