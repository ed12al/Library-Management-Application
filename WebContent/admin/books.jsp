<%@include file="/template.html" %>

<script type="text/javascript">
$("#booksTable").onload = viewBooks();

function viewBooks(pageNumber){
	$.ajax({
		url: "viewBooks",
		type: "GET",
		data: { 
			searchString: $('#searchString').val(),
			pageNo: pageNumber
		}
	}).done(function(response) {
		$("#booksTable").html(response);
	});
}

function viewBook(Id){
	$.ajax({
		url: "viewBook",
		type: "GET",
		data: { 
			bookId: Id
		}
	}).done(function(response) {
		$("#viewBookModalBody").html(response);
	});
}

function editBook(Id){
	$.ajax({
		url:"editBook",
		type: "GET",
		data: {
			bookId: Id
		}
	}).done(function(response) {
		$("#editBookModalBody").html(response);
	});
}

function updateBook(){
	$.ajax({
		url: "editBook",
		type: "POST",
		error: function (xhr,status,error) {
			toastr["error"](error);
	    },
		success: function(response){
			toastr["success"]("Successfully edited the book");
			viewBooks(1);
		},
		data: {
			bookId: $('#editBookId').val(),
			bookName: $('#editBookName').val()
		}
	});
}

function deleteBook(Id){
	$.ajax({
		url:"deleteBook",
		type: "GET",
		data: {
			bookId: Id
		}
	}).done(function(response) {
		$("#deleteBookModalBody").html(response);
	});
}

function removeBook(){
	$.ajax({
		url: "deleteBook",
		type: "POST",
		error: function (xhr,status,error) {
			toastr["error"](error);
	    },
		success: function(response){
			toastr["success"]("Successfully deleted the book");
			viewBooks(1);
		},
		data: {
			bookId: $("#deleteBookId").val()
		}
	});
}

function addBook(){
	$.ajax({
		url: "addBook",
		type: "POST",
		error: function (xhr,status,error){
			toastr["error"](error);
		},
		success: function(response){
			toastr["success"]("Successfully added the book");
			viewBooks(1);
		},
		complete: function(xhr,status){
			$("#addBookName").val("");
		},
		data: {
			bookName: $("#addBookName").val()
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
			<input type="text" class="form-control" placeholder="Title"
				aria-describedby="basic-addon1" name="searchString" id="searchString" onkeyup="viewBooks(1)">
		</div>
		<button class="btn btn-primary" data-toggle='modal' data-target='#addBookModal'>Add Book</button>	
		<div id="booksTable"></div>
	</div>
</div>

<div class="modal fade" id="viewBookModal" tabindex="-1" role="dialog" aria-labelledby="viewBookModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="viewBookModalLabel">View Book Detail</h4>
     		</div>
     		<div class="modal-body" id="viewBookModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="editBookModal" tabindex="-1" role="dialog" aria-labelledby="editBookModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="editBookModalLabel">Edit Book</h4>
     		</div>
     		<div class="modal-body" id="editBookModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateBook()">Save</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="deleteBookModal" tabindex="-1" role="dialog" aria-labelledby="deleteBookModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="deleteBookModalLabel">Delete Book</h4>
     		</div>
     		<div class="modal-body" id="deleteBookModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="removeBook()">Delete</button>
      		</div>
		</div>
	</div>
</div>

<div class="modal fade" id="addBookModal" tabindex="-1" role="dialog" aria-labelledby="addBookModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title" id="addBookModalLabel">Add a New Book</h4>
     		</div>
     		<div class="modal-body" id="addBookModalBody">
     			<div class="form-group">
     				<label class="control-label">Enter Title:</label><input type="text" class="form-control" id="addBookName">
     			</div>
     			<div class="form-group">
     				<label class="control-label">Pick genres:</label>
     				<label class="checkbox-inline">
  					<input type="checkbox" id="inlineCheckbox1" value="option1"> genre long name
					</label>
					<label class="checkbox-inline">
  					<input type="checkbox" id="inlineCheckbox2" value="option2"> 2
					</label>
					<label class="checkbox-inline">
 				 	<input type="checkbox" id="inlineCheckbox3" value="option3"> 3
					</label>
     			</div>
			</div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addBook()">Add</button>
      		</div>
		</div>
	</div>
</div>