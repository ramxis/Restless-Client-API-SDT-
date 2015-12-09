package kave;

import java.io.File;
import java.util.Set;
//check
public interface IModelService {
	//public Set<ModelDescriptor> getModels(); // index.json.gz -- publicly available
	
	//public File download(ModelDescriptor md); // download <<server url>>/path/from/descriptor-<version>.zip -- publicly available
	
	//public void upload(ModelDescriptor md, File f); // available with simple pw check, maintains folder and index file
	
	//public void remove(ModelDescriptor md); // available with simple pw check, maintains folder and index file
}

/*
[
    {
      // model descriptor ...
    },
    {
      ..
    }
]


*/