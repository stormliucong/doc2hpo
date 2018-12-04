function removeNonAsc(s) {
	s = s.replace(/[^\x00-\x7F]/g, "");
	return s;
}

function normalizeSpace(s){
	s = s.replace(/ +(?= )/g,'');
	s = s.replace(/\n\s*\n/g, '\n');
	return s;
}

function copyToClipboard(text) {
	var dummy = document.createElement("input");
	document.body.appendChild(dummy);
	dummy.setAttribute('value', text);
	dummy.select();
	document.execCommand("copy");
	document.body.removeChild(dummy);
}

function formatText(s){
	s = removeNonAsc(s);
	s = normalizeSpace(s);
	return s;
}