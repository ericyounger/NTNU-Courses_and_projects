
public class Tekstanalyse{
	private String tekst;
	public int tabell[];
	int sum=0;
	double hundre=0;
	int count=0;
	int forespurt;
	public Tekstanalyse(String tekst){
    	this.tekst = tekst;
			System.out.println("Teksten du skrev inn er:  \n" +tekst + "\n");
    	this.tabell = new int[30];
			tellBokstaver();

	}

	public void tellBokstaver(){
			for(int j=0; j<tekst.length(); j++){
				char tegn = tekst.charAt(j);
				int verdi = tegn;
				int verdiLowerCase = tegn-97;
				int verdiUpperCase = tegn-65;
				int verdiNorskLower = tegn-203;
				int verdiNorskUpper = tegn-171;
				int verdiNorskoeLower = tegn-220;
				int verdiNorskoeUpper = tegn-188;

				if(verdiLowerCase<29 && verdiLowerCase>=0){
						tabell[verdiLowerCase]++;
				}
				if(verdiUpperCase<29 && verdiUpperCase>=0){
					tabell[verdiUpperCase]++;
				}

				if(verdiNorskoeLower == 28){
					tabell[verdiNorskoeLower]++;
				}

				if(verdiNorskoeUpper == 28){
					tabell[verdiNorskoeUpper]++;
				}

				if(verdiNorskLower>=26 && verdiNorskLower<= 28){
					tabell[verdiNorskLower]++;

				}

				if(verdiNorskUpper>=26 && verdiNorskUpper<=28){
					tabell[verdiNorskUpper]++;
				}

				if(verdi<65 || verdi < 90 && verdi<97 || verdi>122 && verdi<127 ){
					tabell[29]++;
				}
			} // For loop end
	}

  public int getAntallBokstaver(){
	  for (int i = 0; i < tabell.length-1; i++){
			int tall = tabell[i];
			sum += tall;


		}
		return sum;
	}

	public double getProsentTegn(){
		double tegn = tabell[29];
		double prosentTegn = tegn/(getAntallBokstaver()+tegn)*100;
		return prosentTegn;
	}

	public int getAntallForskjellige(){
		for(int m=0; m<tabell.length-1; m++){
			if(tabell[m] !=0){
				count++;
			}
		}
		return count;
	}

	public int getAntallforespurt(int bokstavDiff){
		forespurt = tabell[bokstavDiff];
		return forespurt;
	}

	public int getAntallforespurt(){
		int forespurt2 = forespurt;
		return forespurt2;
	}

	public char finnMaksimum(){
		int maks=0;
		int unicodeMaks=0;
		if(tabell.length>0){
			maks= tabell[0];
			for(int a= 1; a<tabell.length; a++){
				if (tabell[a]>maks) {
				//	maks = tabell[a];
				unicodeMaks = a;
				}
			}
		}
		unicodeMaks+=97;
		char oftest = (char) unicodeMaks;
		return oftest;
	}

	public String toString(){
		return "Antall forskjellige bokstaver: " + getAntallForskjellige() + "\nAntall bokstaver i teksten: "
		+ getAntallBokstaver() + "\nProsent som ikke er tegn: " + getProsentTegn() + "\nAntall forekomst av bokstav: " + getAntallforespurt() +
		"\nBokstav som forekommer oftest i teksten: " +finnMaksimum()+"\n";
	}

}
