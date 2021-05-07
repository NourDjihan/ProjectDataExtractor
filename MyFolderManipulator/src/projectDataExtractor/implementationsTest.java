package projectDataExtractor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class implementationsTest {

	@Test
	void test() throws IOException {
ProjectDataExtractor dataExtractor = new ProjectDataExtractor();
		
/**
 * We define here the regular expression	
 */
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

		String addedContent = "}";
		Path file = Path.of("/Users/nour/eclipse-workspace/MyFolderManipulator/testFiles/moreThanOneImplementation");
		String fileContent = Files.readString(file);
		String newFileContent 
			= dataExtractor
			.reduceFileContent(
					fileContent, 
					regexPattern,
					addedContent);
		String expectedContent = "package blah;\n"
				+ "class BlahBlah implements Blah implements Bouhou<T,N,More>{}";
		System.out.println(newFileContent);
		
		Assertions.assertEquals(expectedContent, newFileContent);
		
	}

}
