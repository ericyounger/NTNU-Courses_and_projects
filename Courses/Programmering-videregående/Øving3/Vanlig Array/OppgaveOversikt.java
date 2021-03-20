class OppgaveOversikt{
    private Student[] studenter = new Student[5];
    private int antStud = 0;

    public OppgaveOversikt(){

    }

    public boolean regNyStudent(String navn){
        for(int i=0; i<studenter.length; i++){
            if(studenter[i] != null){
                if (navn.equalsIgnoreCase(studenter[i].getNavn())) {
                    return false;
                }
            }
        }
        if(antStud == studenter.length){
            Student[] kopi = new Student[studenter.length+5];
            for(int i=0; i<studenter.length; i++){
                kopi[i] = studenter[i];
            }
            studenter = kopi;
        }

        Student a = new Student(navn);
        studenter[antStud] = a;
        antStud++;
        //registrer student
        return true;
    }

    public int finnAntStud(){
        return antStud;
    }

    public int finnAntOppgaver(String navn){
        for(int i=0; i<studenter.length; i++){
            if (navn.equalsIgnoreCase(studenter[i].getNavn())) {
                return studenter[i].getAntOppg();
            }
        }
        
        return -1;
    }

    public boolean økAntOppg(String navn, int økning){
        for(int i=0; i<studenter.length; i++){
            if (navn.equalsIgnoreCase(studenter[i].getNavn())) {
                studenter[i].setAntOppg(økning);
                return true;
            }
        }
        return false;
    }
    
    public String[] finnAlleNavn(){
        String[] navnene = new String[studenter.length];

        for(int i=0; i<studenter.length; i++){
            if(studenter[i] != null){
                navnene[i] = studenter[i].getNavn();
            }
          
        }
        return navnene;
    }

    public String toString(){
        String message = "";
        for(int i=0; i<studenter.length; i++){
            if(studenter[i] != null){
                message += studenter[i].toString();
            }
           
        }
        return message;
    }
}