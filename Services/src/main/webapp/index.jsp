<html>
	<title>Spring MVC - Ajax</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<body>
	
		<h2>Hello World!</h2>
		<script type="text/javascript">
			$(document).ready(function() {
				
				$.getJSON(
						'http://localhost:8080/sports-buddies/api/regs/1', 
						function(reg) {
							alert(reg.BigId + " " + reg.LittleId + " " + reg.EventId);
						});
			}
		</script>
	
	</body>
</html>
