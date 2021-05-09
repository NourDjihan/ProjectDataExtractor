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
									+ "(([A-Za-z].*\\s*)?(\\.\\s*)?(,\\s*)?(<\\s*)?(>\\s*)?(\\?\\s*)?)*\\s*"		
							+"\\{"
							);
							
		
		Path myProjectDirectory = Path.of("/Users/nour/Desktop/GitJavaProjects/SelectedProjects/elasticsearch/");
		Path newProjectDirectory = Path.of("/Users/nour/Desktop/GitJavaProjects/SelectedProjects/newElasticSearch/");
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
		System.out.println(
				"Original file contains: " 
						+ dataExtractor.filesOf(myProjectDirectory, ".java").size() +
				"New Files contains: " 
					+ dataExtractor.filesOf(newProjectDirectory, ".java").size()
		);
		/*
		 * To check if there are missing files, if so are they package info files
		 */
		var myProjectFileNames = dataExtractor.fileNamesOf(myProjectFiles);
		var newProjectFileNames = dataExtractor.fileNamesOf(newProjectFiles);
		List<String> differences = new ArrayList<String>(myProjectFileNames);
		String packageInfoFile = "package-info";
		Boolean areAllPackageInfoFiles = true;
		differences.removeAll(newProjectFileNames);
		if(differences.isEmpty()) {
			System.out.println("No missing files!");
		}else {
			System.out.println("Missing files of the same extension:"); 
			for(String difference : differences) {			
				System.out.println("\n" + difference);
				if(difference == packageInfoFile) { 
					areAllPackageInfoFiles = false;
					}
				
			}
			
		}		
		
		Assertions.assertEquals(true, areAllPackageInfoFiles);
	}

}
