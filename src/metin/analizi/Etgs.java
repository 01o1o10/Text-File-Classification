/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metin.analizi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.krb5.internal.rcache.AuthList;
import zemberek.core.logging.Log;
import zemberek.morphology.TurkishMorphology;
import zemberek.normalization.TurkishSpellChecker;

/**
 *
 * @author ilyas
 */
public class Etgs {

    static Map<String, List<Map>> gramTree = new HashMap<>();//gramların ağaç yapısı...
    static Map<String, Integer> totalGrams = new HashMap<>();//her gramın bütün datasetteki frekansını tutar.
    static Map<String, Map> categorizedGrams = new HashMap<>();//her bir gramın hangi kategoride kaç kez geçtiğini tutar.
    static Map<String, Map> ortalamaGrams = new HashMap<>();//hangi kategoride hangi gramın bir dosyada ortalama frekansını tutar.
    static Map<String, Integer> dosyaSayilari = new HashMap<>();//her bir kategorideki dosya sayisini tutar.
    static Map<String, List<Map>> testGrams = new HashMap<>();
    static String[] wordStops = {"üzere", "zaten", "zira", "ya", "ya da", "yani", "yerine", "yine", "yoksa", "var", "ve", "veya", "veyahut", "tabi", "tamam", "tüm", "tümü", "şayet", "şey", "şimdi", "şöyle", "şu", "şuna", "şunda", "şundan", "şunlar", "şunu", "şunun", "sana", "sen", "senden", "seni", "senin", "siz", "sizden", "size", "sizi", "sizin", "son", "sonra", "seobilog", "öbürü", "ön", "önce", "ötürü", "öyle", "ona", "ondan", "onlar", "onlara", "onlardan", "onların", "onu", "onun", "orada", "oysa", "oysaki", "nasıl", "ne", "ne kadar", "ne zaman", "neden", "nedir", "nerde", "nerede", "nereden", "nereye", "nesi", "neyse", "niçin", "niye", "madem", "mı", "mi", "mu", "mü", "kaç", "kadar", "kendi", "kendine", "kendini", "ki", "kim", "kime", "kimi", "kimin", "kimisi", "için", "içinde", "ile", "ise", "işte", "hangi", "hangisi", "hani", "hatta", "hem", "henüz", "hep", "hepsi", "hepsine", "hepsini", "her", "her biri", "herkes", "herkese", "herkesi", "hiç", "hiç kimse", "hiçbiri", "hiçbirine", "hiçbirini", "gene", "gibi", "fakat", "falan", "felan", "filan", "elbette", "en", "da", "daha", "de", "değil", "demek", "diğer", "diğeri", "diğerleri", "diye", "dolayı", "çoğu", "çoğuna", "çoğunu", "çok", "çünkü", "acaba", "ama", "ancak", "artık", "asla", "aslında", "az", "bana", "bazen", "bazı", "bazıları", "bazısı", "belki", "ben", "beni", "benim", "beş", "bile", "bir", "birçoğu", "birçok", "birçokları", "biri", "birisi", "birkaç", "birkaçı", "birşey", "birşeyi", "biz", "bize", "bizi", "bizim", "böyle", "böylece", "bu", "buna", "bunda", "bundan", "bunu", "bunun", "burada", "bütün"};

