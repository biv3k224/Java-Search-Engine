import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequencyBasedSearcher implements Searcher {

    private final Indexer indexer;

    public FrequencyBasedSearcher(Indexer indexer) {
        this.indexer = indexer;
    }

    @Override
    public List<SearchResult> search(String query) {
        // Split the query into individual words
        String[] queryWords = query.split("\\s+");

        // Initialize a map to store cumulative scores for each document
        Map<String, Double> documentScores = new HashMap<>();

        // Process each word in the query
        for (String word : queryWords) {
            // Get the list of DocumentFrequency objects for the current word
            List<DocumentFrequency> documents = indexer.getIndex().get(word);
            if (documents != null) {
                for (DocumentFrequency docFreq : documents) {
                    String docName = docFreq.getDocumentName();
                    // Update the score by adding the word's frequency in the document
                    double currentScore = documentScores.getOrDefault(docName, 0.0);
                    documentScores.put(docName, currentScore + docFreq.getFrequency());
                }
            }
        }

        // Create a list of SearchResult objects from the document scores
        List<SearchResult> results = new ArrayList<>();
        for (Map.Entry<String, Double> entry : documentScores.entrySet()) {
            results.add(new SearchResult(entry.getKey(), entry.getValue()));
        }

        // Sort the results by score in descending order
        results.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        // Return the sorted list
        return results;
    }
}