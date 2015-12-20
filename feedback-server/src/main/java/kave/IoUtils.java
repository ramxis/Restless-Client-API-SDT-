package kave;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipException;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

public class IoUtils {
	public static final String EMPTY_FILE = "The file is empty or contains no valid feedback.";
	public static final String NO_ZIP = "No valid zip file.";
	private ObjectMapper mapper;

	private UniqueFileCreator ufc;

	public IoUtils(UniqueFileCreator ufc) {
		this.ufc = ufc;
		mapper = new ObjectMapper();
		mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);

	}

	public File purify(File in) throws IOException {
		File out = ufc.createNextUniqueFile();

		try {

			FileUtils.copyFile(in, out);
		} catch (ZipException e) {
			FileUtils.deleteQuietly(in);
			FileUtils.deleteQuietly(out);
			throw new KaVEException(NO_ZIP);
		} catch (KaVEException ke) {
			FileUtils.deleteQuietly(in);
			FileUtils.deleteQuietly(out);
			throw ke;
		} finally {
			// closeQuietly(zfin);
			// closeQuietly(in);
		}

		return out;
	}

	public boolean add(ModelDescriptor modelDesc, byte[] filebyte, File dataFolder) throws IOException {
		// TODO Auto-generated method stub
		String filename = modelDesc.getname();
		String fileversion = modelDesc.getversion();
		String newVersion = new SimpleDateFormat("HH-mm-ss").format(new Date());
		String fileName = filename + "-" + fileversion + ".zip";
		File file = new File(dataFolder + "\\" + fileName);
		// File file = new File("local_path/"+file_name);
		FileOutputStream fop = new FileOutputStream(file);

		fop.write(filebyte);
		fop.flush();
		fop.close();
		return true;
	}
	/*
	 * public IoUtils(File rootFolder) {
	 * 
	 * }
	 */

	/*
	 * public boolean add(ModelDescriptor modelDesc, File f) { // TODO
	 * Auto-generated method stub String filename = modelDesc.getname(); String
	 * fileversion = modelDesc.getversion(); return false; }
	 */

	// public remove(modeDescritor)
	// public list modelDescriptot getall()

}
