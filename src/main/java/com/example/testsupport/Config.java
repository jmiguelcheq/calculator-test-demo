package com.example.testsupport;
import java.io.IOException; import java.io.InputStream; import java.util.Properties;
public class Config {
	
    private static final Properties props = new Properties();
    
    static { 
    	try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) { if (in != null) props.load(in); } catch (IOException ignored) {} 
	}
    public static String calcUrl() {
        String sys = System.getProperty("calc.url"); if (sys != null && !sys.isBlank()) return sys;
        String env = System.getenv("CALC_URL"); if (env != null && !env.isBlank()) return env;
        String file = props.getProperty("calc.url"); if (file != null && !file.isBlank()) return file;
        
        return "https://jmiguelcheq.github.io/calculator-demo";
    }
    
    public static boolean headless() {
        String sys = System.getProperty("headless", System.getenv().getOrDefault("HEADLESS", "true"));
        
        return !"false".equalsIgnoreCase(sys);
    }
}