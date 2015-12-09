package kave;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ModelServiceTest {

	private ModelService2 sut;
	
	@Mock
	private IoUtils io;
	
	@Mock
	private ArgumentCaptor<File> fileCaptor;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		sut = new ModelService2(io);
		
		
		when(io.add(any(ModelDescriptor.class), fileCaptor.capture())).thenReturn(false);
	}
	
	private byte[] readBytes(File actualFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Test
	public void filesCanBeAdded() {
		byte[] expectedFileContent = new byte[0];
		UploadObject uploadObject = new UploadObject(new ModelDescriptor(), expectedFileContent);
		
		sut.add(uploadObject);
		
		File actualFile = fileCaptor.getValue();
		byte[] actual = readBytes(actualFile);
		assertArrayEquals(expectedFileContent, actual);

	}
}