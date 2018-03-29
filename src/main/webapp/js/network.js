$(window)
		.load(
				function() {
//					$("#dialog").dialog("close");
//					$('.weaker').fadeTo(1000, 1);
					$('.selectpicker').selectpicker({
						width : '100%',
					});

//					var interaction = $("tr.related_interaction");
//					var detail = $("tr.related_disease");
//					var detail_check = $("h3#show_detail");
//					var prediction_check = $("h3#show_prediction");
					var result_panel = $("#accordion");

					var disease_on = $('#disease_on');
					var gene_on = $('#gene_on');
					var disease_names_on = $("#show_disease_names");
					var gene_names_on = $("#show_gene_names");
					var save_photo = $('#save_photo');
					disease_on.bootstrapSwitch();
					gene_on.bootstrapSwitch();
					disease_names_on.bootstrapSwitch();
					gene_names_on.bootstrapSwitch();
//					interaction.hide();
//					detail.hide();

//					var change_text = $("h3#barplot a");
					//  change_text.css('color','#7777fa')

					//accordion
//					result_panel.accordion({
//						header : "h3.gene_score",
//						heightStyle : "content",
//						collapsible : true,
//						active : null
//					});

					//The gene-disease network

					var if_json_success;
					var work_dir = $('#workdir').text();
//					console.log(work_dir);
//					var json_url = document.URL;
//					var id = json_url.match(/#(\d+)/);
//					if (id && $(id[0]))
//						$(id[0]).click();
//
//					json_url = json_url.replace(/(index\.html|index\.php).*/,
//							"");
					var json_url = work_dir + "/network.json";
					console.log(json_url);
					var json;
					$.ajaxSetup({
						'async' : false
					});
					$
							.getJSON(
									json_url,
									function(data) {
										json = data;
										json = json.map(function(each) {
											each.data.weight = Math
													.sqrt(each.data.weight);
											return each;
										});
										if_json_success = true;
										return json;
									})
							.fail(
									function(jqxhr, textStatus, error) {
										alert("Sorry, the fancy network plot is not available now. Please try to refresh");
										if_json_success = false;
									});
					if (!json) {
						$("a[href=#summary]").click();
					}

					if (if_json_success) {
						$('#cy')
								.cytoscape(
										{
											style : cytoscape
													.stylesheet()
													.selector('node')
													.css(
															{
																'content' : 'data(id)',
																'text-valign' : 'center',
																'font-size' : 'mapData(weight, 0, 1, 18px, 22px)',
																'color' : 'white',
																'text-outline-width' : 'mapData(weight, 0, 0.5, 4px, 3px)',

															})
													.selector('edge')
													.css(
															{
																'target-arrow-shape' : 'none',
																'line-color' : '#444401',
																'width' : '0.5px',
															//'curve-style':'bezier'
															})
													.selector('edge.BIOSYSTEM')
													.css(
															{
																'line-color' : "#baba11"

															})
													.selector('edge.HPRD')
													.css(
															{
																'line-color' : "mapData(weight,0.2,0.8, #5555ff, #4444dd)"
															})
													.selector(
															'edge.GENE_FAMILY')
													.css(
															{
																'line-color' : '#00ef33'
															})
													.selector('edge.HTRI')
													.css(
															{
																'line-color' : '#020202',
																'target-arrow-shape' : 'triangle',
																'target-arrow-color' : '#1f1f1f'
															})
													.selector(
															'edge.disease_term')
													.css(
															{
																'line-color' : "#ef55cc"
															})
													.selector(
															'edge.disease_gene')
													.css(
															{
																'line-color' : "#ef55cc"
															})
													.selector(':selected')
													.css(
															{
																'background-color' : 'red',
																'line-color' : 'red',
																'target-arrow-color' : 'red',
																'source-arrow-color' : 'red'
															})
													.selector('node.Reported')
													.css(
															{
																'background-color' : 'mapData(color_weight,0,1,#0055ff,#003366)',
																'text-outline-color' : 'mapData(color_weight,0,1,#0077ff,#007766)',
																'border-width' : '0px',
																'width' : 'mapData(weight, 0,1, 16, 85)',
																'height' : 'mapData(weight, 0,1, 16, 85)',

															})
													.selector('node.Predicted')
													.css(
															{
																'background-color' : 'mapData(color_weight,0,1, #bbbb11, #777722)',
																'text-outline-color' : 'mapData(color_weight, 0, 1, #bbbb11, #777722)',
																'border-width' : '0px',
																'width' : 'mapData(weight, 0,1, 16, 85)',
																'height' : 'mapData(weight, 0,1, 16, 85)',

															})
													.selector('node.disease')
													.css(
															{
																'text-outline-width' : '2px',
																'text-outline-color' : 'mapData(weight,0,1,#e033aa,#ea0066)',
																'content' : '',
																'shape' : 'roundrectangle',
																'background-color' : 'mapData(weight,0,1,#e033aa,#ea0066)',
																'width' : 'mapData(weight, 0, 1, 18, 90)',
																'height' : '20',
																'font-size' : '10px'
															})
													.selector('node.term')
													.css(
															{
																'text-outline-width' : '3px',
																'text-outline-color' : '#e02222',
																'background-color' : '#e02222',
																'font-size' : '30px',
																'shape' : 'circle',
																'width' : '20px',
																'height' : '20px'

															})
													.selector('.highlighted')
													.css(
															{
																'background-color' : '#ff0303',
																'line-color' : '#ff0303',
																'text-outline-color' : '#ff0303',
																'target-arrow-color' : '#ff0303',
																'source-arrow-color' : '#ff0303',

															}).selector(
															'.faded').css({
														'opacity' : '0'

													}),
											userZoomingEnabled : true,
											panningEnabled : true,
											userPanningEnabled : true,
											layout : {
												name : 'arbor',
												stiffness : 0,
											// maxSimulationTime:500 

											},

											zoom : 1,
											minZoom : 1e-50,
											maxZoom : 1e50,
											elements : [],

											ready : function() {
												window.cy = this;

												// giddy up...

												cy.elements().unselectify();

												cy
														.on(
																'tap',
																'node',
																function(e) {
																	var node = e.cyTarget;
																	var neighborhood = node
																			.neighborhood()
																			.add(
																					node);

																	//  cy.elements().removeClass('highlighted');
																	// neighborhood.addClass('highlighted');
																	cy
																			.elements()
																			.addClass(
																					'faded');
																	neighborhood
																			.removeClass('faded');

																	if (node
																			.hasClass("disease")) {
																		if (disease_names_on
																				.prop('checked') == false)
																			node
																					.css(
																							'content',
																							'data(id)');
																	}
																});

												cy
														.on(
																'tap',
																function(e) {
																	if (e.cyTarget === cy) {
																		//cy.elements().removeClass('highlighted');
																		cy
																				.elements()
																				.removeClass(
																						'faded');

																		if (disease_names_on
																				.prop('checked') == false)
																			cy
																					.elements(
																							'node.disease')
																					.css(
																							'content',
																							'');
																	}
																});

											}
										});
						var cy = $('#cy').cytoscape('get');
						cy.load(json);
						cy.boxSelectionEnabled(false);
						var defaults = ({
							zoomFactor : 0.05, // zoom factor per zoom tick
							zoomDelay : 45, // how many ms between zoom ticks
							minZoom : 0.1, // min zoom level
							maxZoom : 10, // max zoom level
							fitPadding : 50, // padding when fitting
							panSpeed : 10, // how many ms in between pan ticks
							panDistance : 10, // max pan distance per tick
							panDragAreaSize : 75, // the length of the pan drag box in which the vector for panning is calculated (bigger = finer control of pan speed and direction)
							panMinPercentSpeed : 0.25, // the slowest speed we can pan by (as a percent of panSpeed)
							panInactiveArea : 8, // radius of inactive area in pan drag box
							panIndicatorMinOpacity : 0.5, // min opacity of pan indicator (the draggable nib); scales from this to 1.0
							autodisableForMobile : true, // disable the panzoom completely for mobile (since we don't really need it with gestures like pinch to zoom)

							// icon class names
							sliderHandleIcon : 'fa fa-minus',
							zoomInIcon : 'fa fa-plus',
							zoomOutIcon : 'fa fa-minus',
							resetIcon : 'fa fa-expand'
						});
						$('#cy').cytoscapePanzoom(defaults);

						//set the initial states of checkboxes  
						disease_on.prop('checked', true);
						gene_on.prop('checked', true);
						gene_names_on.prop('checked', true);
						disease_names_on.prop('checked', false);

						var disease_nodes;
						var gene_nodes;
						var disease_edges = cy.elements('edge.disease_term');
						var disease_gene_edges = cy
								.elements('edge.disease_gene');
						var gene_edges = cy.elements('edge.gene');
						var term_nodes;
						disease_on.on('switchChange.bootstrapSwitch',
								function() {
									if (!disease_on.is(":checked")) {
										disease_nodes = cy
												.elements('node.disease');
										term_nodes = cy.elements('node.term');
										cy.remove(disease_nodes);
										cy.remove(term_nodes);
										layout_change(1000);
									} else {
										cy.add(disease_nodes);
										cy.add(term_nodes);
										cy.add(disease_edges);
										cy.add(disease_gene_edges);
										layout_change(1000);
									}
								});
						gene_on.on('switchChange.bootstrapSwitch', function() {
							if (!gene_on.is(":checked")) {
								gene_nodes = cy.elements('node.gene');
								cy.remove(gene_nodes);
								layout_change(1000);
							} else {
								cy.add(gene_nodes);
								cy.add(gene_edges);
								cy.add(disease_gene_edges);
								layout_change(1000);
							}
						});
						disease_names_on.on('switchChange.bootstrapSwitch',
								function() {
									if (disease_names_on.is(":checked")) {
										cy.elements('node.disease').css(
												'content', 'data(id)');
									} else {
										cy.elements('node.disease').css(
												'content', '');
									}
								});

						gene_names_on.on('switchChange.bootstrapSwitch',
								function() {
									if (gene_names_on.is(":checked")) {
										cy.elements('node.gene').css('content',
												'data(id)');
									} else {
										cy.elements('node.gene').css('content',
												'	');
									}
								});
						var show_edges = $('#show_edges');
						show_edges
								.change(function() {
									if (show_edges.find("option:selected")
											.val() == "all") {
										cy.elements('edge').show();
									}
									if (show_edges.find("option:selected")
											.val() == "HPRD") {
										cy.elements('edge').hide();
										cy.elements('edge.HPRD').show();
									}
									if (show_edges.find("option:selected")
											.val() == "GeneFamily") {
										cy.elements('edge').hide();
										cy.elements('edge.GENE_FAMILY').show();
									}
									if (show_edges.find("option:selected")
											.val() == "Biosystem") {
										cy.elements('edge').hide();
										cy.elements('edge.BIOSYSTEM').show();
									}
									if (show_edges.find("option:selected")
											.val() == "TranscriptionInteraction") {
										cy.elements('edge').hide();
										cy.elements('edge.HTRI').show();
									}
								});

						$('#save_photo').click(function() {
							window.open(cy.png({
								'bg' : '#ffffff'
							}));
						});
						$("#tooltips")
								.click(
										function() {
											window
													.open(
															"img/network_instruction.png",
															"",
															"height=600px, width=1000px,top=200px, left = 200px");
										});

						var adjust_layout = $('#adjust_layout');
						function layout_change(time) {
							if (adjust_layout.find("option:selected").val() == "force") {
								cy.layout({
									name : 'arbor',
									stiffness : 0,
									maxSimulationTime : time
								});
							}
							if (adjust_layout.find("option:selected").val() == "circle") {
								cy.layout({
									name : 'circle',
									rStepSize : 30
								});
							}
							if (adjust_layout.find("option:selected").val() == "grid") {
								cy.layout({
									name : 'grid'
								});
							}
							if (adjust_layout.find("option:selected").val() == "concentric") {
								cy.layout({
									name : 'concentric'
								});
							}

						}
						;
						adjust_layout.change({
							time : 1000
						}, function(event) {
							layout_change(event.data.time);
						});
						setTimeout(function() {
							adjust_layout.selectpicker('val', 'force');
							adjust_layout.selectpicker('refresh');
							adjust_layout.change();
						}, 1500);

					} 
					
					
//					//if(if_json_success)
//					json_url = json_url.replace(/network.json/, "");
//					json_url += "details.json";
//					$.ajaxSetup({
//						'async' : false
//					});
//					$
//							.getJSON(json_url, function(data) {
//								json = data;
//								var MAX = json.length;
//								BARPLOT(MAX);
//								if_json_success = true;
//							})
//							.fail(
//									function(jqxhr, textStatus, error) {
//										$("#network").remove();
//										$("a[href=#network]").remove();
//										$("a[href=#barplot]").remove();
//										$("a[href=#details]").remove();
//										alert("Sorry, the result is not available!! Please try to refresh");
//										if_json_success = false;
//									});
//					if (if_json_success) {
//						var current_page = $("#page-number").val();
//
//						$("#details").find("a").css("cursor", "pointer");
//						var MAX_PAGE = Math.ceil(json.length / 50);
//						for (var i = 0; i < 50 && i < json.length; i++) {
//							result_panel.append(json[i][1]);
//						}
//						result_panel.accordion({
//							header : "h3.gene_score",
//							heightStyle : "content",
//							collapsible : true,
//							active : 0
//						});
//						result_panel.accordion("refresh");
//						result_panel.accordion("option", "active", 0);
//						var change_page = function(page) {
//							if ((page < 1) || (page > MAX_PAGE)) {
//								return;
//							}
//							var start = (page - 1) * 50;
//							result_panel.empty();
//							for (var i = start; (i < start + 50)
//									&& i < json.length; i++) {
//								result_panel.append(json[i][1]);
//							}
//							result_panel.accordion("refresh");
//							$("#page-number").val(page);
//							$("a.outside").attr("target", "_blank");
//						}
//
//						$("#page-start").click(function() {
//							change_page(1);
//						});
//						$("#page-end").click(function() {
//							change_page(MAX_PAGE);
//						});
//						$("#page-next").click(function() {
//							current_page = $("#page-number").val();
//							change_page(++current_page);
//						});
//						$("#page-previous").click(function() {
//							current_page = $("#page-number").val();
//							change_page(--current_page);
//						});
//						$("#page-go").click(function() {
//							current_page = $("#page-number").val();
//							change_page(parseInt(current_page));
//						});
//
//					}//if(if_json_success)
//					for (var i = 0; i < 500 && i < json.length; i++) {
//						var bar_modal = $(
//								'<div class="modal fade" id="gene'
//										+ i
//										+ '" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
//										+ '<div class="modal-dialog modal-lg"><div class="modal-content"><div class="modal-header">'
//										+ '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'
//										+ '<h4 class="modal-title text-primary" id="myModalLabel">'
//										+ json[i][0]
//										+ '</h4></div><div class="modal-body">'
//										+ json[i][1]
//										+ '</div><div class="modal-footer"><button type="button" class="btn btn-primary" data-dismiss="modal">Close</button></div></div></div></div>')
//								.insertAfter("#bar");
//						bar_modal.find("div.modal-body div p").first().remove();
//
//					}
//
//					$("#details")
//							.prepend(
//									'<a class="outside" style="padding-top:20px;color:#010101;width:100%;text-align:center;display:block" href = "'
//											+ window.location.href
//											+ 'out.predicted_gene_scores">View source data</a>');
//					$("a.outside").attr("target", "_blank");
				});
