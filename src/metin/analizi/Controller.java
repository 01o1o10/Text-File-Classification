/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metin.analizi;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ilyas
 */
public class Controller {
    
    static File src;
    FaF faf = new FaF();
    static Mysql sql = new Mysql();
    
    public void chooseFile(){
        src = faf.selectFile();
    }
    
    public void chooseFolder(){
        src = faf.selectFolder();
    }
    
    public void ScanDataAndLearn(){
        System.out.println("scanSrc ye gidecek.");
        faf.scanSrc(src);
        System.out.println("Edited all files!");
        Etgs.scanGrams();
        Etgs.deleteLowerPriority();
    }
    
    public void findClass(){
        Map grams = Etgs.fileGramaj(src);
        String categori = Etgs.naiveBayse(grams);
    }
}
