
undefined8 main(void)

{
  char local_28 [32];
  
  printf("Enter your name: ");
  fgets(local_28,0x20,stdin); //0x20 is 32 in hex decimal.
  printf("Hello ");

  //THIS IT IS THE VULNERABILITY
  //Prone to format strings attacks.
  //https://owasp.org/www-community/attacks/Format_string_attack
  //http://www.cis.syr.edu/~wedu/Teaching/cis643/LectureNotes_New/Format_String.pdf
  printf(local_28); 

  //Solution: Either specify printf with a literal format string (%s) or use method puts() instead.
  
  putchar(10);
  return 0;
}

