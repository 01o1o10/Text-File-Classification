package metin.analizi;

import java.io.BufferedReader;
import javax.swing.JFileChooser;
import java.io.File; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FafEvents {
    public String chooseFile() throws FileNotFoundException, IOException{
        String filePath = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(fileChooser);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + filePath);
        }
        return filePath;
    }
    
    public String readFile(String filePath){
        String doc = "", line;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fileReader);
            while ((line = br.readLine()) != null) {
                doc += line;
            }
        } catch (IOException e) {}
        return doc;
    }
}
