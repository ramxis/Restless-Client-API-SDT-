package kave;
//check
public class ModelDescriptor {
	private  String name;
	private String version;
	
	public ModelDescriptor(String name, String version)
	{
		this.name = name;
		this.version = version;
	}
	public String getname()
	{
		return this.name;
	}
	public String getversion()
	{
		return this.version;
	}
	
	
}
