function copyColumn(columnIndex) {
	console.log("clicked");
	var nameTerm = [];
	$('tbody tr').each(
			function() {
				nameTerm.push($(this).find('td:nth-child(' + columnIndex + ')')
						.text());
			})
	var copyString = nameTerm.join(";");
	console.log(copyString);
	copyToClipboard(copyString);
}

function copyToClipboard(text) {
	var dummy = document.createElement("input");
	document.body.appendChild(dummy);
	dummy.setAttribute('value', text);
	dummy.select();
	document.execCommand("copy");
	document.body.removeChild(dummy);
}

function refreshTable() {
	$('.table-add').unbind("click").click(
			function() {
				var $clone = $('#termTable').find('tr.hide').clone(true)
						.removeClass('hide table-line');
				$('#termTable').find('tbody').append($clone);
			});
	$('#termTable tbody').html('');
	$('.table-remove').unbind("click").click(function() {
		var basePath = $('input[id=basePath]').val();
		console.log("remove elements");
		$(this).parents('tr').remove();
	});
	$('#copyName').unbind("click").click(function() {
		copyColumn(1);
	});
	$('#copyId').unbind("click").click(function() {
		copyColumn(2);
	});
}
function updateTable(terms) {
	var basePath = $('input[id=basePath]').val();
	var $TABLE = $('#termTable');
	var pre = '<tr>'
	var pos = '</tr>'
	var cross = '<td><span class="table-remove glyphicon glyphicon-remove"></span></td>';
	Object
			.keys(terms)
			.forEach(
					function(key) {
						var url_base = "#";
						var re = /^HP/i;

						var name = key;
						var id = terms[key];
						var isHpo = re.test(id);
						if (isHpo == true) {
							url_base = 'http://compbio.charite.de/hpoweb/showterm?id=';
						} else {
							url_base = 'http://denote.rnet.ryerson.ca/umlsMap/pairs/indexframe.php?CUI=';
						}
						var idHref = url_base + id;
						var nameTd = '<td>' + name + '</td>';
						var idTd = '<td><a href="' + idHref
								+ '" target="_blank">' + id + '</a></td>';
						var clone = pre + nameTd + idTd + cross + pos;
						$TABLE.find('table').append(clone);
					});
	// have to define table-remove again inside the function.
	$('.table-remove').unbind("click").click(function() {
		var basePath = $('input[id=basePath]').val();
		console.log("remove elements");
		$(this).parents('tr').remove();
	});
}

function deleteTerms() {
	
}