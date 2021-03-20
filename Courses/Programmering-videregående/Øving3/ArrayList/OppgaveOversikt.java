import java.util.ArrayList;

class OppgaveOversikt{
    private ArrayList<Student> studenter = new ArrayList<Student>();
    private int antStud = 0;

    public OppgaveOversikt(){

    }

    public boolean regNyStudent(String navn){
        for(int i=0; i<studenter.size(); i++){
            if(studenter.get(i) != null){
                if (navn.equalsIgnoreCase(studenter.get(i).getNavn())) {
                    return false;
                }
            }
        }
        // if(antStud == studenter.length){
        //     Student[] kopi = new Student[studenter.length+5];
        //     for(int i=0; i<studenter.size(); i++){
        //         kopi[i] = studenter[i];
        //     }
        //     studenter = kopi;
        // } 
        // ArrayList trenger ikke utvide seg?

        Student a = new Student(navn);
        studenter.add(a);
        antStud++;
        //registrer student
        return true;
    }

    public int finnAntStud(){
        return antStud;
    }

    public int finnAntOppgaver(String navn){
        for(int i=0; i<studenter.size(); i++){
            if (navn.equalsIgnoreCase(studenter.get(i).getNavn())) {
                return studenter.get(i).getAntOppg();
            }
        }
        
        return -1;
    }

    public boolean økAntOppg(String navn, int økning){
        for(int i=0; i<studenter.size(); i++){
            if (navn.equalsIgnoreCase(studenter.get(i).getNavn())) {
                studenter.get(i).setAntOppg(økning);
                return true;
            }
        }
        return false;
    }
    
    public String[] finnAlleNavn(){
        String[] navnene = new String[studenter.size()];

        for(int i=0; i<studenter.size(); i++){
            if(studenter.get(i) != null){
                navnene[i] = studenter.get(i).getNavn();
            }
          
        }
        return navnene;
    }

    public String toString(){
        String message = "";
        for(int i=0; i<studenter.size(); i++){
            if(studenter.get(i) != null){
                message += studenter.get(i).toString();
            }
           
        }
        return message;
    }
}