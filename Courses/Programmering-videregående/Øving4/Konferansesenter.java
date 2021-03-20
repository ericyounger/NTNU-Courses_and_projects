import java.util.ArrayList;
import static javax.swing.JOptionPane.*;

class Konferansesenter{
    private ArrayList<Rom> rom = new ArrayList<Rom>();
    
    
    public Konferansesenter(){
        
    }

    public boolean leggTilRom(int storrelse, int romNr){
        for(int i=0; i<rom.size(); i++){
            if (romNr == (rom.get(i).getRomNr())) { 
                showMessageDialog(null, "Rom finnes fra før av");
                return false;
            }
        }
        
       Rom a = new Rom(storrelse, romNr);
        rom.add(a);
        return true;
    }

    public boolean finnRom(int romNr){
        if(romNr < rom.size() && romNr>=0){
            finnRomGittIndex(romNr);
            System.out.println(rom.size());
            return true;
        } else{
        for(int i=0; i<rom.size(); i++) {
            if (rom.get(i).getRomNr() == romNr) {
                System.out.println(romNr);
                    System.out.println(rom.get(i));

                showMessageDialog(null, rom.get(i).toString());
            }
            
        }
     
        return true;
       
    }
}

    public void finnRomGittIndex(int indeks){
        if(indeks>=0 && indeks<rom.size()){
            showMessageDialog(null, rom.get(indeks).toString());
        } else{
            showMessageDialog(null, "Ugyldig indeks, skriv inn et tall mellom 0-"+(rom.size()-1));
        }
        
    }

    public int getantRom(){
        return rom.size();
    }

    public boolean leggTilReservasjon(int personer, Reservasjon e){
        // SJEKK OM ROMMET ER LEDIG PÅ DET FORESPURTE ROMMET OGSÅ!!!
        //******************************************************** */
        
        for(int i=0; i<rom.size(); i++){
            if(rom.get(i).getStorrelse()>= personer){
                
               
                    return rom.get(i).leggTilReservasjon(e);
                
              
            }
        }
        return false;
    }

    

    public String toString(){
        String tmp = "All informasjon som er lagret: \n" + "----------------------------\n";
        for(int i=0; i<rom.size(); i++){
            tmp += rom.get(i).toString();
        }
        return tmp;
    }

}