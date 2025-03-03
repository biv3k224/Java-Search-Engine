
public class SearchResult {

	private  String documentName;
	private  double score;
	
	public SearchResult(String documentName, double score) {
		this.documentName = documentName;
		this.score = score;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	
	public double getScore() {
		return score;
	}
}
