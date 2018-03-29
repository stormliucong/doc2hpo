console.log("barplot begin!");

var BARPLOT = function(MAX) {
	var valueLabelWidth = 40; // space reserved for value labels (right)
	var barHeight = 23; // height of one bar
	var barLabelWidth = 100; // space reserved for bar labels
	var barLabelPadding = 5; // padding between bar and bar labels (left)
	var gridLabelHeight = 18; // space reserved for gridline labels
	var gridChartOffset = 3; // space between start of grid and first bar
	var maxBarWidth = 600; // width of the bar with the max value

	function type(d) {
		d.value = parseFloat(d.value); // coerce to number
		return d;
	}
	;

	var generate_barplot = function(file) {
		console.log('please open file: ' + file);
		d3
				.tsv(
						file,
						type,
						function(error, data) {
							var slice_length;
							if (data.length >= 500)
								slice_length = 500;
							else
								slice_length = data.length;
							data = data.slice(0, slice_length);

							// accessor functions
							var barLabel = function(d) {
								return d['Gene'];
							};
							var barValue = function(d) {
								return parseFloat(d['Score']);
							};
							var colorScale = d3.scale.linear().domain(
									[ 0, d3.max(data, barValue) ]).range(
									[ "#ef7788", "#5555ef" ]);
							var colorScale2 = d3.scale.linear().domain(
									[ 0, d3.max(data, barValue) ]).range(
									[ "#cf0022", "#1111cf" ]);
							// scales
							var yScale = d3.scale.ordinal().domain(
									d3.range(0, data.length)).rangeBands(
									[ 0, data.length * barHeight ], 0.10);
							var y = function(d, i) {
								return yScale(i);
							};
							var yText = function(d, i) {
								return y(d, i) + yScale.rangeBand() / 2;
							};
							var x = d3.scale.linear().domain(
									[ 0, d3.max(data, barValue) ]).range(
									[ 0, maxBarWidth ]);
							// svg container element
							var chart = d3.select('#bar').append("svg").attr(
									'width', '100%').attr(
									'height',
									gridLabelHeight + gridChartOffset
											+ data.length * barHeight).style(
									'background', '#fcf3ff');
							// grid line labels
							var gridContainer = chart.append('g').attr(
									'transform',
									'translate(' + barLabelWidth + ','
											+ gridLabelHeight + ')');
							gridContainer.selectAll("text").data(x.ticks(10))
									.enter().append("text").attr("x", x).attr(
											"dy", -3).attr("text-anchor",
											"middle").text(String);
							// vertical grid lines
							gridContainer.selectAll("line").data(x.ticks(10))
									.enter().append("line").attr("x1", x).attr(
											"x2", x).attr("y1", 0).attr(
											"y2",
											yScale.rangeExtent()[1]
													+ gridChartOffset).style(
											"stroke", "#cbb").style(
											"stroke-width", "0.5");
							// bar labels
							var labelsContainer = chart
									.append('g')
									.attr(
											'transform',
											'translate('
													+ (barLabelWidth - barLabelPadding)
													+ ','
													+ (gridLabelHeight + gridChartOffset)
													+ ')');
							labelsContainer
									.selectAll('text')
									.data(data)
									.enter()
									.append('text')
									.attr('y', yText)
									.attr('stroke', 'none')
									.attr("font-weight", "bold")
									.attr("font-size", "15px")
									.attr("dy", ".35em")
									// vertical-align: middle
									.attr('text-anchor', 'end')
									.append("a")
									.attr(
											"xlink:href",
											function(d) {
												return "http://www.ncbi.nlm.nih.gov/gene/"
														+ d.ID;
											})
									.attr('target', '_blank')
									.attr('opacity', '0.8')
									.attr('fill', function(d) {
										return colorScale2(barValue(d));
									})
									.on(
											'mouseover',
											function(d) {
												d3.select(this).attr('opacity',
														'1').attr('fill',
														'#fe3333')
											})
									.on(
											'mouseout',
											function(d) {
												d3
														.select(this)
														.attr('opacity', '0.8')
														.attr(
																'fill',
																function(d) {
																	return colorScale2(barValue(d));
																})
											}).text(barLabel);
							// bars
							var url = document.URL;
							url = url.replace(/barplot.html.*/, "");
							var barsContainer = chart
									.append('g')
									.attr(
											'transform',
											'translate('
													+ barLabelWidth
													+ ','
													+ (gridLabelHeight + gridChartOffset)
													+ ')');
							barsContainer
									.selectAll("rect")
									.data(data)
									.enter()
									.append("rect")
									.on(
											'mouseover',
											function(d, i) {
												if (i < MAX)
													d3.select(this).attr(
															'fill', '#fecc33');
											})
									.on(
											'mouseout',
											function(d, i) {
												d3
														.select(this)
														.attr(
																'fill',
																function(d) {
																	return colorScale(barValue(d));
																});
											}).attr('data-toggle', "modal")
									.attr('data-target', function(d, i) {
										return "#gene" + i;
									}).attr('y', y).attr('height',
											yScale.rangeBand()).attr('width',
											function(d) {
												return x(barValue(d));
											}).attr('stroke', 'white').attr(
											'fill', function(d) {
												return colorScale(barValue(d));
											}).style('cursor', function(d, i) {
										if (i < MAX)
											return 'pointer';
									});

							// bar value labels
							barsContainer.selectAll("text").data(data).enter()
									.append("text").attr("x", function(d) {
										return x(barValue(d));
									}).attr("y", yText).attr("dx", 3) // padding-left
									.attr("dy", ".35em") // vertical-align:
									// middle
									.attr("text-anchor", "start") // text-align:
									// right
									.attr("fill", "#cc3377").attr("font-size",
											"10px").attr("stroke", "none")
									.text(function(d) {
										return d3.round(barValue(d), 3);
									});
							// start line
							barsContainer.append("line").attr("y1",
									-gridChartOffset).attr("y2",
									yScale.rangeExtent()[1] + gridChartOffset)
									.style("stroke", "#000").style(
											"stroke-width", "0");
							// -------------------------- HI score
							// ---------------------------
							var annotationStart = barLabelPadding
									+ gridChartOffset + barLabelWidth
									+ maxBarWidth + 20;
							HI_score_width = 60;
							annotationStart += 3;
							var annotationContainer = {};
							annotationContainer.HI = chart
									.append('g')
									.attr(
											'transform',
											'translate('
													+ annotationStart
													+ ','
													+ (gridLabelHeight + gridChartOffset)
													+ ')');
							annotationContainer.HI
									.selectAll('rect')
									.data(data)
									.enter()
									.append('rect')
									.attr("x", 10)
									.attr('y', y)
									.attr('fill', "#55c3cf")
									.attr(
											'width',
											function(d) {
												return ('HaploinsufficiencyScore' in d) ? HI_score_width
														: null;
											})
									.attr('height', 20)
									.attr({
										'rx' : 5,
										'ry' : 5
									})
									.style('cursor', 'pointer')
									.on(
											'click',
											function() {
												window
														.open("http://journals.plos.org/plosgenetics/article?id=10.1371/journal.pgen.1001154")
											});
							annotationContainer.HI
									.selectAll('text')
									.data(data)
									.enter()
									.append('text')
									.attr('x', 13)
									.attr('y', yText)
									.attr('dy', '.35em')
									.attr("text-anchor", "start")
									// text-align: right
									.attr("fill", "#fefefe")
									.attr("font-size", "9px")
									.attr('font-weight', '700')
									.text(
											function(d) {
												return ('HaploinsufficiencyScore' in d) ? "HI: "
														+ Number(
																d['HaploinsufficiencyScore'])
																.toFixed(3)
														: null;
											})
									.style('cursor', 'pointer')
									.on(
											'click',
											function() {
												window
														.open("http://journals.plos.org/plosgenetics/article?id=10.1371/journal.pgen.1001154")
											});

							// -------------------------- IT score
							// ---------------------------
							IT_score_width = 80;
							annotationStart += 5 + HI_score_width;
							var annotationContainer = {};
							annotationContainer.IT = chart
									.append('g')
									.attr(
											'transform',
											'translate('
													+ annotationStart
													+ ','
													+ (gridLabelHeight + gridChartOffset)
													+ ')');
							annotationContainer.IT
									.selectAll('rect')
									.data(data)
									.enter()
									.append('rect')
									.attr("x", 10)
									.attr('y', y)
									.attr('fill', "#ff5566")
									.attr(
											'width',
											function(d) {
												return ('GeneIntoleranceScore' in d) ? IT_score_width
														: null;
											})
									.attr('height', 20)
									.attr({
										'rx' : 5,
										'ry' : 5
									})
									.style('cursor', 'pointer')
									.on(
											'click',
											function() {
												window
														.open("http://journals.plos.org/plosgenetics/article?id=10.1371/journal.pgen.1003709")
											});
							annotationContainer.IT
									.selectAll('text')
									.data(data)
									.enter()
									.append('text')
									.attr('x', 13)
									.attr('y', yText)
									.attr('dy', '.35em')
									.attr("text-anchor", "start")
									// text-align: right
									.attr("fill", "#fefefe")
									.attr("font-size", "9px")
									.attr('font-weight', '700')
									.text(
											function(d) {
												return ('GeneIntoleranceScore' in d) ? "RVIS: "
														+ Number(
																d['GeneIntoleranceScore'])
																.toFixed(3)
														: null;
											})
									.style('cursor', 'pointer')
									.on(
											'click',
											function() {
												window
														.open("http://journals.plos.org/plosgenetics/article?id=10.1371/journal.pgen.1003709")
											});

						});
	}

	$("#bar div").css({
		'padding' : '20px 20px'
	});
	$("#bar a").css({
		'margin-left' : '30px'
	});

	$.ajax({
		url : work_dir + '/out.annotated_gene_list',
		type : 'HEAD',
		error : function() {
			//file not exists
			var work_dir = $('#workdir').text();
			console.log("out.final_gene_list not exists!");
			var file = work_dir + '/out.final_gene_list';
			generate_barplot(file);

		},
		success : function() {
			//file exists
			var work_dir = $('#workdir').text();
			console.log("out.annotated_gene_list not exists!");
			var file = work_dir + '/out.annotated_gene_list';
			
			generate_barplot(file);
		}
	});

	$('#barplot h3.result')
			.after(
					'<a class="outside" style="color:#010101;width:100%;padding-bottom:20px;display:block" href = "'
							+ window.location.href
							+ 'out.final_gene_list">View source data</a>');

}

$(window).load(function() {
	var work_dir = $('#workdir').text();
	console.log(work_dir);
	json_url = work_dir + '/details.json';
	console.log("json is here " + json_url);
	$.ajaxSetup({
		'async' : false
	});
	$.getJSON(json_url, function(data) {
		json = data;
		var MAX = json.length;
		BARPLOT(MAX);
		if_json_success = true;
	}).fail(function(jqxhr, textStatus, error) {
		alert("Sorry, the result is not available!! Please try to refresh");
		if_json_success = false;
	});
})
