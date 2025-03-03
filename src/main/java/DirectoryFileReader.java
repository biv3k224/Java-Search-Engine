import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DirectoryFileReader implements FileReader {
    @Override
    public Map<String, String> readFiles(String directoryPath) {
        Map<String, String> fileContents = new HashMap<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath), "{*.docx,*.pdf,*.txt}")) {
            for (Path file : stream) {
                String fileName = file.getFileName().toString().toLowerCase();
                String content = "";

                if (fileName.endsWith(".txt")) {
                    content = new String(Files.readAllBytes(file));
                } else if (fileName.endsWith(".pdf")) {
                    try (PDDocument document = PDDocument.load(file.toFile())) {
                        PDFTextStripper stripper = new PDFTextStripper();
                        content = stripper.getText(document);
                    }
                } else if (fileName.endsWith(".docx")) {
                    try (XWPFDocument doc = new XWPFDocument(Files.newInputStream(file))) {
                        StringBuilder sb = new StringBuilder();
                        for (XWPFParagraph para : doc.getParagraphs()) {
                            sb.append(para.getText()).append("\n");
                        }
                        content = sb.toString();
                    }
                }

                if (!content.isEmpty()) {
                    fileContents.put(fileName, content);
                } else {
                    System.err.println("No content extracted from: " + fileName);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
        return fileContents;
    }
}