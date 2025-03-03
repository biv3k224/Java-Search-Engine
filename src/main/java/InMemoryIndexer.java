import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryIndexer implements Indexer {
    private Map<String, List<DocumentFrequency>> index = new HashMap<>();

    @Override
    public void indexFiles(Map<String, String> files) {
        for (Map.Entry<String, String> entry : files.entrySet()) {
            String fileName = entry.getKey(); // Use the actual file name (e.g., "document1.txt")
            String content = entry.getValue();
            List<String> words = new SimpleTextProcessor().processText(content);
            Map<String, Integer> wordFrequency = new HashMap<>();
            
            // Calculate word frequencies in this file
            for (String word : words) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
            
            // Add to the index
            for (Map.Entry<String, Integer> freqEntry : wordFrequency.entrySet()) {
                String word = freqEntry.getKey();
                int frequency = freqEntry.getValue();
                index.computeIfAbsent(word, k -> new ArrayList<>())
                     .add(new DocumentFrequency(fileName, frequency));
            }
        }
    }

    @Override
    public Map<String, List<DocumentFrequency>> getIndex() {
        return index;
    }
}