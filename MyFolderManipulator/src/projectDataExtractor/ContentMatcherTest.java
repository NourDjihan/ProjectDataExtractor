package projectDataExtractor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ContentMatcherTest {

	@Test
	void test() throws IOException {
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
								+ "([^(\\{\\s*)]*\\s*)*"
						);
						
	Boolean match = true;
	
	String content = Files.readString(Path.of("/Users/nour/eclipse-workspace/MyFolderManipulator/testFiles/ContentTest.java"));
//	String content = 
// "/**
//	 * 
//	 * Class Definition
//	 */
//
//	 package blah;
//	 public abstract class BlahBlah extends Bouh implements <N, J>, A {
//
//	    Any other text
//	 }"
   Matcher matcher = regexPattern.matcher(content);
   
   if (!matcher.find()) {
	   match = false;
	   System.out.println(matcher.end());
       throw new IllegalArgumentException("pattern not found");    
       
   }
   Assertions.assertEquals(match, true);
  
   
   
	}

}
