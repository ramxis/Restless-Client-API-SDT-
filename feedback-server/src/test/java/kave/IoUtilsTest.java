package kave;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class IoUtilsTest {

	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();
	@Rule
	public TemporaryFolder root = new TemporaryFolder();
	
	private IoUtils sut;

	@Before
	public void setup() {
		//sut = new IoUtils(root.getRoot());
	}

	//@Test
	/*public void correctFileNameIsCreated() throws IOException {
		File tmpFile = tmp.newFile("xyz.stg");
		ModelDescriptor md = new ModelDescriptor();
		md.name= "a/b/C";
		md.version = "123";
		
		sut.add(md, tmpFile);
		
		// compare file contents
	}*/
	
	@Test
	public void tmpFileIsDeleted() throws IOException {
		
	}
	
	@Test
	public void FileContentsAreNotChanged() throws IOException {
		
	}
	
	@Test
	public void correctIndexIsCreated() throws IOException {
		
	}

}