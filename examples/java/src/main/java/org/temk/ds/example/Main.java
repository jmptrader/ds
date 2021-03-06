package org.temk.ds.example;

import java.util.Map;
import org.temk.ds.DataStorage;
import org.temk.ds.Type;

public class Main {
    public static void main1(String [] args) {
        try {
        DataStorage ds = new DataStorage("/tmp/ds");
        Map<String, String> m1 = ds.getMeta();
        Map<String, String> m2 = ds.getColumn("A").getMeta();
        
        ds.addMeta("xaxa1", "xoxo1");
        ds.getColumn("A").addMeta("xaxa2", "xoxo2");
        ds.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String [] args) {
        
        DataStorage ds = new DataStorage();
        double [] dc = new double [] {1,2,3,4,5,6,7,8,9,0};
        int    [] ic = new int [] {1,2,3,4,5,6,7,8,9,0};
        String [] sc = new String [] {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        
        try {
            ds.open("/tmp/storage", "rwct", 1024 * 1024);
            ds.addColumn(Type.STRING16, "s", 2);
            ds.addColumn(Type.INT32,    "i");
            ds.addColumn(Type.FLOAT64,  "d");
            
            long t1 = System.currentTimeMillis();
            for (int k = 0; k < 1; ++ k) {
               ds.getColumn("s").append(sc);
               ds.getColumn("i").append(ic );
               ds.getColumn("d").append(dc );
            }

            ds.flush();
            long t2 = System.currentTimeMillis();
            System.out.println("time write: " + (t2 - t1)/1000.0);

            long t3 = System.currentTimeMillis();
            String [] s0 = ds.getColumn("s").read(sc); 
            int    [] i0 = ds.getColumn("i").read(ic);
            double [] d0 = ds.getColumn("d").read(dc);
            
            long t4 = System.currentTimeMillis();
            System.out.println("time read: " + (t4 - t3)/1000.0);
            
            int [] indexes = new int[] { 0, 2, 4};
            
            String [] s1 = ds.getColumn("s").read(2, 2, (String [])null);
            String [] s2 = ds.getColumn("s").read(indexes, (String [])null);
            
            int [] i1 = ds.getColumn("i").read(2, 4, (int [])null);
            int [] i2 = ds.getColumn("i").read(indexes, (int [])null);
            
            double [] d1 = ds.getColumn("d").read(2, 4, (double [])null);
            double [] d2 = ds.getColumn("d").read(indexes, (double [])null);
            
            ds.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
