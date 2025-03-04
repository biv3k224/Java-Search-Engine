import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GraphicalUserInterface implements UserInterface {
    private final FileReader fileReader;
    private final Indexer indexer;
    private final Searcher searcher;

    private JFrame frame;
    private JTextField pathField;
    private JTextField queryField;
    private JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    private JTable resultTable;
    private DefaultTableModel resultTableModel;
    private JTextPane contentPane;
    private Map<String, String> fileContents;

    public GraphicalUserInterface(FileReader fileReader, Indexer indexer, Searcher searcher) {
        this.fileReader = fileReader;
        this.indexer = indexer;
        this.searcher = searcher;
    }

    @Override
    public void start() {
        try {
            // Set a modern Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Mini Search Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the GUI on start
        frame.setLayout(new BorderLayout());

        // North Panel: Directory Path Input
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        northPanel.setBackground(new Color(230, 230, 250)); // Light purple background
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel pathLabel = new JLabel("Directory Path:");
        pathLabel.setForeground(new Color(75, 0, 130)); // Dark purple text
        pathField = new JTextField(20);
        pathField.setToolTipText("Enter the directory path containing your files");

        JButton loadButton = new JButton("Load Files");
        loadButton.setIcon(new ImageIcon("icons/load_icon.png")); // Add an icon
        loadButton.setBackground(new Color(147, 112, 219)); // Medium purple background
        loadButton.setForeground(Color.BLACK); // Black text
        loadButton.setFocusPainted(false);
        loadButton.setToolTipText("Load files from the specified directory");

        // Set a larger preferred size for the button
        loadButton.setPreferredSize(new Dimension(100, 30)); // Adjust width and height as needed

        northPanel.add(pathLabel);
        northPanel.add(pathField);
        northPanel.add(loadButton);

        // Center Panel: Split between File List and Content
        JSplitPane centerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        centerSplitPane.setResizeWeight(0.3); // 30% for file list, 70% for content

        // Left Side: File List
        JPanel fileListPanel = new JPanel(new BorderLayout());
        fileListPanel.setBackground(new Color(245, 245, 245)); // Light gray background
        fileListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setBackground(new Color(255, 255, 255)); // White background
        fileList.setSelectionBackground(new Color(147, 112, 219)); // Medium purple selection
        fileList.setSelectionForeground(Color.WHITE); // White text for selection

        JScrollPane fileScrollPane = new JScrollPane(fileList);
        fileListPanel.add(new JLabel("Available Files:"), BorderLayout.NORTH);
        fileListPanel.add(fileScrollPane, BorderLayout.CENTER);
        centerSplitPane.setLeftComponent(fileListPanel);

        // Right Side: Content Display
        contentPane = new JTextPane();
        contentPane.setEditable(false);
        contentPane.setBackground(new Color(255, 255, 255)); // White background
        JScrollPane contentScrollPane = new JScrollPane(contentPane);
        centerSplitPane.setRightComponent(contentScrollPane);

        // South Panel: Search Input and Results
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(230, 230, 250)); // Light purple background
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(230, 230, 250)); // Light purple background

        JLabel queryLabel = new JLabel("Search Query:");
        queryLabel.setForeground(new Color(75, 0, 130)); // Dark purple text
        queryField = new JTextField(20);
        queryField.setToolTipText("Enter your search query");

        JButton searchButton = new JButton("Search");
        searchButton.setIcon(new ImageIcon("icons/search_icon.png")); // Add an icon
        searchButton.setBackground(new Color(147, 112, 219)); // Medium purple background
        searchButton.setForeground(Color.BLACK); // White text
        searchButton.setFocusPainted(false);
        searchButton.setToolTipText("Search for files containing the query");
        searchButton.setPreferredSize(new Dimension(100, 30));

        searchPanel.add(queryLabel);
        searchPanel.add(queryField);
        searchPanel.add(searchButton);

        String[] columnNames = {"File Name", "Score"};
        resultTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(resultTableModel);
        resultTable.setBackground(new Color(255, 255, 255)); // White background
        resultTable.setSelectionBackground(new Color(147, 112, 219)); // Medium purple selection
        resultTable.setSelectionForeground(Color.WHITE); // White text for selection

        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        southPanel.add(searchPanel, BorderLayout.NORTH);
        southPanel.add(resultScrollPane, BorderLayout.CENTER);

        // Add panels to frame
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerSplitPane, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        // Action Listeners
        loadButton.addActionListener(e -> loadFiles());
        searchButton.addActionListener(e -> performSearch());
        resultTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = resultTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        String fileName = (String) resultTableModel.getValueAt(row, 0);
                        openFile(fileName);
                        displayFileContentWithHighlight(fileName, queryField.getText().trim());
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    private void loadFiles() {
        String directoryPath = pathField.getText().trim();
        if (directoryPath.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a directory path!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        fileContents = fileReader.readFiles(directoryPath);
        if (fileContents.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No .txt, .docx, or .pdf files found!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        indexer.indexFiles(fileContents);
        fileListModel.clear();
        for (String fileName : fileContents.keySet()) {
            fileListModel.addElement(fileName);
        }
        JOptionPane.showMessageDialog(frame, "Files indexed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void performSearch() {
        String query = queryField.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a search query!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<SearchResult> results = searcher.search(query);
        resultTableModel.setRowCount(0);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No matches found!", "Info", JOptionPane.INFORMATION_MESSAGE);
            contentPane.setText("");
        } else {
            for (SearchResult result : results) {
                resultTableModel.addRow(new Object[]{result.getDocumentName(), result.getScore()});
            }
        }
    }

    private void openFile(String fileName) {
        try {
            String directoryPath = pathField.getText().trim();
            File file = new File(directoryPath, fileName);
            if (!file.exists()) {
                JOptionPane.showMessageDialog(frame, "File not found at: " + file.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Desktop.isDesktopSupported()) {
                JOptionPane.showMessageDialog(frame, "Desktop API not supported!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Desktop.getDesktop().open(file);
            System.out.println("Opened file: " + file.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void displayFileContentWithHighlight(String fileName, String query) {
        if (fileContents == null || !fileContents.containsKey(fileName)) {
            contentPane.setText("File content not available.");
            return;
        }

        String content = fileContents.get(fileName);
        contentPane.setText(content);

        if (query.isEmpty()) return;

        StyledDocument doc = contentPane.getStyledDocument();
        SimpleAttributeSet highlight = new SimpleAttributeSet();
        StyleConstants.setBackground(highlight, Color.YELLOW);

        try {
            doc.setCharacterAttributes(0, content.length(), new SimpleAttributeSet(), true);
            String[] queryWords = query.toLowerCase().split("\\s+");
            for (String word : queryWords) {
                int pos = 0;
                while ((pos = content.toLowerCase().indexOf(word, pos)) >= 0) {
                    doc.setCharacterAttributes(pos, word.length(), highlight, false);
                    pos += word.length();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error highlighting text: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}