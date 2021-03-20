import static javax.swing.JOptionPane.*;

class Oppgaveoversikt{
  private int antStud;
  Student[] studenter;
  int ant=0;

  Oppgaveoversikt(int antStud){
    this.antStud = antStud;
    studenter = new Student[antStud];


  }

  public int getantStud(){
    return ant;
  }

  public void getOppgaverGjort(){

  }

  public void nyStudent(String navn, int antOppg){
    if(antStud == ant){
      antStud *= 2;
      Student[] studenter2 = new Student[antStud];
      for(int i=0; i<studenter.length; i++){
        studenter2[i] = studenter[i];
      }
      studenter = studenter2;
      studenter2[ant] = new Student(navn, antOppg);

      ant++;
    }
    else{
      studenter[ant] = new Student(navn, antOppg);
      ant++;
    }
  }




  public String toString(){
    String antStudentReg = "";
    for(int i=0; i<ant; i++){
      antStudentReg +=i +": " + studenter[i].getNavn() +"\n";
    }

    return antStudentReg;
  }

}
