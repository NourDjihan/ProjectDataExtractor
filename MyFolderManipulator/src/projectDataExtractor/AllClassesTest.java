package projectDataExtractor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AllClassesTest {

	@Test
	void test() throws IllegalArgumentException, IOException {
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
									+ "([^(\\{\\s*)]*\\s*)*");
		
		Path myProjectDirectory = Path.of("/Users/nour/Desktop/GitJavaProjects/SelectedProjects/flink/");
		Path newProjectDirectory = Path.of("/Users/nour/Desktop/GitJavaProjects/SelectedProjects/newFlink/");
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
//		System.out.println(
//				"Original file contains: " 
//						+ dataExtractor.filesOf(myProjectDirectory, ".java").size() +
//				"New Files contains: " 
//					+ dataExtractor.filesOf(newProjectDirectory, ".java").size()
//		);
		/*
		 * To check if there are missing files, if so are they package info files
		*/
		var myProjectFileNames = dataExtractor.fileNamesOf(myProjectFiles);
		var newProjectFileNames = dataExtractor.fileNamesOf(newProjectFiles);
		List<String> differences = new ArrayList<String>(myProjectFileNames);
		differences.removeAll(newProjectFileNames);
		
		String packageInfoFile = "package-info.java";
		Boolean areAllPackageInfoFiles = true;
		
		if(differences.isEmpty()) {
			System.out.println("No missing files!");
		}else {
			for(String difference : differences) {	
				System.out.print(difference + "\n");
				if(!difference.equals(packageInfoFile)) { 
					areAllPackageInfoFiles = false;
					}	
			}	
		}
		System.out.println("Expected: " + myProjectFiles.size() + " But got: "+ newProjectFiles.size());
		if(areAllPackageInfoFiles) {
			System.out.println("Missing files are package-info files.");
			}
		Assertions.assertEquals(true, areAllPackageInfoFiles);
	}

}
