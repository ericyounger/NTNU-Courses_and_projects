//Transaksjoner innlesing
FileReader leseforbTilFil = new FileReader(transaksjoner);
BufferedReader leser = new BufferedReader(leseforbTilFil);
String etBelop= leser.readLine();
if(etBelop.charAt(0) == 'I' && etBelop !=null){
  String belop = etBelop.substring(2);
  double tallBelop = Double.parseDouble(belop);
  setSaldo(tallBelop);
} else if(etBelop.charAt(0) == 'U' && etBelop !=null){
  String belop = etBelop.substring(2);
  double tallBelop = Double.parseDouble(belop);
  setSaldo((-tallBelop));
}
String innlesteTrans = "Følgende transaksjoner er registrert";

while(etBelop != null){
  innlesteTrans += ("\n" + etBelop);
  etBelop = leser.readLine();
  if(etBelop != null){
    if(etBelop.charAt(0) == 'I'){
      String belop = etBelop.substring(2);
      double tallBelop = Double.parseDouble(belop);
      setSaldo(tallBelop);
   } else if(etBelop.charAt(0) == 'U'){
      String belop = etBelop.substring(2);
      double tallBelop = Double.parseDouble(belop);
      setSaldo((-tallBelop));
     }
}

}
leser.close();
System.out.print("\033[H\033[2J");
System.out.flush();
System.out.println(innlesteTrans);
System.out.println("\nSaldo: " + getSaldo());
