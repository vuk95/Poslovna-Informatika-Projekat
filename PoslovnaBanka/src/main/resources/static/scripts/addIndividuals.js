
function addIndividuals() {
	
	var $form = $("#addClient");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	console.log(s);

	$.ajax({
		type : 'POST',
		url : "individual/addIndividuals",
		contentType : 'application/json',
		dataType : "json",
		data : s,
		success : function(data) {
			alert("Uradjen je add");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
	
}
function getFormData($form){
	
	 var unindexed_array = $form.serializeArray();
	    var indexed_array = {};

	    $.map(unindexed_array, function(n, i){
	        indexed_array[n['name']] = n['value'];
	    });

	    return indexed_array;
	
}

function openProfile(id) {
	$.ajax({
		url: "individual/indivudalClient" + id,
		type: "GET",
		contentType:"application/json",
		dataType:"json",
		success: function(data) {
			if(data != null) {
				window.location.href = "IndividualsProfile.html";
			}
		},
		error: function(jqxhr,textStatus,errorThrown) {
			alert(errorThrown);
		}
	});
}