import java.util.Arrays;
import java.util.List;

public class SimpleTextProcessor implements TextProcessor{

	@Override

	public List<String> processText(String text){
	text = text.toLowerCase().replaceAll("[^\\w\\s]", "");
	return Arrays.asList(text.split("\\s+"));
	}
}
