<%@include file="/template.html" %>

<script type="text/javascript">
$("#branchesTable").onload = viewBranches();

function viewBranches(pageNumber){
	$.ajax({
		url: "viewBranches",
		type: "GET",
		data: { 
			searchString: $('#searchString').val(),
			pageNo: pageNumber
		}
	}).done(function(response) {
		$("#branchesTable").html(response);
	});
}

function viewBranch(Id){
	$.ajax({
		url: "viewBranch",
		type: "GET",
		data: { 
			branchId: Id
		}
	}).done(function(response) {
		$("#viewBranchModalBody").html(response);
	});
}

function editBranch(Id){
	$.ajax({
		url:"editBranch",
		type: "GET",
		data: {
			branchId: Id
		}
	}).done(function(response) {
		$("#editBranchModalBody").html(response);
	});
}

function updateBranch(){
	$.ajax({
		url: "editBranch",
		type: "POST",
		error: function (xhr,status,error) {
			toastr["error"](error);
	    },
		success: function(response){
			toastr["success"]("Successfully edited the branch");
			viewBranches(1);
		},
		data: {
			branchId: $('#editBranchId').val(),
			branchName: $('#editBranchName').val(),
			branchAddress: $('#editBranchAddress').val()
		}
	});
}

function deleteBranch(Id){
	$.ajax({
		url:"deleteBranch",
		type: "GET",
		data: {
			branchId: Id
		}
	}).done(function(response) {
		$("#deleteBranchModalBody").html(response);
	});
}

function removeBranch(){
	$.ajax({
		url: "deleteBranch",
		type: "POST",
		error: function (xhr,status,error) {
			toastr["error"](error);
	    },
		success: function(response){
			toastr["success"]("Successfully deleted the branch");
			viewBranches(1);
		},
		data: {
			branchId: $("#deleteBranchId").val()
		}
	});
}

function getAddBranch(){
	$("#addBranchName").val("");
	$('#addBranchAddress').val("");
}

function addBranch(){
	$.ajax({
		url: "addBranch",
		type: "POST",
		error: function (xhr,status,error){
			toastr["error"](error);
		},
		success: function(response){
			toastr["success"]("Successfully added the branch");
			viewBranches(1);
		},
		data: {
			branchName: $("#addBranchName").val(),
			branchAddress: $('#addBranchAddress').val()
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
			<input type="text" class="form-control" placeholder="Branch Name"
				aria-describedby="basic-addon1" name="searchString" id="searchString" onkeyup="viewBranches(1)">	
		</div>
		<button class="btn btn-primary" data-toggle='modal' data-target='#addBranchModal' onclick='getAddBranch()'>Add Branch</button>
		<div id="branchesTable"></div>
	</div>
</div>

<div class="modal fade" id="viewBranchModal" tabindex="-1" role="dialog" aria-labelledby="viewBranchModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="viewBranchModalLabel">View Branch Detail</h4>
     		</div>
     		<div class="modal-body" id="viewBranchModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="editBranchModal" tabindex="-1" role="dialog" aria-labelledby="editBranchModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="editBranchModalLabel">Edit Branch</h4>
     		</div>
     		<div class="modal-body" id="editBranchModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateBranch()">Save</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="deleteBranchModal" tabindex="-1" role="dialog" aria-labelledby="deleteBranchModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="deleteBranchModalLabel">Delete Branch</h4>
     		</div>
     		<div class="modal-body" id="deleteBranchModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="removeBranch()">Delete</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="addBranchModal" tabindex="-1" role="dialog" aria-labelledby="addBranchModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="addBranchModalLabel">Add an New Branch</h4>
     		</div>
     		<div class="modal-body" id="addBranchModalBody">
     			<div class="form-group">
     				<label class="control-label">Enter Branch Name:</label><input type="text" class="form-control" id="addBranchName">
     			</div>
     			<div class="form-group">
     				<label class="control-label">Enter Branch Address:</label><input type="text" class="form-control" id="addBranchAddress">
     			</div>
			</div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addBranch()">Add</button>
      		</div>
		</div>
	</div>
</div>