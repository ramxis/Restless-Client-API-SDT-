package kave;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.json.JSONException;

import com.google.inject.Inject;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.MultiPart;

@JsonIgnoreProperties(ignoreUnknown = true)
@Path("/")
public class TestService {
	public static final String NO_SINGLE_UPLOAD = "Only a single file can be selected!";
	public static final String UPLOAD_FAILED = "The Uplaod Failed!";
	private  IoUtils io; //io being final might cause problems
	private final File dataFolder;
	private final File tmpFolder;
	// private final UploadCleanser cleanser;
	private final UniqueFileCreator tmpufc;
	private final UniqueFileCreator dataUfc;

	// checking the files if its zip and contains valid feed back
	// need to know what is a valid feedback file might need to delete or change
	// it!
	@Inject
	public TestService(File dataFolder, File tmpFolder, IoUtils io, UniqueFileCreator tmpufc, UniqueFileCreator dataUfc)
			throws IOException {
		this.dataFolder = dataFolder;
		// this.dataFolder = new File("E:\\Github\\Upload\\");
		this.tmpFolder = tmpFolder;
		// this.cleanser = cleanser;
		this.tmpufc = tmpufc;
		this.dataUfc = dataUfc;
		this.io = io;
		enforceFolders();
	}

	// creates tmp and data folders
	private void enforceFolders() throws IOException {
		if (!dataFolder.exists()) {
			// File CustomFolder = new File("E:\\Github\\Upload\\");
			FileUtils.forceMkdir(dataFolder);
		}
		if (!tmpFolder.exists()) {
			FileUtils.forceMkdir(tmpFolder);
		}
	}

	@GET
	@Produces(MediaType.TEXT_HTML + "; charset=utf-8")
	public Viewable index() {
		return new Viewable("/index.jsp");
	}

	private void moveToData(File tmpFile) throws IOException {
		File dataFile = dataUfc.createNextUniqueFile();
		FileUtils.moveFile(tmpFile, dataFile);
	}

	@POST
	@Path("/upload2")
	@Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	public Response receiveJSON(JsonNode json) throws JSONException, IOException {
		
		String file = json.findValue("file").toString();
		String file_content = file.substring(1, file.length() - 1);
		String fileName = json.findValue("file_name").toString();
		fileName = fileName.substring(1, fileName.length() - 1);
		String version = json.findValue("Version").toString();
		String response = convertFile(file_content, fileName, version);
		System.out.println("Json Was received\n");
		return Response.status(200).entity(response).build();

	}

	// Convert a Base64 string and create a file
	private String convertFile(String file_string, String file_name, String fileVersion) throws IOException {
		byte[] bytes = Base64.decode(file_string);
		ModelDescriptor fileDescriptor = new ModelDescriptor(file_name,fileVersion);
		io.add(fileDescriptor, bytes,dataFolder);
		return "true";
		//UploadObject fileObject = new UploadObject(fileDescriptor, bytes);
		/*
		 * String name = new SimpleDateFormat("HH-mm-ss").format(new Date());
		 * String fileName = file_name + "-" + name + ".zip"; File file = new
		 * File(dataFolder + "\\" + fileName); // File file = new
		 * File("local_path/"+file_name); FileOutputStream fop = new
		 * FileOutputStream(file);
		 * 
		 * fop.write(bytes); fop.flush(); fop.close(); return fileName +
		 * "uploaded to Server @ " + dataFolder.getPath();
		 */

	}

	// check
	@POST
	@Path("/upload")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)

	public Response upload2(InputStream fileInputStream) throws IOException {
		System.out.println("Upload 2 was called");

		// http://howtodoinjava.com/2015/08/05/jersey-file-upload-example/
		FileOutputStream outputStream = null;
		try {
			File f = tmpufc.createNextUniqueFile();
			outputStream = new FileOutputStream(f);

			IOUtils.copy(fileInputStream, outputStream);
			
			File purifiedFile = io.purify(f);// move
			moveToData(purifiedFile);
			// File purifiedFile = cleanser.purify(f);// move
			// moveToData(purifiedFile);

			String output = "File uploaded to : GITHub \n";
			Result.ok();
			return Response.status(200).entity(output).build();

		} finally {
			IOUtils.closeQuietly(outputStream);
		}

	}

	@GET
	@Path("/download")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFilebyQuery(@QueryParam("fname") String fileName) {
		return download(fileName);
	}

	@GET
	@Path("/download/{filename}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response downloadFilebyPath(@PathParam("filename") String fileName) {
		return download(fileName);
	}

	private Response download(String fileName) {
		System.out.println("Downlaod was called");
		// download url = http://127.0.0.1:8080/0.zip
		File file = new File(dataFolder.getPath() + "\\" + fileName);
		System.out.println(dataFolder.getPath());
		if (fileName == null || fileName.isEmpty()) {
			ResponseBuilder response = Response.status(Status.BAD_REQUEST);
			return response.build();
		}
		if (file.exists()) {
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "attachment; filename=" + file.getName());
			return response.build();
		} else {
			ResponseBuilder response = Response.status(404);
			response.header("FILE NOT FOUND: ", file.getName());

			return response.build();
		}

	}

	@DELETE
	@Path("/delete/{fname}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void remove(@PathParam("fname") String fileName) {
		try {

			System.out.println("DELETE was called");
			File file = new File(dataFolder.getPath() + "\\" + fileName);
			boolean filexists = file.exists();
			if (filexists) {
				if (file.delete()) {
					System.out.println(file.getName() + " is deleted!");
				} else {
					System.out.println("Delete operation is failed.");
				}
			} else {
				System.out.println("File does Not exist.");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/*public void add(UploadObject uploadObject) {

		File f = writeToTempFile(uploadObject.getBytes());

		io.add(uploadObject.getModelDesc(), f);
	}

	private File writeToTempFile(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
