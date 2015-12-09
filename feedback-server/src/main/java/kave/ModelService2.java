package kave;

import java.io.File;

public class ModelService2 {
	
	private IoUtils io;

	public ModelService2(IoUtils io){
		this.io = io;
		
	}
	
	public void add(UploadObject uploadObject) {
		
		
		File f = writeToTempFile(uploadObject.getBytes());
		
		io.add(uploadObject.getModelDesc(), f);
	}

	private File writeToTempFile(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

}