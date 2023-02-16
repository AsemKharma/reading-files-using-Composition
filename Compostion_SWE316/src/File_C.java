import java.nio.file.Path;

//leaf 
public class File_C extends FLDelm {
	private String extension;
	
	
	
	public String getExtension() {return extension;}
	
	
	public File_C(String name ,String extension, double size,Path p ) {
		
		setName(name);
		
		this.extension = extension;
		
		setSize(size);setPath(p.toFile().getAbsolutePath().toString());}
	
	
	public File_C(String name , String extension,Path p ) {this(name, extension, 0,p);}
	
	public File_C() {this(null,null,0,null);}
	
	public void setExtension(String extension) {this.extension = extension;}

	public String getName() {return super.getName();}
	
	public void setName(String name) {this.name=name;}
}
