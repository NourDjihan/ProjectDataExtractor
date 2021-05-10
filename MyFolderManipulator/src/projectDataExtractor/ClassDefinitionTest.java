package projectDataExtractor;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassDefinitionTest {

	@Test
	void test() throws IOException {
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
							

		String addedContent = "{}";
		Path file = Path.of("/Users/nour/eclipse-workspace/MyFolderManipulator/testFiles/AbstractClassExtendsImplements.java");
		String fileContent = Files.readString(file);
		String newFileContent 
			= dataExtractor
			.reduceFileContent(
					fileContent, 
					regexPattern,
					addedContent);
String expectedContent = "/*\n"
		+ " * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one\n"
		+ " * or more contributor license agreements. Licensed under the Elastic License\n"
		+ " * 2.0 and the Server Side Public License, v 1; you may not use this file except\n"
		+ " * in compliance with, at your election, the Elastic License 2.0 or the Server\n"
		+ " * Side Public License, v 1.\n"
		+ " */\n"
		+ "\n"
		+ "package org.elasticsearch.index.reindex;\n"
		+ "/**\n"
		+ " * Any comment\n"
		+ " */\n"
		+ "public abstract class AbstractAsyncBulkByScrollActionMetadataTestCase<\n"
		+ "                Request extends AbstractBulkByScrollRequest<Request>,\n"
		+ "                Response extends BulkByScrollResponse>\n"
		+ "        implements AsyncBulkByScrollActionImpl<Request, Response> {}";
		System.out.println(newFileContent);
		
		Assertions.assertEquals(expectedContent, newFileContent);
		

	}

	

}
