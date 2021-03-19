#/bin/sh

# Skriver ut hvilken bruker som kjører kommandoen
echo Jeg heter `whoami`

# deklarerer en variabel med navn maskinnavn som kjører hostname og setter variabelen til dette
MASKIN=`hostname`

#maskin blir eric-macbook

#grav-aksenter vil kjøres som kommandoer først, og utskriften fra de vil settes inn istedet for aksentene. deretter kjøres hele kommandoen.