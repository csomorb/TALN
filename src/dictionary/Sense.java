package dictionary;




public class Sense implements Cloneable{
	
	private String ids;
	private String senseID;
	private Definition def;
	
	public Sense(){
		ids = "";
		def = new Definition(1);
	}
	

	public void setIDS(String string){
		this.ids = string;
	}
	
	public String getIDS(){
		return this.ids;
	}

	public void setDef(String string){
		def = new Definition(string);
	}
	
	public Definition getDef(){
		return this.def;
	}

	/**
	 * @return the senseID
	 */
	public String getSenseID() {
	    return senseID;
	}

	public String toString(){
		return ids+" "+senseID+" "+def.toString();
	}
	/**
	 * @param senseID the senseID to set
	 */
	public void setSenseID(String senseID) {
	    this.senseID = senseID;
	}
	
	public Object clone() {
	    Sense sens = null;
	    try {
	    	// On récupère l'instance à renvoyer par l'appel de la 
	      	// méthode super.clone()
	      	sens = (Sense) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	      	// Ne devrait jamais arriver car nous implémentons 
	      	// l'interface Cloneable
	      	cnse.printStackTrace(System.err);
	    }
	    
	    // On clone l'attribut de type Patronyme qui n'est pas immuable.
	    sens.def = (Definition) def.clone();
	    
	    // on renvoie le clone
	    return sens;
	}
	

}
