import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class FolderMain extends Application{
		Button button1=new Button("Select") ;
		TextArea selecteddir = new TextArea();
		TreeView<String> treeView = new TreeView<>();
		FLD_C rootFolder=new FLD_C("nothing",null);
		TreeItem<String> tree;
		String path;
		
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primarystage) throws Exception {
		try {
			treeView.setOnMouseClicked(e->treeViewClicked(treeView));
		} catch (Exception e1) {

		}	
		// scene control
		Pane pane = new Pane( ); 
		treeView.setMinHeight(400);
		treeView.setMinWidth(100);
		pane.getChildren().add(treeView);
		pane.getChildren().add(selecteddir);
		pane.getChildren().add(button1);
		button1.setLayoutX(0);
		button1.setLayoutY(400);
		button1.setMinWidth(250);
		selecteddir.setMinHeight(500);
		selecteddir.setMinWidth(300);
		selecteddir.setLayoutX(400);
		selecteddir.setLayoutY(0);
		pane.setStyle("-fx-background-color: #A6E0FF");

		button1.setOnMouseClicked(e->buttonclicked(e));
		
		Scene scene = new Scene(pane,1000,600);
		primarystage.setScene(scene);
		primarystage.setResizable(true);
		primarystage.show();
		
	}
	
	
	
	public void buttonclicked(MouseEvent e) {
		if(e.getSource().equals(button1)) {
			DirectoryChooser filechooser = new DirectoryChooser();
		
			File response = filechooser.showDialog(null);	
			
			
					
			try {
				String Name =  response.getName();
				path=response.getAbsolutePath();
				rootFolder.setName(Name);
				rootFolder.setPath(path);
				rootFolder.setSize((int)(response.length()));	
				createTreeView(path);} 
			catch (Exception e1) {
				return;}}
		
	}
	public void label(String s) {
		selecteddir.setText(s);
	} 
	
    private void createTreeView(String dbPath) {
    
         tree = new TreeItem<>(dbPath.substring(dbPath.lastIndexOf(File.separator)+1));
        List<TreeItem<String>> dirs = new ArrayList<>();
        List<TreeItem<String>> files = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dbPath))) {
            for(Path path: directoryStream) {
                if (Files.isDirectory(path)) {
                	File temp = path.toFile();     	
                	FLD_C folder = new FLD_C(temp.getName(),path);	
                	TreeItem<String> subDirectory = new TreeItem<>(path.toFile().getName());  //here 
                    getSubLeafs(path, subDirectory,folder);
                    dirs.add(subDirectory);
                    rootFolder.add(folder);
                } else {
                	rootFolder.add(getLeafsComp(path));                                    
                }
            }
            
            tree.getChildren().addAll(dirs);
            tree.getChildren().addAll(files);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tree.setExpanded(true);
        treeView.setRoot(tree);
        treeView.setShowRoot(true);
 
        
    }
    
    private File_C getLeafsComp(Path f) {
    	File_C temp = new File_C(f.toFile().getName(),getExtension(f.toFile()),f.toFile().length(),f);
		return temp;  	
    }

    private void getSubLeafs(Path subPath, TreeItem<String> parent,FLD_C folder) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(subPath.toString()))) {
            for(Path subDir: directoryStream) {
                // explicit search for files because we dont want to get sub-sub-directories
                if (!Files.isDirectory(subDir)) {
                    String subTree = subDir.toString();
                    folder.add(new File_C(subTree.substring(1 + subTree.lastIndexOf(File.separator)),getExtension(subDir.toFile()),(double)subDir.toFile().length()*0.001,subDir));

                }
                else if(Files.isDirectory(subDir)){
                	String subTree = subDir.toString();
                	FLD_C temp = new FLD_C(subTree.substring(1 + subTree.lastIndexOf(File.separator)),subDir);
                	TreeItem<String> subDirectory = new TreeItem<>(subDir.toFile().getName());
                	getSubLeafs(subDir, subDirectory,temp); //recursive
                	folder.add(temp);
                	parent.getChildren().add(subDirectory);
                	
                }
              
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	//-----------------------------------------------------------------------//
	private String getExtension(File a ) {
		String filename = a.getName();
		int index = filename.lastIndexOf('.');
	    if(index > 0) {
	      String extension = filename.substring(index + 1);
	      return extension;
	    }
	    return "";
	    
	}

	private void treeViewClicked(TreeView<String> e) {
		
		String selection="";
		try {
			selection = e.getSelectionModel().getSelectedItem().toString();} 
		catch (Exception e1) {}
			

		if(selection!= "") {
			selection = selection.substring(selection.indexOf(':')+2, selection.length()-2);


			FLDelm labelcontent = findItem(selection);
		    if(labelcontent!=null){PrepareLabel((FLD_C)labelcontent);}}}
	
	//functions 
	public FLDelm findItem(String item) {
		if(rootFolder.getName().equals(item)) {return rootFolder;}
		else {return	searchFolder(rootFolder,item);}}
	

	public void PrepareLabel(FLD_C f) {String s = f.getName()+" ("+f.getSize()+" KB)"; label(getLabel(f, s+"\n","    "));}
	
	
	
	
	public FLD_C searchFolder(FLD_C Folder,String s){	
				
		for(int i = 0; i <Folder.Foldercomponent.size();i++) {	
			
			
			
			FLDelm currentF = Folder.Foldercomponent.get(i);
			if(currentF instanceof FLD_C) {
				if(currentF.getName().equals(s)){ 
					return (FLD_C) currentF; }
				FLD_C findChild = searchFolder((FLD_C)currentF,s);
				if( findChild!=null) { return findChild; }}}
		
		
		return null;}
	
	
	
	public String getLabel(FLDelm f,String theString,String Tabs) {
		FLD_C file = (FLD_C)f;
		for(int i = 0; i< file.Foldercomponent.size();i++) {FLDelm fChild =file.Foldercomponent.get(i);
			
			
			if(fChild instanceof FLD_C) {
					
			theString=theString+Tabs+fChild.getName()+" ("+fChild.getSize()+" KB)\n";
					
			theString=getLabel(fChild, theString, Tabs+"    ");}
				
			else {theString=theString+Tabs+"-"+fChild.getName()+"."+((File_C) fChild).getExtension()+" ("+fChild.getSize()+" KB)\n";}}return theString;}}

