#/bin/sh

#Parameteren $1 er filnavn m. katalog
#Beregn katalognavn utfra filnavn
JPGDIR=`dirname $1`/jpg
#Opprett hvis den ikke fins
if [ ! -d $JPGDIR ] ; then mkdir $JPGDIR ; fi
#Flytt filen
mv $1 $JPGDIR
