package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
 private static Properties prop;
 public static Properties initProperties(){
     prop=new Properties(); try{
         FileInputStream fis=new FileInputStream("config.properties");
         prop.load(fis);


     } catch (IOException e) {
System.out.println("unable to load config.properties file" +e.getMessage());     }
     return prop;
 }

 public static  String getProperty(String key){
     if (prop==null){initProperties();}
     return prop.getProperty(key);
 }
}
