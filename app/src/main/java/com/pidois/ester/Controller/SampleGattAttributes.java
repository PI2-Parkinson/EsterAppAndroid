package com.pidois.ester.Controller;
import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
   // public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    public static String ESTER_SERVICE = "c96d9bcc-f3b8-442e-b634-d546e4835f64";
    public static String ESTER_CHARACTERISTIC = "807b8bad-a892-4ff7-b8bc-83a644742f9b";


    static {
        // Sample Services.
        attributes.put("c96d9bcc-f3b8-442e-b634-d546e4835f64", "TOPZERA SERVICE");
//        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
//        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("807b8bad-a892-4ff7-b8bc-83a644742f9b", "ENVIA ESSE CARAI!");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
