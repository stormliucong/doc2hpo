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

function longestParsingJson(parsingJson){
	// sort by start first.
	parsingJson = parsingJson.sort(function(a, b) {
	    return a.start - b.start;
	});
	// Create an empty stack of intervals 
    var s = []; 
    
    // push the first interval to stack 
    s.push(parsingJson[0]); 

    // loop and compare to the previous one
	for(var i = 1; i <= parsingJson.length - 1; i++){
		// get interval from stack top 
        var top = s[s.length-1];
        // if current interval is not overlapping with stack top, 
        // push it to the stack 
        if ( (top.start + top.length) < parsingJson[i].start) {
            s.push(parsingJson[i]); 
        }
        
        // Otherwise select the longest one.
        else{
        	if(top.length < parsingJson[i].length){
        		s.pop();
        		s.push(parsingJson[i]); 
        	}else{
        		continue;
        	}
        } 
	}
	return s;
}