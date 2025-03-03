
public class DocumentFrequency {
	private final String documentName;
	private final int frequency;
	
	public DocumentFrequency(String documentName, int frequency) {
		this.documentName = documentName;
		this.frequency = frequency;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	
	public int getFrequency() {
		return frequency;
	}

}
