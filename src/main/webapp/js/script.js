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
			$("#ncboOptions").hide();
			$("#mmpOptions").hide();
			$("#mmlpOptions").hide();

		}

		if ($("#parsingEngine").dropdown('get value') == 'mmp') {
			$("#actpOptions").hide();
			$("#ncboOptions").hide();
			$("#mmpOptions").show();
			$("#mmlpOptions").hide();

		}

		if ($("#parsingEngine").dropdown('get value') == 'ncbo') {
			$("#actpOptions").hide();
			$("#ncboOptions").show();
			$("#mmpOptions").hide();
			$("#mmlpOptions").hide();
		}
		
		if ($("#parsingEngine").dropdown('get value') == 'mmlp') {
			$("#actpOptions").hide();
			$("#ncboOptions").hide();
			$("#mmpOptions").hide();
			$("#mmlpOptions").show();
		}
	});


	$("#parsingButton").on("click", function() {
		if ($('#inputForm').form('is valid')) {
			parse();
		}
	});

	$("#phenolyzerButton")
			.on(
					"click",
					function() {
						alert('you will be directed to Phenolyzer page. You could paste the copied terms to the Disease/Phenotype box!')
						window.open('http://phenolyzer.wglab.org/', '_blank');
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
