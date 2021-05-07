package projectDataExtractor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;



public class Main {
	
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

		Path myProjectDirectory = Path.of("/Users/nour/Desktop/GitJavaProjects/SelectedProjects/elasticSearch/");
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

	}
}