/**
 * Copyright 2014 Technische Universität Darmstadt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kave;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
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

import com.google.inject.Inject;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.multipart.MultiPart;

@Path("/")
public class FeedbackService {

	public static final String NO_SINGLE_UPLOAD = "Only a single file can be selected!";
    public static final String UPLOAD_FAILED = "The Uplaod Failed!";


    private final File dataFolder;
    private final File tmpFolder;
    private final UploadCleanser cleanser;
    private final UniqueFileCreator tmpufc;
    private final UniqueFileCreator dataUfc;

    @Inject
    public FeedbackService(File dataFolder, File tmpFolder, UploadCleanser cleanser, UniqueFileCreator tmpufc,
            UniqueFileCreator dataUfc) throws IOException {
    	this.dataFolder = dataFolder;
        //this.dataFolder = new File("E:\\Github\\Upload\\");
        this.tmpFolder = tmpFolder;
        this.cleanser = cleanser;
        this.tmpufc = tmpufc;
        this.dataUfc = dataUfc;
        enforceFolders();
    }

 // creates tmp and data folders
    private void enforceFolders() throws IOException {
        if (!dataFolder.exists()) {
        	//File CustomFolder = new File("E:\\Github\\Upload\\");
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
// upload
   /* @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    //@Path("upload1")
   // @Produces(MediaType.APPLICATION_JSON)
    public Result upload(MultiPart data) {
    	System.out.println("Upload 1 was called");
    	try {
            if (!isSingleFileUpload(data)) {
                return Result.fail(NO_SINGLE_UPLOAD);
            }

            File tmpFile = storeTmp(data);
            File purifiedFile = cleanser.purify(tmpFile);

            moveToData(purifiedFile);

            return Result.ok();
        } catch (KaVEException e) {
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            return Result.fail(UPLOAD_FAILED);
        }
    }*/

    private boolean isSingleFileUpload(MultiPart data) {
        int numParts = data.getBodyParts().size();
        return numParts == 1;
    }

    private File storeTmp(MultiPart data) throws IOException {
        FileOutputStream outputStream = null;
        try {
            BodyPart bp = data.getBodyParts().get(0);
            BodyPartEntity bpe = (BodyPartEntity) bp.getEntity();

            File f = tmpufc.createNextUniqueFile();
            outputStream = new FileOutputStream(f);
            IOUtils.copy(bpe.getInputStream(), outputStream);

            return f;
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

	private void moveToData(File tmpFile) throws IOException {
        File dataFile = dataUfc.createNextUniqueFile();
        FileUtils.moveFile(tmpFile, dataFile);
    }
	
	
	
	
	
	//check
	@POST
	@Path("upload")
	//@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	  // @Produces(MediaType.APPLICATION_JSON)
		 //public Result upload2( byte[] fileContentMultiPart) {
	 
	public Result upload2(@FormDataParam("file") InputStream fileInputStream) throws IOException 
	{
		// fileContent = .readFileToByteArray(File file)
		 System.out.println("Upload 2 was called");
		 
		 //http://howtodoinjava.com/2015/08/05/jersey-file-upload-example/
		 FileOutputStream outputStream = null;
		 try
		 {
			 File f = tmpufc.createNextUniqueFile();
			 outputStream = new FileOutputStream(f);
			 //byte[] fileContentMultiPart = new byte[1024];
			 
			 IOUtils.copy(fileInputStream,outputStream);
			 File purifiedFile = cleanser.purify(f);//move
			 moveToData(purifiedFile);

	            return Result.ok();
		 }finally {
	            IOUtils.closeQuietly(outputStream);
	        }
		 
		
	 }
	//private static final String TXT_FILE = "e:\\Github\\Upload\\0.zip";
	  @GET
	  @Path("/download")
	  @Produces(MediaType.APPLICATION_OCTET_STREAM)
	  public Response downloadFilebyQuery(@QueryParam("fname") String fileName) {
	    return download(fileName);
	  }
	   
	  @GET
	  @Path("/download/{filename}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response downloadFilebyPath(@PathParam("filename")  String fileName) {
	    return download(fileName);
	  }
	 
	//@GET
	//@Path("/download/{fname}")
	//@Produces({"application/zip"})
	//@Produces(MediaType.APPLICATION_OCTET_STREAM)
	//private Response downloadFile(@PathParam("fname") String fileName)
	  private Response download(String fileName) 
	  {
		
		System.out.println("Downlaod was called");
		//download url = http://127.0.0.1:8080/0.zip
		File file = new File(dataFolder.getPath()+"\\"+ fileName);
		System.out.println(dataFolder.getPath());
		
		 
		        if(fileName == null || fileName.isEmpty())
		        {
		        	ResponseBuilder response = Response.status(Status.BAD_REQUEST);
		            return response.build();
		        }
		        if (file.exists()) 
		        {
		            //ResponseBuilder builder = Response.ok(file);
		            //builder.header("Content-Disposition", "attachment; filename=" + file.getName());
		        	ResponseBuilder response = Response.ok((Object) file);
		     		response.header("Content-Disposition", "attachment; filename=" + file.getName());
				    return response.build();
		        } 
		        else 
		        {
		            		             
		        	//Response response = Response.status(404).entity("FILE NOT FOUND: " + file.getAbsolutePath()).type("application/zip").build();
		        	//return response;
		        	ResponseBuilder response = Response.status(404);	             
		        	response.header("FILE NOT FOUND: ", file.getName());
		        	
		        	return response.build();
		        }
		            
		       
	}
	  //ask-why @HEAD and DELTE are not working
	  	@GET
	  	//@DELETE
		@Path("/delete/{fname}")
	  	@Consumes(MediaType.APPLICATION_JSON)
	  	@Produces(MediaType.APPLICATION_JSON)
		public void remove(@PathParam("fname") String fileName)
		{
	  		try
	  		{
	    		
	    		
	    		System.out.println("DELETE was called");
	    		//download url = http://127.0.0.1:8080/0.zip
	    		File file = new File(dataFolder.getPath()+"\\"+ fileName);
	    		boolean filexists = file.exists();
	    		if (filexists)
	    		{
		    		if(file.delete())
		    		{
		    			System.out.println(file.getName() + " is deleted!");
		    		}
		    		else
		    		{
		    			System.out.println("Delete operation is failed.");
		    		}
	    		}
	    		else
	    		{
	    			System.out.println("File does Not exist.");
	    		}
		    	   
	    	}
	  		catch(Exception e)
	  		{
	    		
	    		e.printStackTrace();
	    		
	    	}
	    	
	    
		}
	  
	  //@DELETE
	  //@Consumes(MediaType.APPLICATION_JSON)
	
	
	 //check
	 //@POST
	// public void remove(ModelDescriptor model){}
	
	
}