
public abstract class FLDelm {
	private double size; 
	int foldercompsize =0;
	String path;
	 String name ; 
	 public String getPath() {
		return path;
	}
	public void setPath(String path) {

		this.path = path;
	}
	
	public double getSize() {
		return size; 
	} 
	public void setSize(double size) {
		this.size= size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void add(FLDelm FolderCcomponent) {
		throw new UnsupportedOperationException();
	}
	public void remove(FLDelm FolderCcomponent) {
		throw new UnsupportedOperationException();
	}
	public FLDelm getChild(int i ) {
		throw new UnsupportedOperationException();
	}
	
 
	
}
