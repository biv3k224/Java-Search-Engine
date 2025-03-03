import java.util.List;
import java.util.Map;

public interface FileReader {
    Map<String, String> readFiles(String directoryPath);
}

 interface TextProcessor {
    List<String> processText(String text);
}

 interface Indexer {
	 void indexFiles(Map<String, String> files);
	    Map<String, List<DocumentFrequency>> getIndex();
}

 interface Searcher {
    List<SearchResult> search(String query);
}

 interface UserInterface {
    void start();
}