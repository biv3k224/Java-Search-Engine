public class Main {
    public static void main(String[] args) {
        FileReader fileReader = new DirectoryFileReader();
        Indexer indexer = new InMemoryIndexer();
        Searcher searcher = new FrequencyBasedSearcher(indexer);
        UserInterface ui = new GraphicalUserInterface(fileReader, indexer, searcher);
        ui.start();
    }
}