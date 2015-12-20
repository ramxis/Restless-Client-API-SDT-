package kave;

import java.io.File;
import java.io.FileOutputStream;

public class UploadObject {

	public UploadObject(ModelDescriptor modelDescriptor, byte[] bs) {
		// TODO Auto-generated constructor stub
		//check if the filedescriptor exists already
		
		//if not just copy it using ioUtils
		//if so change its version according to current time and date
		/*String name = new SimpleDateFormat("HH-mm-ss").format(new Date());
		String fileName = file_name + "-" + modelDescriptor.getname() + ".zip";
		File file = new File(dataFolder + "\\" + fileName);
		// File file = new File("local_path/"+file_name);
		FileOutputStream fop = new FileOutputStream(file);

		fop.write(bytes);
		fop.flush();
		fop.close();*/
	}

	public ModelDescriptor getModelDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] getBytes() {
		// TODO Auto-generated method stub
		return null;
	}
	
}