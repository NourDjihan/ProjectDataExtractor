package projectDataExtractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * DataExtractor is a simple project which extract the necessary data following
 * a given regular expression
 * @param projectDirectory where the initial project is placed
 * @param newProjectDirectory where the new project is going to be placed
 * @param the extension of the files to be manipulated
 * @param Pattern with the regular expression to be compiled
 * @param String of the added content
 * @author Nour
 */
public class ProjectDataExtractor {

	/**
	 * 
	 * @param content
	 * @param pattern
	 * @param addedContent
	 * @return new content of the file
	 * @throws IllegalArgumentException
	 */
	 String reduceFileContent(String content, Pattern pattern, String addedContent) throws IllegalArgumentException {
        int end = endFileIndex(content, pattern);
        return content.substring(0, end) + addedContent;
    }
	 /**
	  * 
	  * @param files
	  * @return a list of file names
	  */
	 public List<String> fileNamesOf(List<Path> files){
			List<String> fileNames = new ArrayList<String>();
			for(Path file : files) {
				fileNames.add(file.getFileName().toString());
			}
			return fileNames;
		}
	 /**
	  * This methods is responsible for extracting files with a given extension 
	  * @param projectDirectory
	  * @param extension
	  * @return list of files (paths)
	  */
	public List<Path> filesOf(Path projectDirectory, String extension) {
        List<Path> files = null;

        try (var walk = Files.walk(projectDirectory)) {
            files 
            	= walk
                    .filter(path -> !Files.isDirectory(path))
                    .filter(path -> path.toString().toLowerCase().endsWith(extension))
                    .collect(Collectors.toList());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return files;
    }
	
	/**
	 * @param content
	 * @param pattern
	 * @return regular expression end index which is an integer
	 * @throws IllegalArgumentException
	 */
	private int endFileIndex(String content, Pattern pattern) throws IllegalArgumentException {
            		 
        Matcher matcher = pattern.matcher(content);
       
        if (!matcher.find())
            throw new IllegalArgumentException("pattern not found");
        return matcher.end();
    }
	
	
	/**
	 * This method is responsible for reducing the files of a given extension of a certain
	 * project according to a defined regular expression
	 * @param myProjectDirectory
	 * @param newProjectDirectory
	 * @param extension
	 * @param pattern (the regular expression)
	 * @param added content
	 * 
	 */
	public void reduceProjectContent(Path myProjectDirectory, Path newProjectDirectory, String extension, Pattern pattern, String addedContent) throws IOException, IllegalArgumentException {
        if (!Files.isDirectory(myProjectDirectory) || !Files.isDirectory(newProjectDirectory)) {
            throw new IllegalArgumentException("Path must be a directory");
        }

        var myFiles = filesOf(myProjectDirectory, extension);

        for (Path myFile : myFiles) {
            String content = Files.readString(myFile);
            String newContent = "";

            try {
            	newContent = reduceFileContent(content, pattern, addedContent);
            }
            catch (IllegalArgumentException exception) {
               System.out.println(myFile + " No such pattern");
            }

            Path relativePath = myProjectDirectory.relativize(myFile);
            Path outputFile = newProjectDirectory.resolve(relativePath);
            
            if(newContent != "") {
            Files.createDirectories(outputFile.getParent());
            Files.write(outputFile, newContent.getBytes());
        }
        }
    }

}
