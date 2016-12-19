<%@include file="/template.html" %>

<script type="text/javascript">
$("#borrowersTable").onload = viewBorrowers();

function viewBorrowers(pageNumber){
	$.ajax({
		url: "viewBorrowers",
		type: "GET",
		data: { 
			searchString: $('#searchString').val(),
			pageNo: pageNumber
		}
	}).done(function(response) {
		$("#borrowersTable").html(response);
	});
}

function viewBorrower(Id){
	$.ajax({
		url: "viewBorrower",
		type: "GET",
		data: { 
			borrowerId: Id
		}
	}).done(function(response) {
		$("#viewBorrowerModalBody").html(response);
	});
}

function editBorrower(Id){
	$.ajax({
		url:"editBorrower",
		type: "GET",
		data: {
			borrowerId: Id
		}
	}).done(function(response) {
		$("#editBorrowerModalBody").html(response);
	});
}

function updateBorrower(){
	$.ajax({
		url: "editBorrower",
		type: "POST",
		error: function (xhr,status,error) {
			toastr["error"](error);
	    },
		success: function(response){
			toastr["success"]("Successfully edited the borrower");
			viewBorrowers(1);
		},
		data: {
			borrowerId: $('#editCardNo').val(),
			borrowerName: $('#editBorrowerName').val(),
			borrowerPhone: $('#editBorrowerPhone').val(),
			borrowerAddress: $('#editBorrowerAddress').val()
		}
	});
}

function deleteBorrower(Id){
	$.ajax({
		url:"deleteBorrower",
		type: "GET",
		data: {
			borrowerId: Id
		}
	}).done(function(response) {
		$("#deleteBorrowerModalBody").html(response);
	});
}

function removeBorrower(){
	$.ajax({
		url: "deleteBorrower",
		type: "POST",
		error: function (xhr,status,error) {
			toastr["error"](error);
	    },
		success: function(response){
			toastr["success"]("Successfully deleted the borrower");
			viewBorrowers(1);
		},
		data: {
			borrowerId: $("#deleteCardNo").val()
		}
	});
}

function addBorrower(){
	$.ajax({
		url: "addBorrower",
		type: "POST",
		error: function (xhr,status,error){
			toastr["error"](error);
		},
		success: function(response){
			toastr["success"]("Successfully added the borrower");
			viewBorrowers(1);
		},
		complete: function(xhr,status){
			$("#addBorrowerName").val("");
			$('#addBorrowerPhone').val("");
			$('#addBorrowerAddress').val("");
		},
		data: {
			borrowerName: $("#addBorrowerName").val(),
			borrowerPhone: $('#addBorrowerPhone').val(),
			borrowerAddress: $('#addBorrowerAddress').val()
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
			<input type="text" class="form-control" placeholder="Borrower Name"
				aria-describedby="basic-addon1" name="searchString" id="searchString" onkeyup="viewBorrowers(1)">	
		</div>
		<button class="btn btn-primary" data-toggle='modal' data-target='#addBorrowerModal'>Add Borrower</button>
		<div id="borrowersTable"></div>
	</div>
</div>

<div class="modal fade" id="viewBorrowerModal" tabindex="-1" role="dialog" aria-labelledby="viewBorrowerModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="viewBorrowerModalLabel">View Borrower Detail</h4>
     		</div>
     		<div class="modal-body" id="viewBorrowerModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="editBorrowerModal" tabindex="-1" role="dialog" aria-labelledby="editBorrowerModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="editBorrowerModalLabel">Edit Borrower</h4>
     		</div>
     		<div class="modal-body" id="editBorrowerModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateBorrower()">Save</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="deleteBorrowerModal" tabindex="-1" role="dialog" aria-labelledby="deleteBorrowerModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="deleteBorrowerModalLabel">Delete Borrower</h4>
     		</div>
     		<div class="modal-body" id="deleteBorrowerModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="removeBorrower()">Delete</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="addBorrowerModal" tabindex="-1" role="dialog" aria-labelledby="addBorrowerModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="addBorrowerModalLabel">Add an New Borrower</h4>
     		</div>
     		<div class="modal-body" id="addBorrowerModalBody">
     			<div class="form-group">
     				<label class="control-label">Enter Borrower Name:</label><input type="text" class="form-control" id="addBorrowerName">
     			</div>
     			<div class="form-group">
     				<label class="control-label">Enter Borrower Address:</label><input type="text" class="form-control" id="addBorrowerAddress">
     			</div>
     			<div class="form-group">
     				<label class="control-label">Enter Borrower Phone:</label><input type="text" class="form-control" id="addBorrowerPhone">
     			</div>
			</div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addBorrower()">Add</button>
      		</div>
		</div>
	</div>
</div>