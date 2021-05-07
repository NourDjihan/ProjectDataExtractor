package projectDataExtractor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractClassTest {

	@Test
	void test() throws IOException {
ProjectDataExtractor dataExtractor = new ProjectDataExtractor();
		/**
		 * We define here the regular expression	
		 */
		Pattern regexPattern 
			= Pattern.compile(
					"\\s*"
					+ "(package\\s+[a-zA-Z].*)\\s+"
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
		Path file = Path.of("/Users/nour/eclipse-workspace/MyFolderManipulator/testFiles/AbstractClass.java");
		String fileContent = Files.readString(file);
			
		String newFileContent 
				= dataExtractor
				.reduceFileContent(
						fileContent, 
						regexPattern,
						addedContent);
		
		String expectedContent = "\\**\n"
				+ "\n"
				+ "Abstract class definition \n"
				+ "\n"
				+ "*\\\n"
				+ "\n"
				+ "package abstract.class.definition;\n"
				+ "\n"
				+ "abstract class myAbstract {"
				+ "}";
		Assertions.assertEquals(expectedContent, newFileContent);
			
	}

}
