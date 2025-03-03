import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoryFileReader implements FileReader{

	@Override
	public Map<String, String> readFiles(String directoryPath){
		Map<String, String> fileContents = new HashMap<>();
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath), "*.txt")){
			for(Path file : stream) {
				String fileName = file.getFileName().toString();
				String content = new String(Files.readAllBytes(file));
				fileContents.put(fileName, content);
			}
		}
		catch (IOException e) {
			System.out.println("Error reading files: "+ e.getMessage());
		}
		return fileContents;
	}
}
