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

class noMissingFilesTest {

	
	private List<String> fileNamesOf(List<Path> files){
		List<String> fileNames = new ArrayList<String>();
		for(Path file : files) {
			fileNames.add(file.getFileName().toString());
		}
		return fileNames;
	}
	@Test
	public void Test() throws IllegalArgumentException, IOException {
		/**
		 * In this part the parameters are initialized to be received by the data extractor and 
		 * applied on the project files
		 */
		ProjectDataExtractor dataExtractor = new ProjectDataExtractor();
		
		
		Pattern regexPattern 
			= Pattern.compile(
					"\\s*"
							+ "((public|private|final)\\s+)?"
								+ "("
									+ "(abstract\\s+)?"
									+ "(class"
									+ "|"
									+ "@?interface"
									+ "|"
									+ "enum)"
								+ ")\\s+" 
								+ "[a-zA-Z].*(<([a-zA-Z].*) (,\\s*([a-zA-Z].*))*>)?\\s*"
									+ "("
									+ "(extends\\s+)"
									+ "[a-zA-Z].*(<([a-zA-Z].*) (,\\s*([a-zA-Z].*))*>)?\\s*"
									+ ")?"
									+ "(\\s+)?"
									+ "("
									+ "(implements\\s+)" 
									+ "[a-zA-Z].*(<([a-zA-Z].*) (,\\s*([a-zA-Z].*))*>)?\\s*"
									+ ")*"
								+ "(\\s*)" /** The space at the end is optional, developers sometimes miss the space */				
							+"\\{"
							);

		Path myProjectDirectory = Path.of("/Users/nour/eclipse-workspace/MyFolderManipulator/testFiles/");
		Path newProjectDirectory = Path.of("/Users/nour/eclipse-workspace/MyFolderManipulator/testsResult/");
		String extension = ".java";
		String addedContent = "/n" + "}";
		dataExtractor
			.reduceProjectContent(
					myProjectDirectory, 
					newProjectDirectory,
					extension,
					regexPattern,
					addedContent);
		
		/**
		 * In this part file names from both new and old project are extracted to be compared 
		 * and test if a file is missing
		 */
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
			
		}		
		
		Assertions.assertEquals(myProjectFileNames.size(), newProjectFileNames.size());
	

}
}