    public void correctDataAndLearn(File src) {
        FaF faf = new FaF();
        String doc = faf.readFile(src);
        for (String wordStop : wordStops) {
            doc = doc.replace(wordStop, "");
        }
        /*String[] words = doc.split(" ");
        try {
            TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
            TurkishSpellChecker spellChecker = new TurkishSpellChecker(morphology);

            System.out.println("Editing...");
            for (String word : words) {
                if(!spellChecker.check(word)){
                    List<String> possiblities = spellChecker.suggestForWord(word);
                    Log.info(word + " -> " + possiblities);
                    Log.info(word + " -> " + possiblities);
                    if (!(possiblities.size() == 0)) {
                        System.out.println("Dizi boş değil.");
                        System.out.println(possiblities.get(0));
                        doc = doc.replace(word, possiblities.get(0));
                    }
                    else{
                        System.out.println("Dizi boş.");
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Etgs.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        doc = doc.replaceAll("[.,;:…_?!-/\"'()]", "");
        doc = doc.replaceAll("  ", " ");
        doc = doc.replaceAll("   ", " ");
        doc = doc.toLowerCase();
        //faf.writeFile(src, doc);
        gramaj(doc, src);

    }

    static int j;

    public void gramaj(String doc, File src) {
        Map<String, Map> docGrams = new HashMap<>();
        Map<String, Integer> grams = new HashMap<>();

        String key;
        int i;

        for (i = 0; i < doc.length() - 3; i++) {
            key = doc.substring(i, i + 2);
            grams.put(key, (grams.containsKey(key)) ? grams.get(key) + 1 : 1);
            key = doc.substring(i, i + 3);
            grams.put(key, (grams.containsKey(key)) ? grams.get(key) + 1 : 1);
        }
        key = doc.substring(i, i + 2);
        grams.put(key, (grams.containsKey(key)) ? grams.get(key) + 1 : 1);

        docGrams.put(src.getAbsolutePath(), grams);

        String parentName = src.getParentFile().getName();
        if (gramTree.containsKey(parentName)) {
            List<Map> newList = gramTree.get(parentName);
            newList.add(docGrams);
            gramTree.put(parentName, newList);
        } else {
            List<Map> newList = new ArrayList();
            newList.add(docGrams);
            gramTree.put(parentName, newList);
        }//gramların ağaç yapısı oluşturulur ve biter. Elimizde sadece bu vardır
        j++;
        Etgs.dosyaSayilari.put(key.toString(), j);///burada da daha sonra kategori bazında hangi gramın hangi kategorideki dosyada ortalama frekansını elde etmek için dosya sayılarını alıyoruz
    }

    public static void scanGrams() {//gram ağacı dolaşılarak categorizedGrams, dosyaSayilari ve totalGrams oluşturulur.
        Set keys = gramTree.keySet();//kategori anahtarları alınıyor...
        keys.forEach((Object key) -> {//kategoriler içinde dolaşılıyor...
            Map<String, Integer> ng = new HashMap<>();
            System.out.println("Categori: " + key);
            @SuppressWarnings("element-type-mismatch")
            List<Map> docList = gramTree.get(key);//ilgili kategori altındaki dosyaların listesi alınıyor.
            //j = 0;
            docList.forEach((Map doc) -> {//bu dosyalar içinde dolaşılıyor
                Set docKeys = doc.keySet();
                docKeys.forEach((docKey) -> {
                    Map grams = (Map) doc.get(docKey);
                    Set kk = grams.keySet();
                    kk.forEach((k) -> {
                        Log.info(k);
                        ng.put(k.toString(), (ng.containsKey(k.toString())) ? ng.get(k.toString()) + 1 : 1);//Her kategoride hangi gramdan kaç adet olduğunu elde edebilmek için gramların kategori bazında frekansı tutuluyor. daha sonra bütün kategorileri tek çatıda birleştirmek üzere tek çatıya ekleniyor.
                        totalGrams.put(k.toString(), (totalGrams.containsKey(k.toString())) ? totalGrams.get(k.toString()) + 1 : 1);//sayısı  altında olan gramları tespit edebilmek için bütün gramlar bu map a eklenir.
                    });
                });

                j++;
                if (j > ((dosyaSayilari.get(key.toString()) * 75) / 100)) {
                    if (testGrams.containsKey(key.toString())) {
                        List<Map> newList = testGrams.get(key.toString());
                        newList.add(docList.get(j));
                        testGrams.put(key.toString(), newList);
                    } else {
                        List<Map> newList = new ArrayList();
                        newList.add(docList.get(j));
                        testGrams.put(key.toString(), newList);
                    }
                }
            });
            Etgs.categorizedGrams.put(key.toString(), ng);//tek çatı dediğimiz burası
        });
    }

    public static void deleteLowerPriority() {//categorizedGrams dolaşılarak tüm datasette frekansı 50 altında olan gramları siliyoruz ve her gramın ilgili kategorideki dosyalarda ortalama frekansını buluyoruz. 
        Set categories = categorizedGrams.keySet();//kategori anahtarlarını alıyoruz
        categories.forEach((Object categori) -> {//kategoriler içinde dolaşıyoruz
            Log.info("Categori: " + categori);
            Map grams = categorizedGrams.get(categori.toString());
            Map<String, Double> ortalama = new HashMap<>();//her bir kategorideki dosya sayisini tutar.
            Set gramKeys = grams.keySet();//bulunduğu kategorinin gramlarının anahtarlarını alıyoruz.
            gramKeys.forEach((Object gramKey) -> {//her gramı dolaşıyoruz...
                if (totalGrams.get(gramKey.toString()) > 50) {//eğer gramın frekansı tüm datasette 50 altında ise...
                    ortalama.put(gramKey.toString(), (totalGrams.get(gramKey.toString())));//kategoriye ait gram ortalamalarının saklanması...
                    System.out.println("Devam: " + ortalama.get(gramKey.toString()));
                } else {//50 altında değilse siliyoruz.
                    System.out.println("Tamam: " + totalGrams.get(gramKey.toString()));
                    //grams.remove(gramKey.toString());
                }
            });
            ortalamaGrams.put(categori.toString(), ortalama);//kategorilerin ortalamalarının birleştirilmesi
        });
        System.out.println("Bitti");
    }
    
    public static Map fileGramaj(File file) {
        FaF faf = new FaF();
        String doc = faf.readFile(file);
        Map<String, Integer> grams = new HashMap<>();

        String key;
        int i;

        for (i = 0; i < doc.length() - 3; i++) {
            key = doc.substring(i, i + 2);
            grams.put(key, (grams.containsKey(key)) ? grams.get(key) + 1 : 1);
            key = doc.substring(i, i + 3);
            grams.put(key, (grams.containsKey(key)) ? grams.get(key) + 1 : 1);
        }
        key = doc.substring(i, i + 2);
        grams.put(key, (grams.containsKey(key)) ? grams.get(key) + 1 : 1);
        return grams;
    }    
    
    public static String naiveBayse(Map fileGrams) {
        String categori = null;
        Map<String, Double> olasiliklar = new HashMap<>();
        
        Set catKeys = ortalamaGrams.keySet();
        catKeys.forEach((catKey) -> {
            Map grams = ortalamaGrams.get(catKey.toString());
            Set gramKeys = grams.keySet();
            
            olasiliklar.put(catKey.toString(), 0.2d);
            for(Object gramKey : gramKeys){
                //olasiliklar.put(categori, olasiliklar.get(catKey.toString()));
            }
        });
        
        return categori;
    }
}
