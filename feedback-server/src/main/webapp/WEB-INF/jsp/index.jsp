<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="js/jquery-2.0.3.js"></script>
<script type="text/javascript" src="js/require-2.1.10.js"
	data-main="js/index.js"></script>
<script type="text/javascript" src="js/growl/jquery.growl.js"></script>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/growl/jquery.growl.css" />
<link rel="stylesheet" type="text/css" href="css/feedback.css" />
<title>Feedback Hochladen</title>
</head>
<body>
	<noscript>
		<b>WARNUNG:</b> Der Upload ben&ouml;tigt JavaScript.
	</noscript>

	<div id="content">
		<div id="header">
			<a href="http://kave.cc" title="KaVE-Projekt-Webseite"> <img
				alt="KaVE-Projekt" src="images/kave_logo_with_title.png" />
			</a>
		</div>
		<h1>Model Upload</h1>

		<p>This is a test project for FileUpload using Jersey Restful Services &ldquo;Jersey Restful Upload&rdquo; .</p>

		<form id="file-upload-form" enctype="multipart/form-data">
			<input type="file" id="file" name="file" /><br /> <input
				type="checkbox" id="confirm" />
			<div id="hint">
				Click the check box and and than press the upload button please.
			</div>
			<button id="submit-upload" disabled="disabled">Upload</button>
		</form>
		<form id="file-upload-form2" enctype="multipart/form-data">
			<input type="file" id="file2" name="file2" /><br /> <input
				type="checkbox" id="confirm2" />
			<div id="hint">
				Click the check box and and than press the upload button please.
			</div>
			<button id="submit-upload2" disabled="disabled">Upload</button>
		
		</form>
		<form id="file-download-form" action="/download/" method="GET">  
 		<p><label for="name">File Name:</label>  
   		<input type="text" name="fname" size="50">   
 		</p>  
 		<input type="submit" value="Download File" />  
		</form>
		
		
		<hr />
		<dl id="footer" class="cf">
			<dt>Projektleiter:</dt>
			<dd>
				<a href="mailto:proksch@st.informatik.tu-darmstadt.de">Sebastian
					Proksch</a> (TU Darmstadt)
			</dd>
			<dt>Allgemeiner Ansprechpartner:</dt>
			<dd>
				<a href="mailto:andreas-chr.fischer@datev.de">Andreas Fischer</a>
				(DATEV)
			</dd>
			<dt>Administrativer Ansprechpartner:</dt>
			<dd>
				<a href="mailto:martin.kutter@datev.de">Martin Kutter</a> (DATEV)
			</dd>
			<dt>Informationen zum KaVE-Projekt:</dt>
			<dd>
				<a href="http://www3.bk.datev.de/eprtl/dyn.ica?www.kave.cc"
					title="KaVE-Projekt-Webseite">KaVE Projekt Webseite</a>
			</dd>
		</dl>
	</div>
</body>
</html>
