require([], function() {
	
	function cbFail(msg) {
		$.growl.error({
			"title":msg,
			"message":"",
			"location":"tl"
		})
	}
	
	function cbOk(msg) {
		$.growl.notice({
			"title":"The file has been successfully uploaded.",
			"message":"",
			"location":"tl"
		})
	}

	$("#submit-upload").removeAttr("disabled")
	$("#submit-upload").click(function(e) {
		e.preventDefault()
		e.stopPropagation()
		
		var confirmation = $("#confirm")[0]
		if (!confirmation.checked) {
			cbFail("Please agree to terms and conditions before uplaoding")
			return
		}
		
					
		var fileInput = $("#file")[0]
		var files = fileInput.files
		if (files.length == 0) {
			cbFail("No file has been selected for upload. Please Select a file.");
			return
		} else if (files.length > 1) {
			cbFail("Please select one file at a time.")
			return
		}
		
		// TODO start a spinner here, for upload may take some time

		var file = files[0]
		var data = new FormData()
		data.append("file", file)
		
		$.ajax({
			url: "./",
			type: "POST",
			data: data,
			cache: false,
			dataType: "json",
			processData: false,
			contentType: false
		}).done(function(r) {
			if (r.status == "OK") {
				cbOk("The file has been uploaded successfully.")
				$("#file-upload-form")[0].reset()
			} else {
				cbFail(r.message)
			}
		}).fail(function(r) {
			cbFail(r.message)
		})
	})
	$("#submit-upload2").removeAttr("disabled")
	$("#submit-upload2").click(function(e) {
		e.preventDefault()
		e.stopPropagation()
		
		var confirmation = $("#confirm2")[0]
		if (!confirmation.checked) {
			cbFail("Please agree to terms and conditions before uplaoding")
			return
		}
		
					
		var fileInput = $("#file2")[0]
		var files = fileInput.files
		if (files.length == 0) {
			cbFail("No file has been selected for upload. Please Select a file.");
			return
		} else if (files.length > 1) {
			cbFail("Please select one file at a time.")
			return
		}
		
		// TODO start a spinner here, for upload may take some time

		var file = files[0]
		var data = new FormData()
		data.append("file", file)
		
		$.ajax({
			url: "./",
			type: "POST",
			data: data,
			cache: false,
			dataType: "json",
			processData: false,
			contentType: false
		}).done(function(r) {
			if (r.status == "OK") {
				cbOk("The file has been uploaded successfully.")
				$("#file-upload-form2")[0].reset()
			} else {
				cbFail(r.message)
			}
		}).fail(function(r) {
			cbFail(r.message)
		})
	})
	
	
})
