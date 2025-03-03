# Mini Search Engine 🚀

Welcome to the **Mini Search Engine**—a sleek, Java-powered tool that turns your `.txt`, `.docx`, and `.pdf` files into a searchable treasure trove! Whether you're digging through notes, reports, or docs, this bad boy indexes your files and ranks them by relevance with a frequency-based scoring system. Built with **SOLID principles**, it’s modular, extensible, and ready to rock your document-searching world. Let’s get started! 🎉  

---

## Features ✨
- **File Support**: Indexes `.txt`, `.docx`, and `.pdf` files like a pro.  
- **Smart Search**: Case-insensitive keyword search with ranked results based on word frequency.  
- **SOLID Design**: Clean, maintainable code that’s easy to tweak or expand.  
- **Actual File Names**: Shows real file names from your directory—no generic placeholders here!  

---

## Prerequisites 🛠️
Before you dive in, make sure you’ve got:  
- **Java 8+**: The engine runs on Java (we used 1.8 in the build).  
- **Maven**: For dependency management and building the project. [Install it here](https://maven.apache.org/download.cgi).  
- **Git**: To clone this repo. [Grab it here](https://git-scm.com/downloads) if you don’t have it.  

---

## Getting Started 🔥  

### 1. Clone the Repository  
Bring this powerhouse to your machine with a quick Git clone:  

```bash
git clone https://github.com/your-username/mini-search-engine.git
cd mini-search-engine
2. Check Out the Goodies
Inside, you’ll find:

bash
Copy
Edit
mini-search-engine/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Main.java                # The entry point
│   │   │   ├── DirectoryFileReader.java # Reads .txt, .docx, .pdf
│   │   │   ├── InMemoryIndexer.java     # Builds the index
│   │   │   ├── ... (other files)
│   └── resources/                        # Optional logging config
├── pom.xml                               # Maven magic
└── README.md                             # This file!
3. Compile the Project
Fire up Maven to fetch dependencies (Apache POI, PDFBox, Log4j) and compile the code:

bash
Copy
Edit
mvn clean compile
This pulls in everything you need and builds the project into target/classes.
If you see [INFO] BUILD SUCCESS, you’re golden! 🌟

4. Run the Engine
Launch the search engine and start exploring your files:

bash
Copy
Edit
mvn exec:java
Maven runs the Main class automatically (thanks to the exec-maven-plugin config).

How to Use It 🎮
1. Prep Your Files
Drop some .txt, .docx, and .pdf files into a directory (e.g., C:\docs or /home/user/docs).

Example files:
notes.txt: "Java rules!"
report.pdf: "Java Java code"
doc.docx: "Java programming"
2. Enter the Directory Path
When prompted, type the full path to your directory (e.g., C:\docs) and hit Enter.
Watch it index your files—boom, "Indexing complete!"

3. Search Away
Type a query (e.g., "java") and press Enter.

Example Output:
markdown
Copy
Edit
Results:
1. report.pdf (score: 2.0)
2. notes.txt (score: 1.0)
3. doc.docx (score: 1.0)
Keep searching or type exit to quit.

Dependencies 🧰
This project leans on some heavy hitters:

Apache PDFBox (2.0.27): For cracking open .pdf files.
Apache POI (5.2.3): For parsing .docx goodness.
Log4j 2 (2.17.1): For smooth logging (no more SimpleLogger warnings!).
All managed via Maven—check pom.xml for the full scoop.

Troubleshooting ⚡
"ClassNotFoundException: Main"?

Ensure Main.java is in src/main/java/. Run mvn clean compile again.
Log4j Warning?

The log4j-core dependency in pom.xml fixes this. Rebuild with mvn clean compile.
No Results?

Double-check your directory path and file extensions (only .txt, .docx, .pdf supported).
Wanna Hack It? 🛠️
Add More File Types: Tweak DirectoryFileReader’s glob pattern (e.g., "{*.docx,*.pdf,*.txt,*.md}") and add parsing logic.
Fancy Scoring: Play with FrequencyBasedSearcher to tweak how scores are calculated.
Better Logs: Drop a log4j2.xml in src/main/resources/ for custom logging vibes.
Built With ❤️
Crafted as a core Java project for your resume—show off those SOLID skills! Questions? Hit me up in the issues tab. Now go search some docs like a boss! 😎
