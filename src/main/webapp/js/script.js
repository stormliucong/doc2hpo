// custermized js.
var keys = {};
window.onkeydown = function(e) {
	keys[e.keyCode] = true;
}
window.onkeyup = function(e) {
	keys[e.keyCode] = false;
}

$(document).ready(onload());

function onload() {
	$(document).ajaxStop($.unblockUI);
	semantiUIInit();
	formControl();
	uiSearch();
	highlightMouseSelected();
	showPhiAgreement()
}

function showPhiAgreement() {
	$('#phiAgreement')
			.modal(
					'setting',
					{
						closable : false,
						onDeny : function() {
							// $('#phiAgreementWarning').modal('show');
							alert('Without accepting this terms & conditions you can\'t use public Doc2Hpo.');
							return false;
						},
					}).modal('show');
}

function semantiUIInit() {
	$('.ui.dropdown').dropdown();
	$('.ui.accordion').accordion();
	$('.ui.form').form({
		fields : {
			note : {
				identifier : 'note',
				rules : [ {
					type : 'empty',
					prompt : 'Please enter your note'
				} ]
			}
		}
	});
	$('.tooltip').popup();
}

function formControl() {
	$('#exampleButton')
			.on(
					"click",
					function() {
						var example = "He denies synophrys. Individual II-1 is a 10 year old boy. He was born at term with normal birth parameters and good APGAR scores (9/10/10). The neonatal period was uneventful, and he had normal motor development during early childhood: he began to look up at 3 months, sit by himself at 5 months, stand up at 11 months, walk at 13 months, and speak at 17 months. He attended a regular kindergarten, without any signs of difference in intelligence, compared to his peers. Starting at age 6, the parents observed ever increasing behavioral disturbance for the boy, manifesting in multiple aspects of life. For example, he can no longer wear clothes by himself, cannot obey instruction from parents/teachers, can no longer hold subjects tightly in hand, which were all things that he could do before 6 years of age. In addition, he no longer liked to play with others; instead, he just preferred to stay by himself, and he sometimes fell down when he walked on the stairs, which had rarely happened at age 5. The proband continued to deteriorate: at age 9, he could not say a single word and had no action or response to any instruction given in clinical exams. Additionally, rough facial features were noted with a flat nasal bridge, a synophrys (unibrow), a long and smooth philtrum, thick lips and an enlarged mouth. He also had rib edge eversion, and it was also discovered that he was profoundly deaf and had completely lost the ability to speak. He also had loss of bladder control. The diagnosis of severe intellectual disability was made, based on Wechsler Intelligence Scale examination. Brain MRI demonstrated cortical atrophy with enlargement of the subarachnoid spaces and ventricular dilatation (Figure 2). Brainstem evoked potentials showed moderate abnormalities. Electroencephalography (EEG) showed abnormal sleep EEG.";
						$("#note").val(formatText(example));
					});

	$("#parsingEngine").on("change", function() {
		if ($("#parsingEngine").dropdown('get value') == 'act') {
			$("#actpOptions").show();
			$("#esmbOptions").hide();
			$("#ncboOptions").hide();
			$("#mmlpOptions").hide();

		}

		if ($("#parsingEngine").dropdown('get value') == 'esmb') {
			$("#actpOptions").hide();
			$("#esmbOptions").show();
			$("#ncboOptions").hide();
			$("#mmlpOptions").hide();

		}

		if ($("#parsingEngine").dropdown('get value') == 'ncbo') {
			$("#actpOptions").hide();
			$("#esmbOptions").hide();
			$("#ncboOptions").show();
			$("#mmlpOptions").hide();
		}

		if ($("#parsingEngine").dropdown('get value') == 'mmlp') {
			$("#actpOptions").hide();
			$("#esmbOptions").hide();
			$("#ncboOptions").hide();
			$("#mmlpOptions").show();
		}

		if ($("#parsingEngine").dropdown('get value') == 'noparsing') {
			$("#actpOptions").hide();
			$("#esmbOptions").hide();
			$("#ncboOptions").hide();
			$("#mmlpOptions").hide();
		}
	});

	$("#parsingButton").on("click", function() {
		$("#parsingResultsPanel").hide();
		$('#genePanel').hide();
		$("#outputPanel").hide();
		$("#externalPanel").hide();
		if ($('#inputForm').form('is valid')) {
			parse();
		}
	});
	
	$("#geneButton").on("click", function() {
		p2g();
	})

	$("#phenolyzerButton")
			.on(
					"click",
					function() {
						copyColumn2();
						alert('you will be directed to Phenolyzer page. You could paste the copied terms to the Disease/Phenotype box!')
						window.open('http://phenolyzer.wglab.org/', '_blank');
					});
	
	$("#PubCaseFinderButton")
	.on(
			"click",
			function() {
				alert('you will be directed to PubCaseFinder page.')
				var table = $('#shoppingCart').DataTable();
				var indexes = table.rows().eq( 0 ).filter( function (rowIdx) {
				    return table.cell( rowIdx, 2 ).data() == "false" ? true : false;
				} );
				var data = table.cells(indexes,1).data();
				var copyString = data.join(",");
				var pubcasefinder_href='https://pubcasefinder.dbcls.jp/search_disease/phenotype:' +
				copyString
				+'/gene:/page:1,1/size:10,10,omim'
				window.open(pubcasefinder_href, '_blank');
			});
	
	$('#ExomizerButton')
	.on(
			"click",
			function() {
				alert('you will be directed to Exomizer page. You could paste the copied terms to the Clinical phenotypes box!')
				copyColumn();
				window.open('https://monarch-exomiser-web-dev.monarchinitiative.org/exomiser/submit?', '_blank');
			});
	
	$('#Phevor2Button')
	.on(
			"click",
			function() {
				alert('you will be directed to Phevor2 page. You could paste the copied terms to the Ontology Terms box!')
				copyColumn();
				window.open('http://weatherby.genetics.utah.edu/phevor2/index.html', '_blank');
			});
	
	

	$("#copyColumnButton").on("click", function() {
		copyColumn();
	});
	$("#copyColumnButton2").on("click", function() {
		copyColumn2();
	});

	$("#jsonDownloadButton").on(
			"click",
			function() {
				var res = getTermsInSession();
				var json = res["hmName2Id"];
				$(
						"<a />",
						{
							"download" : "data.json",
							"href" : "data:application/json,"
									+ encodeURIComponent(JSON.stringify(json))
						}).appendTo("body").click(function() {
					$(this).remove()
				})[0].click()
			});
}
