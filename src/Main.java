

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import corpus.Corpus;
import corpus.Text;
import dictionary.Dictionary;
import dictionary.Sense;
import dictionary.Word;


public class Main {
	
	public static void main(String[] args) throws FileNotFoundException{
	/*	Dictionary d1 = new Dictionary();
		d1.loadDictionary("src/Dict-Lesk.xml");
		Dictionary d2 = new Dictionary();
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
		System.out.println("Similirat� Lesk avec le dico Disk-Lesk-etendu: "+ d2.getSimilarity("cone", "pine"));
		System.out.println("Similirat� Lesk avec le dico 3: "+ d3.getSimilarity("cone", "pine"));
		
		double[] vect1 = {1.,4.,8.,12.,2.,5.,4.};
		double[] vect2 = {4,2,1,6,6,10,4};
		System.out.println("La cor�lation est de: "+Stats.getPearsonCorrelation(vect1,vect2));
		
		System.out.println("nombre de sens: "+nbCombinaisons("go",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("mouse pilot computer",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("dog eat bone every day",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("doctor be hospital last day night",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("pictures paint be flat round figure be very often foot do look be stand ground all point downward be hanging air",d1));
			exo14(d1,d2,d3);
        
      	ArrayList<Sense> l = algoExhaustif("doctor be hospital last day night",d1);
      */ 
		/* Argument de ligne de commande:
		 * Main.class [dictionnaire] [corpus]
		 * Par exemple: java org.wic.wsd.Main dictionnaire.xml eng-coarse-all-words.xml sortie.ans*/
		
		/*Exemple d'utilisation*/
		Dictionary d = new Dictionary();
		d.loadDictionary("src/Dict-Lesk.xml");
		
		Corpus c = new Corpus();
		c.loadCorpus(d,"eng-coarse-all-words.xml");
		stochastique1(c,50,d);
 //       PrintStream answerWriter = new PrintStream(args[2]);
   /*     
		for(Text t : c.getTexts()){// On r�alise les m�mes op�rations sur chaque texte du corpus
			System.out.println("Texte "+t.getLabel());
			/*Configuration initiale: Selection de sens al�atoires*/
		//	ProblemConfiguration configuration = new ProblemConfiguration(t.getLength(), true, t);
			
			/*Faire quelque chose avec configuration*/
			
			/*Par exemple, pour calculer son score*/
		//	configuration.computeScore(t, d);
		//	double score = configuration.getScore();
		//	System.out.println("\tScore initial=         "+score);
			
			/*Faire un changement al�atoire, 
			 * on peut �tendre/modifier la classe pour avoir un comportement
			 * plus sp�cifique*/
		//	configuration.makeChange(t);
			
			/*Score post changement*/
		//	configuration.computeScore(t, d);
		//	score = configuration.getScore();
		//	System.out.println("\tScore apr�s changement="+score);
       //     configuration.writeResult(t, answerWriter);
        
       // }
	}
	
	
	public static ArrayList<Sense>algoExhaustif(String s, Dictionary dict){
		ArrayList<Sense> liste = new ArrayList<Sense>();
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
        				@SuppressWarnings("unchecked")
						ArrayList<Sense> clone = (ArrayList<Sense>) listSens.get(k).clone();
						listeTMP = clone;
        				Sense sen = (Sense)listeSenseMot.get(j).clone();
            			listeTMP.add(sen);
            			listSensTMP.add(listeTMP);
        			}
        		}
        		listSens = 	listSensTMP;
        	}
        }
        // V�rification :	
       /* System.out.println("TAILLE:"+listSens.size());
		for(i = 0 ; i < listSens.size() ;i++){
			System.out.println("------------"+listSens.get(i).size());
			
			for(j=0; j<listSens.get(i).size(); j++)
				System.out.println(listSens.get(i).get(j));
		}*/
		int similarite;
		ArrayList<Integer> scoreListe = new ArrayList<Integer>();
		//calcul de le similirat� entre chaque sens pour chaque combinaison
		for(i=0; i < listSens.size();i++){
			similarite = 0;
			for(j=0; j < listSens.get(i).size(); j++){
				for(k=j+1; k < listSens.get(i).size(); k++){
					similarite+= dict.getSimilarity(listSens.get(i).get(j),listSens.get(i).get(k));
				}
			}
			scoreListe.add(similarite);
		}
		if (scoreListe.size() == 0){
			System.out.println("Mot non pr�sent dans le dico!!!");
			return liste;
		}
		int maxScore = Collections.max(scoreListe);
		int indexMaxScore = scoreListe.indexOf(maxScore);
		System.out.println("Le score max est: "+maxScore+" a l'indice: "+indexMaxScore+" Pour la phrase: "+s);
		System.out.println("La liste des sens ayant obtenu le meilleur score:\n--------------- ");
		for(i=0;i<listSens.get(indexMaxScore).size() ; i++)
			System.out.println(listSens.get(indexMaxScore).get(i));
		return listSens.get(indexMaxScore);
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
	
    
	public  static void stochastique1(Corpus c, int n, Dictionary d){
        /*Configuration initiale: Selection de sens aléatoires*/
        ArrayList<Text> listeTexte = c.getTexts();
        ArrayList<ProblemConfiguration> listConfiguration = new ArrayList<ProblemConfiguration>();
        Word mot;
        double[] listeScore = new double[listeTexte.size()];
        Random rand = new Random();
        int indiceMotHasard; // l'indice du mot qu'on tire au hasard dans le text
        int indiceSensHasard; // l'indice du sens qu'on tire au hasard 
        //+ arrayliste de configuration
        for (int j= 0; j < listeTexte.size(); j++){
            listeScore[j] = 0.0;
            ProblemConfiguration configuration = new ProblemConfiguration(listeTexte.get(j).getLength(), true, listeTexte.get(j));
            for (int i=1 ; i <= n;i++){
                // tirer un mot au hasard entre 0 et longeur texte
            	indiceMotHasard =  rand.nextInt(listeTexte.get(j).getLength());
            	mot=listeTexte.get(j).getWord(indiceMotHasard);
                // tirer un sense au hasard entre 0 et nombre de sens du mot a partir d'un dico
            	do{
            		indiceSensHasard = rand.nextInt(mot.sensesSize());
            	} while(configuration.getSelectedSenseAt(indiceMotHasard) == indiceSensHasard && mot.sensesSize()!=1);
            	//System.out.println("INDICE"+indiceSensHasard);
            	 configuration.setSelectedSenseAt(indiceMotHasard, indiceSensHasard);
                // repeter l'etape precedente tant que le sens n'est pas different ou qu'il n'y a qu'un sens au mot
                // setsense indeice du mot du nouyveau sens
                // recalculer le sore de la configuaratiopn
            	 configuration.computeScore(listeTexte.get(j), d);	
                // si le score est > listeScore[j] alors listeScore[j] = score qu'on vient de calculer
            	 if (listeScore[j]< configuration.getScore()){
            		 listeScore[j] = configuration.getScore();
            		 if (listConfiguration.size() == j && j!=0)
            			 listConfiguration.remove(j-1);
            		 listConfiguration.add(configuration);
            	 }
            	 System.out.println(configuration.getScore());
            }
            System.out.println("----");
            System.out.println("score retenu: "+ listeScore[j] );
            System.out.println("----");
        }
        //retourne une combinaison <texte,configuration> ou configuartion est le meilleur resultat obtenu
        // return <listText,listConfiguration>; enfin un truc dans ce genre...
	}

	
	
}
