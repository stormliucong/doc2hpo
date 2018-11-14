function removeNonAsc(rawString) {
	return rawString.replace(/[^\x00-\x7F]/g, "");
}

function copyToClipboard(text) {
	var dummy = document.createElement("input");
	document.body.appendChild(dummy);
	dummy.setAttribute('value', text);
	dummy.select();
	document.execCommand("copy");
	document.body.removeChild(dummy);
}