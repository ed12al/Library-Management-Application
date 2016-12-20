<%@include file="/template.html" %>

<script type="text/javascript">
$("#publishersTable").onload = viewPublishers();

function viewPublishers(pageNumber){
	$.ajax({
		url: "viewPublishers",
		type: "GET",
		data: { 
			searchString: $('#searchString').val(),
			pageNo: pageNumber
		}
	}).done(function(response) {
		$("#publishersTable").html(response);
	});
}

function viewPublisher(Id){
	$.ajax({
		url: "viewPublisher",
		type: "GET",
		data: { 
			publisherId: Id
		}
	}).done(function(response) {
		$("#viewPublisherModalBody").html(response);
	});
}

function editPublisher(Id){
	$.ajax({
		url:"editPublisher",
		type: "GET",
		data: {
			publisherId: Id
		}
	}).done(function(response) {
		$("#editPublisherModalBody").html(response);
	});
}

function updatePublisher(){
	$.ajax({
		url: "editPublisher",
		type: "POST",
		error: function (xhr,status,error) {
			toastr["error"](error);
	    },
		success: function(response){
			toastr["success"]("Successfully edited the publisher");
			viewPublishers(1);
		},
		data: {
			publisherId: $('#editPublisherId').val(),
			publisherName: $('#editPublisherName').val(),
			publisherPhone: $('#editPublisherPhone').val(),
			publisherAddress: $('#editPublisherAddress').val()
		}
	});
}

function deletePublisher(Id){
	$.ajax({
		url:"deletePublisher",
		type: "GET",
		data: {
			publisherId: Id
		}
	}).done(function(response) {
		$("#deletePublisherModalBody").html(response);
	});
}

function removePublisher(){
	$.ajax({
		url: "deletePublisher",
		type: "POST",
		error: function (xhr,status,error) {
			toastr["error"](error);
	    },
		success: function(response){
			toastr["success"]("Successfully deleted the publisher");
			viewPublishers(1);
		},
		data: {
			publisherId: $("#deletePublisherId").val()
		}
	});
}

function getAddPublisher(){
	$("#addPublisherName").val("");
	$('#addPublisherPhone').val("");
	$('#addPublisherAddress').val("");
}

function addPublisher(){
	$.ajax({
		url: "addPublisher",
		type: "POST",
		error: function (xhr,status,error){
			toastr["error"](error);
		},
		success: function(response){
			toastr["success"]("Successfully added the publisher");
			viewPublishers(1);
		},
		data: {
			publisherName: $("#addPublisherName").val(),
			publisherPhone: $('#addPublisherPhone').val(),
			publisherAddress: $('#addPublisherAddress').val()
		}
	})
}

</script>

<div class="container theme-showcase" role="main">
	<div class="jumbotron">
		<h1>GCIT Library Management System</h1>
		<p>Welcome to GCIT Library Management System. Have Fun Shopping!</p>
		<h3>Hello Administrator! What do you want to do?</h3>
		<div class="input-group">
			<input type="text" class="form-control" placeholder="Publisher Name"
				aria-describedby="basic-addon1" name="searchString" id="searchString" onkeyup="viewPublishers(1)">	
		</div>
		<button class="btn btn-primary" data-toggle='modal' data-target='#addPublisherModal' onclick='getAddPublisher()'>Add Publisher</button>
		<div id="publishersTable"></div>
	</div>
</div>

<div class="modal fade" id="viewPublisherModal" tabindex="-1" role="dialog" aria-labelledby="viewPublisherModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="viewPublisherModalLabel">View Publisher Detail</h4>
     		</div>
     		<div class="modal-body" id="viewPublisherModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="editPublisherModal" tabindex="-1" role="dialog" aria-labelledby="editPublisherModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="editPublisherModalLabel">Edit Publisher</h4>
     		</div>
     		<div class="modal-body" id="editPublisherModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updatePublisher()">Save</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="deletePublisherModal" tabindex="-1" role="dialog" aria-labelledby="deletePublisherModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="deletePublisherModalLabel">Delete Publisher</h4>
     		</div>
     		<div class="modal-body" id="deletePublisherModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="removePublisher()">Delete</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="addPublisherModal" tabindex="-1" role="dialog" aria-labelledby="addPublisherModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="addPublisherModalLabel">Add an New Publisher</h4>
     		</div>
     		<div class="modal-body" id="addPublisherModalBody">
     			<div class="form-group">
     				<label class="control-label">Enter Publisher Name:</label><input type="text" class="form-control" id="addPublisherName">
     			</div>
     			<div class="form-group">
     				<label class="control-label">Enter Publisher Address:</label><input type="text" class="form-control" id="addPublisherAddress">
     			</div>
     			<div class="form-group">
     				<label class="control-label">Enter Publisher Phone:</label><input type="text" class="form-control" id="addPublisherPhone">
     			</div>
			</div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addPublisher()">Add</button>
      		</div>
		</div>
	</div>
</div>