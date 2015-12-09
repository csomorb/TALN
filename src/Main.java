

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import corpus.Corpus;
import corpus.Text;
import dictionary.Dictionary;
import dictionary.Sense;


public class Main {
	
	public static void main(String[] args) throws FileNotFoundException{
		Dictionary d1 = new Dictionary();
		d1.loadDictionary("src/Dict-Lesk.xml");
	/*	Dictionary d2 = new Dictionary();
		d2.loadDictionary("src/Dict-Lesk-etendu.xml");
		Dictionary d3 = new Dictionary();
		d3.loadDictionary("src/dict_all_stopwords_stemming_semcor_dso_wordnetglosstag_150");
		System.out.println("Taille de Disk-Lesk: "+d1.size()+"\nTaille de Disk-lesk-etendu: "+d2.size()+"\nTaille du troisi�me: "+d3.size());
		
		ArrayList<Sense> listeSensCone = d1.getSenses("cone");
		System.out.println("Nombre de sens du mot cone: "+listeSensCone.size());
		for (int i = 0; i < listeSensCone.size(); i++)
			System.out.println(listeSensCone.get(i));
		
		ArrayList<Sense> listeSensPine = d1.getSenses("pine");
		System.out.println("Nombre de sens du mot pine: "+listeSensPine.size());
		for (int i = 0; i < listeSensPine.size(); i++)
			System.out.println(listeSensPine.get(i));
		System.out.println("Similirat� Lesk avec le dico Disk-Lesk: "+ d1.getSimilarity("cone", "pine")); 
	*//*	System.out.println("Similirat� Lesk avec le dico Disk-Lesk-etendu: "+ d2.getSimilarity("cone", "pine"));
		System.out.println("Similirat� Lesk avec le dico 3: "+ d3.getSimilarity("cone", "pine"));
	*/	
		double[] vect1 = {1.,4.,8.,12.,2.,5.,4.};
		double[] vect2 = {4,2,1,6,6,10,4};
		System.out.println("La cor�lation est de: "+Stats.getPearsonCorrelation(vect1,vect2));
		
		System.out.println("nombre de sens: "+nbCombinaisons("go",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("mouse pilot computer",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("dog eat bone every day",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("doctor be hospital last day night",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("pictures paint be flat round figure be very often foot do look be stand ground all point downward be hanging air",d1));
	//	exo14(d1,d2,d3);
        
        ArrayList<String> l = algoExhaustif("mouse pilot computer",d1);
       
		/* Argument de ligne de commande:
		 * Main.class [dictionnaire] [corpus]
		 * Par exemple: java org.wic.wsd.Main dictionnaire.xml eng-coarse-all-words.xml sortie.ans*/
		
		/*Exemple d'utilisation*/
	/*	Dictionary d = new Dictionary();
		d.loadDictionary("Dict-Lesk.xml");
		
		Corpus c = new Corpus();
		c.loadCorpus(d, args[1]);
		
        PrintStream answerWriter = new PrintStream(args[2]);
        
		for(Text t : c.getTexts()){// On r�alise les m�mes op�rations sur chaque texte du corpus
			System.out.println("Texte "+t.getLabel());
	*/		/*Configuration initiale: Selection de sens al�atoires*/
		//	ProblemConfiguration configuration = new ProblemConfiguration(t.getLength(), true, t);
			
			/*Faire quelque chose avec configuration*/
			
			/*Par exemple, pour calculer son score*/
		/*	configuration.computeScore(t, d);
			double score = configuration.getScore();
			System.out.println("\tScore initial=         "+score);
		*/	
			/*Faire un changement al�atoire, 
			 * on peut �tendre/modifier la classe pour avoir un comportement
			 * plus sp�cifique*/
		//	configuration.makeChange(t);
			
			/*Score post changement*/
		/*	configuration.computeScore(t, d);
			score = configuration.getScore();
			System.out.println("\tScore apr�s changement="+score);
            configuration.writeResult(t, answerWriter);
        
        }*/
	}
	
	
	public static ArrayList<String>algoExhaustif(String s, Dictionary dict){
		ArrayList<String> liste = new ArrayList<String>();
		int i,j,k;
		//generation de toutes les combinaisons de sens
		ArrayList<Sense> listeSenseMot = new ArrayList<Sense>();
		ArrayList<Sense> listeTMP = new ArrayList<Sense>();
		// tableau 2d contenant toutes les combinaisons de sens
		ArrayList<ArrayList<Sense>>listSens = new ArrayList<ArrayList<Sense>>();
		ArrayList<ArrayList<Sense>>listSensTMP = new ArrayList<ArrayList<Sense>>();
		String[] tableauMots = s.split(" ");
        for (i=0; i < tableauMots.length ; i++){
        	listeSenseMot = dict.getSenses(tableauMots[i]);
        	// premier passage
        	if (listSens.isEmpty()){
        		for(j=0; j < listeSenseMot.size(); j++){	
        			listeTMP = new ArrayList<Sense>();
        			listeTMP.add(listeSenseMot.get(j));
        			listSens.add(listeTMP);
        		}		
        	}
        	
        	else{ 		
        		listSensTMP = new ArrayList<ArrayList<Sense>>();
        		for(j=0; j < listeSenseMot.size(); j++){
        			for(k = 0; k < listSens.size(); k++){
        				listeTMP = new ArrayList<Sense>();
        				//System.out.println(listeTMP.size());
        				ArrayList<Sense> clone = (ArrayList<Sense>) listSens.get(i).clone();
						listeTMP = clone;
        				Sense sen = (Sense)listeSenseMot.get(j).clone();
            			listeTMP.add(sen);
            			System.out.println(listeTMP.size());
            			listSensTMP.add(listeTMP);
        			}
        		}
        		listSens = 	listSensTMP;
        	}
        		
        }
        // V�rification :	
        System.out.println("TAILLE:"+listSens.size());
		for(i = 0 ; i < listSens.size() ;i++){
			System.out.println("------------"+listSens.get(i).size());
			
			for(j=0; j<listSens.get(i).size(); j++)
				System.out.println(listSens.get(i).get(j));
		}
		return liste;
	}
    
	public static long nbCombinaisons(String s, Dictionary dict){
        long nbCombinaison = 1;
        String[] tableauCombinaison = s.split(" ");
        boolean hasChanged = false;
        for (int i=0; i < tableauCombinaison.length ; i++){
        	if (dict.getSenses(tableauCombinaison[i]).size()> 0){
        		nbCombinaison*=dict.getSenses(tableauCombinaison[i]).size();
        		hasChanged = true;
        	}
        }
        if (hasChanged)
        	return nbCombinaison;
        else
        	return 0;
    }
	
	//exo 14
	public static void exo14(Dictionary d1,Dictionary d2,Dictionary d3){
		//lecture du csv + initialisation de la structure
		ArrayList<String[]> liste = new ArrayList<String[]>();
		//Input file which needs to be parsed
        String fileToParse = "paires.csv";
        BufferedReader fileReader = null;
         
        //Delimiter used in CSV file
        final String DELIMITER = ",";
        try
        {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));
             
            //Read the file line by line
            while ((line = fileReader.readLine()) != null)
            {
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                liste.add(tokens);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // on retire l'entete...
        liste.remove(0);
        // fin de l'initialisation de la structure liste, qui contient le fichier csv
   
		//pour chaque paire de mots calcul de la similarit� avec les 3 dicos
        System.out.println("Mot 1, mot 2, simil humain, simil dico 1, simil dico 2, simil dico 3");
		for (int i= 0; i< liste.size(); i++){
			float d1sim = d1.getSimilarity(liste.get(i)[0],liste.get(i)[1]);
			float d2sim = d2.getSimilarity(liste.get(i)[0],liste.get(i)[1]);
			float d3sim = d3.getSimilarity(liste.get(i)[0],liste.get(i)[1]);
			System.out.println(liste.get(i)[0]+" "+liste.get(i)[1]+" "+liste.get(i)[2]+" "+d1sim+" "+d2sim+" "+d3sim);
		}
   
	}
	
    
}
