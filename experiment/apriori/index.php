<?php

//Include the Apriori class
require_once("apriori.php");

//Transactions from Array
$data = [
	"transactions" => [
		["tomaat", "kaas", "ui"],
		["kaas", "ui"],
		["kaas", "kaas"]
	]
];
//Transactions from Text file
$data = [
	"file" => "breien.txt"
];

$apriori 		= new Apriori($data);
$associations 	= $apriori::get_associations();

//Support percentage
$min_support 		= 0.1;
//Confidence percentage
$min_confidence 	= 0.5;

?>

<!-- Demo styling -->
<link href="tablesorter/docs/css/jq.css" rel="stylesheet">
<!-- jQuery: required (tablesorter works with jQuery 1.2.3+) -->
<script src="tablesorter/docs/js/jquery-1.2.6.min.js"></script>

<!-- Pick a theme, load the plugin & initialize plugin -->
<link href="tablesorter/dist/css/theme.default.min.css" rel="stylesheet">
<script src="tablesorter/dist/js/jquery.tablesorter.min.js"></script>
<script src="tablesorter/dist/js/jquery.tablesorter.widgets.min.js"></script>
<script>
$(function(){
	$('table').tablesorter({
		widgets        : ['zebra', 'columns'],
		usNumberFormat : false,
		sortReset      : true,
		sortRestart    : true
	});
});
</script>

<table class="tablesorter">
<thead>
	<tr>
		<th>Concept</th>
		<th>Co-concept</th>
		<th>Containment</th>
		<th>Support</th>
		<th>Appearance</th>
		<th>Confidence</th>
	</tr>
</thead>
<tbody>
	<?php
	foreach($associations as $association) {
		echo '
		<tr>
			<td>'.$association[0].'</td>
			<td>'.$association[1].'</td>
			<td>'.$association["containment"].'</td>
			<td>'.$association["support"].'</td>
			<td>'.$association["appearance"].'</td>
			<td>'.$association["confidence"].'</td>
		</tr>';
	}
	?>
	

</tbody>
</table>