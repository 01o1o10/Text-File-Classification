package metin.analizi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FaF {
   
    String category;
    
    static FaF[] files;
    
    public Etgs etgs = new Etgs();
    
    public File selectFile() {
        File file = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/home/ilyas/Downloads/1150haber/raw_texts/"));
        /*new File(System.getProperty("user.home"))*/
        int result = fileChooser.showOpenDialog(fileChooser);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + file.getAbsolutePath());
        }
        return file;
    }

    public File selectFolder() {
        File folder = null;
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("/home/ilyas/Downloads/1150haber/raw_texts/")); // start at application current directory
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        ui form = new ui();
        int returnVal = fc.showSaveDialog(form);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            folder = fc.getSelectedFile();
            System.out.println("Selected folder: " + folder.getPath());
        }
        return folder;

    }

    public String readFile(File file) {
        String doc = "", line;
        try {
            BufferedReader br;
            try (FileReader fr = new FileReader(file)) {
                br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    doc += line;
                }
                fr.close();
            }
            br.close();
        } catch (IOException e) {
        }
        return doc;
    }

    public void writeFile(File file, String doc) {
        FileWriter fw;
        try {
            fw = new FileWriter(file, false);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(doc);
                bw.close();
            }
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(FaF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void scanSrc(File src) {
        if (src.isFile()) {
            //System.out.println("src is file.");
            etgs.correctDataAndLearn(src);
        } else if (src.isDirectory()) {
            //System.out.println("src is dir.");
            for (String childName : src.list()) {
                //System.out.println(src.getAbsolutePath() + "/" + childName);
                File childSrc = new File(src.getAbsoluteFile() + "/" + childName);
                if (childSrc.isFile()) {
                    System.out.println(childSrc);
                    //System.out.println("childSrc is file.");
                    etgs.correctDataAndLearn(childSrc);
                } else if (childSrc.isDirectory()) {
                    //System.out.println("childSrc is dir.");
                    scanSrc(childSrc);
                }
            }
        }
    }
}
