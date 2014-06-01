$(document).ready(function() {
  
  var bigNames = ["test1", "test2"];
  var littleNames = ["test3", "test4"];
  
  $( "#little" ).autocomplete({
    source: bigNames
  });
    
  $( "#big" ).autocomplete({
    source: littleNames
  });
});