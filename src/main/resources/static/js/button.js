/**
 * 
 */
$( "#bind" ).click(function() {
   $(this).attr("disabled", "disabled");
   $("#unbind").removeAttr("disabled");
});
$( "#unbind" ).click(function() {
    $(this).attr("disabled", "disabled");
    $("#bind").removeAttr("disabled");
});
