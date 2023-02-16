import java.nio.file.Path;
import java.util.ArrayList;

//node 
public class FLD_C extends FLDelm {
	ArrayList<FLDelm> Foldercomponent= new ArrayList<FLDelm>();
	private boolean hasNext=false;
	
	public FLD_C(String name,Path p) {
		setName(name);
		if(p !=null)
		setPath(p.toFile().getAbsolutePath().toString());}
	
	public void add(FLDelm f) {
		hasNext=true;
		
		if(f instanceof FLD_C) {Foldercomponent.add((FLD_C)f);}
		
		
		else if(f instanceof File_C) {Foldercomponent.add((File_C)f);}
		
		foldercompsize=foldercompsize+1;}
	
	
	public void remove(FLDelm FolderCcomponent) {
		if(hasNext) {
			foldercompsize=foldercompsize-1;
		Foldercomponent.remove(FolderCcomponent);}}
	
	
	
	public FLDelm getChild(int i ) {return Foldercomponent.get(i);}
	

	public boolean isHasNext() {return hasNext;}
	
	public void setHasNext(boolean hasNext) {this.hasNext = hasNext;}
	
	public double getSize() {
		double sum = 0 ;
		for(int i = 0 ;i<Foldercomponent.size();i++) {sum=sum+ Foldercomponent.get(i).getSize();}return sum;}

	
}
