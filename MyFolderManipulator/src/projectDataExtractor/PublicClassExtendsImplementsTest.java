package projectDataExtractor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PublicClassExtendsImplementsTest {

	@Test
	void test() throws IOException {
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

		String addedContent = "}";
		Path file = Path.of("/Users/nour/eclipse-workspace/MyFolderManipulator/testFiles/publicClassExtendsImplements2.java");
		String fileContent = Files.readString(file);
		String newFileContent 
			= dataExtractor
			.reduceFileContent(
					fileContent, 
					regexPattern,
					addedContent);
		String expectedContent = "package blah;\n"
				+ "\n"
				+ "public \n"
				+ "	class BlahBlah \n"
				+ "		extends Blah \n"
				+ "			implements Bouhou implements <T, N> implements <A>{}";
				
		System.out.println(newFileContent);
		
		Assertions.assertEquals(expectedContent, newFileContent);
		
	
	}

}
