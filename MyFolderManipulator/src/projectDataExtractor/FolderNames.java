package projectDataExtractor;

import java.io.File;

public class FolderNames {
public static void main (String[] args) {
	File folder = new File("/Users/nour/Desktop/newSelectedProjects/");
	File[] listOfFiles = folder.listFiles();
	  System.out.println("{");
	for (int i = 0; i < listOfFiles.length; i++) {
	  if (listOfFiles[i].isFile()) {
	    System.out.println("File " + listOfFiles[i].getName());
	  } else if (listOfFiles[i].isDirectory()) {
	    System.out.println("'"+ listOfFiles[i].getName()+"' . ");
	   
	  }
	}
	 System.out.println("}");

}
}
