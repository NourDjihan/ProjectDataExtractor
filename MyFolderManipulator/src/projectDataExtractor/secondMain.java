package projectDataExtractor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;



public class secondMain {
	
	private static List<String> fileNamesOf(List<Path> files){
		List<String> fileNames = new ArrayList<String>();
		for(Path file : files) {
			fileNames.add(file.getFileName().toString());
		}
		return fileNames;
	}
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		ProjectDataExtractor dataExtractor = new ProjectDataExtractor();
		Pattern regexPattern 
			= Pattern.compile(
					"\\s*"
							+ "((public|private|final)\\s+)?"
									+ "(abstract\\s+)?"
									+ "(class"
									+ "|"
									+ "@?interface"
									+ "|"
									+ "enum)"
								+ "\\s+"
									+ "(([A-Za-z].*\\s*)?(\\.\\s*)?(,\\s*)?(<\\s*)?(>\\s*)?(\\?\\s*)?)*\\s*"		
							+"\\{"
							);
							

		Path myProjectDirectory = Path.of("/Users/nour/Desktop/GitJavaProjects/SelectedProjects/guice/");
		Path newProjectDirectory = Path.of("/Users/nour/Desktop/GitJavaProjects/SelectedProjects/newGuice/");
		String extension = ".java";
		String addedContent = "}";
		dataExtractor
			.reduceProjectContent(
					myProjectDirectory, 
					newProjectDirectory,
					extension,
					regexPattern,
					addedContent);
		List<Path> myProjectFiles = dataExtractor.filesOf(myProjectDirectory, extension);
		List<Path> newProjectFiles = dataExtractor.filesOf(newProjectDirectory, extension);
		var myProjectFileNames = fileNamesOf(myProjectFiles);
		var newProjectFileNames = fileNamesOf(newProjectFiles);

		List<String> differences = new ArrayList<String>(myProjectFileNames);
		differences.removeAll(newProjectFileNames);
		if(differences.isEmpty()) {
			System.out.println("No missing files!");
		}else {
			System.out.println("Missing files of the same extension:"); 
			for(String difference : differences)				
				System.out.println("\n" + difference);	
			System.out.println("Expected: " + myProjectFiles.size() + " But got: "+ newProjectFiles.size());
			
		}
		/*
		 * To check if there are missing files, if so are they package info files
		 */
		String packageInfoFile = "package-info";
		Boolean areAllPackageInfoFiles = false;
		differences.removeAll(newProjectFileNames);
		if(differences.isEmpty()) {
			System.out.println("No missing files!");
		}else {
			System.out.println("Missing files of the same extension:"); 
			for(String difference : differences) {			
				System.out.println("\n" + difference);
				if(difference != packageInfoFile) { 
					areAllPackageInfoFiles = false;
					}
				
			}
			
		}		
		if
		System.out.println("The rest is package-info files.");
		
	}
}