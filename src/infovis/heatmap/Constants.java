package infovis.heatmap;

import java.util.HashMap;

public class Constants {
	
	static final HashMap<String, String> mainNamesCol;
    static
    {
    	mainNamesCol = new HashMap<String, String>();
    	mainNamesCol.put("MH_E", "Migrant Bg.");
    	mainNamesCol.put("E_A", "Foreigners");
    	mainNamesCol.put("E_E", "Everyone");
    	
    	// Age groups
    	mainNamesCol.put("MH_U1", "Age < 1");
    	mainNamesCol.put("E_AU1", "Age < 1");
    	mainNamesCol.put("E_U1", "Age < 1");
    	
    	mainNamesCol.put("MH_1U6", "1-6");
    	mainNamesCol.put("E_A1U6", "1-6");
    	mainNamesCol.put("E_1U6", "1-6");
    	
    	mainNamesCol.put("MH_6U15", "6-15");
    	mainNamesCol.put("E_A6U15", "6-15");
    	mainNamesCol.put("E_6U15", "6-15");
    	
    	mainNamesCol.put("MH_15U18", "15-18");
    	mainNamesCol.put("E_A15U18", "15-18");
    	mainNamesCol.put("E_15U18", "15-18");
    	
    	mainNamesCol.put("MH_18U25", "18-25");
    	mainNamesCol.put("E_A18U25", "18-25");
    	mainNamesCol.put("E_18U25", "18-25");
    	
    	mainNamesCol.put("MH_25U55", "25-55");
    	mainNamesCol.put("E_A25U55", "25-55");
    	mainNamesCol.put("E_25U55", "25-55");
    	
    	mainNamesCol.put("MH_25U55", "25-55");
    	mainNamesCol.put("E_A25U55", "25-55");
    	mainNamesCol.put("E_25U55", "25-55");
    	
    	mainNamesCol.put("MH_55U65", "55-65");
    	mainNamesCol.put("E_A55U65", "55-65");
    	mainNamesCol.put("E_55U65", "55-65");
    	
    	mainNamesCol.put("MH_65U80", "65-80");
    	mainNamesCol.put("E_A65U80", "65-80");
    	mainNamesCol.put("E_65U80", "65-80");
    	
    	mainNamesCol.put("MH_80U110", "80-110");
    	mainNamesCol.put("E_A80U110", "80-110");
    	mainNamesCol.put("E_80U110", "80-110");
    	
    	// Migration background regions
    	mainNamesCol.put("HK_EU15", "EU15");
    	mainNamesCol.put("HK_EU27", "EU27");
    	mainNamesCol.put("HK_EU28", "EU28");
    	mainNamesCol.put("HK_Polen", "Poland");
    	mainNamesCol.put("HK_EheJug", "Yugoslavia");
    	mainNamesCol.put("HK_EheSU", "USSR");
    	mainNamesCol.put("HK_Turk", "Turkey");
    	mainNamesCol.put("HK_Arab", "Arab States");
    	mainNamesCol.put("HK_Sonst", "Others");
    	mainNamesCol.put("HK_NZOrd", "Unspecified");
    }
    
	static String[] mainNamesRow = {"Mitte", "Friedrichshain", "Pankow", "Charlottenburg", "Spandau", "Steglitz","Tempelhof", "Neukoelln", "Treptow", "Marzahn", "Lichtenberg", "Reinickendorf"};
}
