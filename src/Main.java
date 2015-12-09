

import java.io.FileNotFoundException;
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
		Dictionary d2 = new Dictionary();
		d2.loadDictionary("src/Dict-Lesk-etendu.xml");
		Dictionary d3 = new Dictionary();
		d3.loadDictionary("src/dict_all_stopwords_stemming_semcor_dso_wordnetglosstag_150");
		System.out.println("Taille de Disk-Lesk: "+d1.size()+"\nTaille de Disk-lesk-etendu: "+d2.size()+"\nTaille du troisième: "+d3.size());
		
		ArrayList<Sense> listeSensCone = d1.getSenses("cone");
		System.out.println("Nombre de sens du mot cone: "+listeSensCone.size());
		for (int i = 0; i < listeSensCone.size(); i++)
			System.out.println(listeSensCone.get(i));
		
		ArrayList<Sense> listeSensPine = d1.getSenses("pine");
		System.out.println("Nombre de sens du mot pine: "+listeSensPine.size());
		for (int i = 0; i < listeSensPine.size(); i++)
			System.out.println(listeSensPine.get(i));
		System.out.println("Similiraté Lesk avec le dico Disk-Lesk: "+ d1.getSimilarity("cone", "pine"));
		System.out.println("Similiraté Lesk avec le dico Disk-Lesk-etendu: "+ d2.getSimilarity("cone", "pine"));
		System.out.println("Similiraté Lesk avec le dico 3: "+ d3.getSimilarity("cone", "pine"));
		
		Stats s = new Stats();
		double[] vect1 = {1.,4.,8.,12.,2.,5.,4.};
		double[] vect2 = {4,2,1,6,6,10,4};
		System.out.println("La corélation est de: "+s.getPearsonCorrelation(vect1,vect2));
		
		System.out.println("nombre de sens: "+nbCombinaisons("go",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("mouse pilot computer",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("dog eat bone every day",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("doctor be hospital last day night",d1));
        System.out.println("nombre de sens: "+nbCombinaisons("pictures paint be flat round figure be very often foot do look be stand ground all point downward be hanging air",d1));
		
		/* Argument de ligne de commande:
		 * Main.class [dictionnaire] [corpus]
		 * Par exemple: java org.wic.wsd.Main dictionnaire.xml eng-coarse-all-words.xml sortie.ans*/
		
		/*Exemple d'utilisation*/
	/*	Dictionary d = new Dictionary();
		d.loadDictionary("Dict-Lesk.xml");
		
		Corpus c = new Corpus();
		c.loadCorpus(d, args[1]);
		
        PrintStream answerWriter = new PrintStream(args[2]);
        
		for(Text t : c.getTexts()){// On réalise les mêmes opérations sur chaque texte du corpus
			System.out.println("Texte "+t.getLabel());
	*/		/*Configuration initiale: Selection de sens aléatoires*/
		//	ProblemConfiguration configuration = new ProblemConfiguration(t.getLength(), true, t);
			
			/*Faire quelque chose avec configuration*/
			
			/*Par exemple, pour calculer son score*/
		/*	configuration.computeScore(t, d);
			double score = configuration.getScore();
			System.out.println("\tScore initial=         "+score);
		*/	
			/*Faire un changement aléatoire, 
			 * on peut étendre/modifier la classe pour avoir un comportement
			 * plus spécifique*/
		//	configuration.makeChange(t);
			
			/*Score post changement*/
		/*	configuration.computeScore(t, d);
			score = configuration.getScore();
			System.out.println("\tScore après changement="+score);
            configuration.writeResult(t, answerWriter);
        
        }*/
	}
    
	public static long nbCombinaisons(String s, Dictionary dict){
        long nbCombinaison = 1;
        String[] tableauCombinaison = s.split(" ");
        for (int i=0; i < tableauCombinaison.length ; i++){
        	if (dict.getSenses(tableauCombinaison[i]).size()> 0)
        		nbCombinaison*=dict.getSenses(tableauCombinaison[i]).size();
        }
        return nbCombinaison;
    }
	
	
    
}
