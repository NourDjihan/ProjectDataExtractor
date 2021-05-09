package projectDataExtractor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NoClassDefinitionTest {

	@Test
	void test() throws IOException {
ProjectDataExtractor dataExtractor = new ProjectDataExtractor();
		
		
		Pattern regexPattern 
			= Pattern.compile(
					"\\s*package\\s*.*"
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
							


		String addedContent = "}";
		Path file = Path.of("/Users/nour/eclipse-workspace/MyFolderManipulator/testFiles/NoClassDefinition.java");
		String fileContent = Files.readString(file);
			
			Assertions.assertThrows(IllegalArgumentException.class, () -> {
				String newFileContent 
				= dataExtractor
				.reduceFileContent(
						fileContent, 
						regexPattern,
						addedContent);
			  });
		
	}

}
