import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommandLineInterface implements UserInterface {
    private final FileReader fileReader;
    private final Indexer indexer;
    private final Searcher searcher;

    public CommandLineInterface(FileReader fileReader, Indexer indexer, Searcher searcher) {
        this.fileReader = fileReader;
        this.indexer = indexer;
        this.searcher = searcher;
    }

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter directory path to index (.docx, .pdf, and .txt files): ");
        String directoryPath = scanner.nextLine();
        Map<String, String> files = fileReader.readFiles(directoryPath); // Changed to Map
        indexer.indexFiles(files);
        System.out.println("Indexing complete.");

        while (true) {
            System.out.println("Enter search query (or 'exit' to quit): ");
            String query = scanner.nextLine();
            if ("exit".equalsIgnoreCase(query)) {
                break;
            }
            List<SearchResult> results = searcher.search(query);
            if (results.isEmpty()) {
                System.out.println("No results found!!");
            } else {
                System.out.println("Results: ");
                for (int i = 0; i < results.size(); i++) {
                    SearchResult result = results.get(i);
                    System.out.println((i + 1) + ". " + result.getDocumentName() + " (score: " + result.getScore() + ")");
                }
            }
        }
        scanner.close();
    }
}