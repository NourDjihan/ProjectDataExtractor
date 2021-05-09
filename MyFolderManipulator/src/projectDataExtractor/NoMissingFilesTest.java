package projectDataExtractor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Testing if all the files with the same extension have been treated
 * @author nour
 *
 */
class NoMissingFilesTest {

	
	@Test
	public void Test() throws IllegalArgumentException, IOException {
		/**
		 * In this part the parameters are initialized to be received by the data extractor and 
		 * applied on the project files
		 */
		ProjectDataExtractor dataExtractor = new ProjectDataExtractor();
		
		/**
		 * Take only classes and enums no interfaces
		 */
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
		
		/**
		 * In this part file names from both new and old project are extracted to be compared 
		 * and test if a file is missing accoring to their size
		 */
		List<Path> myProjectFiles = dataExtractor.filesOf(myProjectDirectory, extension);
		List<Path> newProjectFiles = dataExtractor.filesOf(newProjectDirectory, extension);
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
