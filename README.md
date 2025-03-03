project:
  name: "Mini Search Engine"
  description: "A Java-powered search engine that indexes .txt, .docx, and .pdf files, ranking them based on frequency-based scoring. Built with SOLID principles."
  features:
    - File Support: "Indexes .txt, .docx, and .pdf files."
    - Smart Search: "Case-insensitive keyword search with ranked results."
    - SOLID Design: "Modular, maintainable, and extensible code."
    - Actual File Names: "Displays real file names from the directory."

prerequisites:
  java: "Java 8+ (Tested on Java 1.8)"
  maven: "Required for dependency management."
  git: "Needed to clone the repository."

setup:
  steps:
    - step: "Clone the Repository"
      command: |
        git clone https://github.com/your-username/mini-search-engine.git
        cd mini-search-engine
    - step: "Compile the Project"
      command: |
        mvn clean compile
    - step: "Run the Engine"
      command: |
        mvn exec:java

usage:
  preparation:
    description: "Place .txt, .docx, and .pdf files in a directory."
  execution:
    directory_input: "Enter the full directory path."
    search: "Enter a search term to find matching files."
  example_output:
    - file: "report.pdf"
      score: 2.0
    - file: "notes.txt"
      score: 1.0
    - file: "doc.docx"
      score: 1.0

project_structure:
  root: "mini-search-engine/"
  directories:
    - src/
    - src/main/java/
    - src/resources/
  files:
    - pom.xml: "Maven configuration."
    - README.md: "Documentation."
    - Main.java: "Entry point."
    - DirectoryFileReader.java: "Reads supported file types."
    - InMemoryIndexer.java: "Indexes words from files."

troubleshooting:
  errors:
    - issue: "ClassNotFoundException: Main"
      solution: "Ensure Main.java is in src/main/java/. Run mvn clean compile."
    - issue: "Log4j Warning"
      solution: "Ensure log4j-core dependency is in pom.xml and rebuild."
    - issue: "No Results"
      solution: "Check directory path and supported file extensions."

dependencies:
  - name: "Apache PDFBox"
    version: "2.0.27"
    purpose: "Parsing .pdf files."
  - name: "Apache POI"
    version: "5.2.3"
    purpose: "Reading .docx files."
  - name: "Log4j 2"
    version: "2.17.1"
    purpose: "Logging."

customization:
  add_more_file_types:
    description: "Modify DirectoryFileReader to support additional formats."
  modify_scoring:
    description: "Tweak FrequencyBasedSearcher for custom ranking algorithms."
  enhance_logging:
    description: "Add a log4j2.xml configuration in src/main/resources/."

credits:
  built_with: "Java and Maven"
  author: "Your Name"
  repository: "https://github.com/your-username/mini-search-engine"
