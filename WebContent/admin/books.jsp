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
	var editGenreIds = $('#editGenreId:checked').map(function(){ return $(this).val(); }).get();
	var editAuthorIds = $('#editAuthorId:checked').map(function(){ return $(this).val(); }).get();
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
			bookName: $('#editBookName').val(),
			publisherId: $("#editPublisherId:checked").val(),
			genreIds: editGenreIds,
			authorIds: editAuthorIds
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
	var addGenreIds = $('#addGenreId:checked').map(function(){ return $(this).val(); }).get();
	var addAuthorIds = $('#addAuthorId:checked').map(function(){ return $(this).val(); }).get();
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
		data: {
			bookName: $("#addBookName").val(),
			publisherId: $("#addPublisherId:checked").val(),
			genreIds: addGenreIds,
			authorIds: addAuthorIds
		}
	})
}

function updateBookPublisher(flag){
	$.ajax({
		url: "addPublisher",
		type: "POST",
		error: function (xhr,status,error){
			toastr["error"](error);
		},
		success: function(response){
			toastr["success"]("Successfully added the publisher");
			var publisherName = $("#addNewPublisherName").val();
			if(flag === 1){
				$('#addPickPublisher').append("<label class='radio-inline'><input type='radio' id='addPublisherId' name='addPublisherId' value="+response+">"+publisherName+"</label>");
			}else if(flag === 2){
				$('#addPickPublisher').append("<label class='radio-inline'><input type='radio' id='editPublisherId' name='editPublisherId' value="+response+">"+publisherName+"</label>");
			}
			publisherName: $("#addNewPublisherName").val("");
			publisherPhone: $('#addNewPublisherPhone').val("");
			publisherAddress: $('#addNewPublisherAddress').val("");
		},
		data: {
			publisherName: $("#addNewPublisherName").val(),
			publisherPhone: $('#addNewPublisherPhone').val(),
			publisherAddress: $('#addNewPublisherAddress').val()			
		}
	})
}

function updateBookGenres(flag){
	$.ajax({
		url: "addGenre",
		type: "POST",
		error: function (xhr,status,error){
			toastr["error"](error);
		},
		success: function(response){
			toastr["success"]("Successfully added the genre");
			var genreName = $("#addNewGenreName").val();
			if(flag === 1){
				$('#addPickGenres').append("<label class='checkbox-inline'><input type='checkbox' id='addGenreId' name='addGenreId' value="+response+">"+genreName+"</label>");
			}else if(flag === 2){
				$('#addPickGenres').append("<label class='checkbox-inline'><input type='checkbox' id='editGenreId' name='editGenreId' value="+response+">"+genreName+"</label>");
			}
			genreName: $("#addNewGenreName").val("");
		},
		data: {
			genreName: $("#addNewGenreName").val()		
		}
	})
}

function updateBookAuthors(flag){
	$.ajax({
		url: "addAuthor",
		type: "POST",
		error: function (xhr,status,error){
			toastr["error"](error);
		},
		success: function(response){
			toastr["success"]("Successfully added the author");
			var authorName = $("#addNewAuthorName").val();
			if(flag === 1){
				$('#addPickAuthors').append("<label class='checkbox-inline'><input type='checkbox' id='addAuthorId' name='addAuthorId' value="+response+">"+authorName+"</label>");
			}else if(flag === 2){
				$('#addPickAuthors').append("<label class='checkbox-inline'><input type='checkbox' id='editAuthorId' name='editAuthorId' value="+response+">"+authorName+"</label>");
			}
			authorName: $("#addNewAuthorName").val("");
		},
		data: {
			authorName: $("#addNewAuthorName").val()		
		}
	})
}

function getAddBook(){
	$.ajax({
		url: "addBook",
		type: "GET"
	}).done(function(response){
		$('#addBookModalBody').html(response);
	});
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
		<button class="btn btn-primary" data-toggle='modal' data-target='#addBookModal' onclick='getAddBook()'>Add Book</button>	
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
     		<div class="modal-body" id="addBookModalBody"></div>
     		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addBook()">Add</button>
      		</div>
		</div>
	</div>
</div>