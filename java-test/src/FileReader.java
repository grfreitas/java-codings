import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileReader {

    private String fileName;
    public List<String> fileData = new ArrayList<String>();

    public void readFileName() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the file name: ");
        String fileName = sc.nextLine();

        this.fileName = fileName;

        sc.close();
    }

    public static List<String> readFile(String[] args) {

        FileReader fileReader = new FileReader();
        fileReader.readFileName();

        try {
            File inputFile = new File(fileReader.fileName);
            Scanner file = new Scanner(inputFile);

            while (file.hasNextLine()) {
                fileReader.fileData.add(file.nextLine());
            }

            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Coudn't read file.");
            e.printStackTrace();
        }
        return fileReader.fileData;
    }
}
