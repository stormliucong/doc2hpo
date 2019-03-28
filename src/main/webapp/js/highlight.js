function highlightMouseSelected() {
	// var key = {};
	// windows.ke
	// $("#parsingResults").mousedown(function (e) {
	// if (e.ctrlKey || e.shiftKey) {
	// // For non-IE browsers
	// e.preventDefault();
	//
	// // For IE
	// if ($.browser.msie) {
	// this.onselectstart = function () { return false; };
	// var me = this; // capture in a closure
	// window.setTimeout(function () { me.onselectstart = null; }, 0);
	// }
	// }
	// });

	$("#parsingResults").bind(
			'mouseup',
			function(e) {
				if (keys[81]) {
					// Press Q
					if (typeof window.getSelection != "undefined") {
						sel = window.getSelection();
						text = sel + '';
						// if (text.length < 5) {
						// return;
						// }
					} else if (typeof document.selection != "undefined"
							&& (sel = document.selection).type != "Control") {
						text = sel + '';
						if (text.length < 5) {
							return;
						}
					}
					var start = 0;
					var end = 0;
					var sel, range, priorRange, text;
					var context = document.querySelector("#parsingResults");
					var instance = new Mark(context);
					$('.hpo-entity').remove();
					$('.negation-button').remove();

					if (typeof window.getSelection != "undefined") {
						sel = window.getSelection();
						text = sel + '';
						range = window.getSelection().getRangeAt(0);
						ref = document.getElementById("parsingResults")
						priorRange = range.cloneRange();
						priorRange.selectNodeContents(context);
						priorRange.setEnd(range.startContainer,
								range.startOffset);
						start = priorRange.toString().length;
						end = start + (sel + '').length - 1;

					} else if (typeof document.selection != "undefined"
							&& (sel = document.selection).type != "Control") {
						text = sel + '';
						range = sel.createRange();
						priorRange = document.body.createTextRange();
						priorRange.moveToElementText(context);
						priorRange.setEndPoint("EndToStart", range);
						start = priorRange.text.length;
						end = start + (sel + '').length - 1;

					}
					length = end - start + 1;
					addTermsInSessionWithHighlight(start, length,
							'click to search HPOs...',
							'click to search HPOs...');
				}

			});

}

function highlight(parsingJson) {
	var note = $("#note").val();
	note = formatText(note);
	$("#parsingResults").text(note);
	$("#parsingResults").addClass('entities');
	var context = document.querySelector("#parsingResults");

	// using https://markjs.io/
	rangeArray = []
	for ( var key in parsingJson) {
		subObj = _.pick(parsingJson[key], [ 'start', 'length' ]);
		rangeArray.push(subObj);
	}
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
	$("#parsingResultsPanel").show();
	$("#outputPanel").show();

}

function processEachTag(node, range, parsingJson) {
	var start = range.start
	var length = range.length

	for ( var key in parsingJson) {
		if (parsingJson.hasOwnProperty(key)) {
			// here you have access to
			var start = parsingJson[key].start;
			var length = parsingJson[key].length;
			var hpo_id = parsingJson[key].hpoId;
			var hpo_term = parsingJson[key].hpoName.toUpperCase();
			var is_negated = parsingJson[key].negated;

			if (start == range.start && length == range.length) {
				var tagId = start + "_" + length;
				$(node).addClass('data-entity')
				if (is_negated) {
					$(node)
							.prepend(
									"<button class='ui toggle button mini negative negation-button' style='font-size: 0.45rem;'>N</button>");
				} else {
					$(node)
							.prepend(
									"<button class='ui toggle button mini positive negation-button' style='font-size: 0.45rem;'>N</button>");
				}

				$(node).append(
						"<span class='hpo-entity' id='" + tagId + "'>"
								+ hpo_term + "</span>")
				$(node).find('.hpo-entity').attr('hpo_term', hpo_term);
				$(node).find('.hpo-entity').attr('hpo_id', hpo_id);

				// delete annotation.
				$(node).on(
						'dblclick',
						function(e) {

							// alert( "Handler for .click() called." );
							$(this).toggleClass("data-entity");
							$(this).find('.hpo-entity').toggle();
							var tagIdArray = $(node).find('.hpo-entity').attr(
									'id').split("_");
							var start = tagIdArray[0];
							var length = tagIdArray[1];
							var hpo_id = $(node).find('.hpo-entity').attr(
									'hpo_id');
							var hpo_term = $(node).find('.hpo-entity').attr(
									'hpo_term');
							if ($(this).find('.hpo-entity').is(":visible")) {
								addTermsInSession(start, length, hpo_id,
										hpo_term);
							} else {
								deleteTermsInSession(start, length, hpo_id,
										hpo_term);

							}

						});
				// search box function.
				$(node).find('span.hpo-entity').on(
						'click',
						function(e) {
							$('#HpoNameEntity').text($(this).attr('id'));
							// detach events.
							$('#selectedResult').val(
									'NORESULTS' + ';;;' + 'NORESULTS');
							$(this).popup({
								popup : $('#searchPopup'),
								on : 'click',
								closable : false
							}).popup('show');
							e.stopPropagation(); // do nothing.

							// attach again.
						});

				// change negation status.
				$(node).find('button.ui').on(
						'click',
						function(e) {
							var tagIdArray = $(node).find('.hpo-entity').attr(
									'id').split("_");
							var start = tagIdArray[0];
							var length = tagIdArray[1];
							var hpo_id = $(node).find('.hpo-entity').attr(
									'hpo_id');
							var hpo_term = $(node).find('.hpo-entity').attr(
									'hpo_term');
							if ($(this).hasClass('negative')) {
								is_negated = false; // change the negation status.
								updateTermsInSession(start, length, hpo_id,
										hpo_term, is_negated);

							} else {
								is_negated = true; // change the negation status.
								updateTermsInSession(start, length, hpo_id,
										hpo_term, is_negated);
							}

						});
			}
		}
	}
}