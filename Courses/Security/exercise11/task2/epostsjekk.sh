#/bin/bash

# Regular Colors defined for printing purposes
Black='\033[0;30m'        # Black
Red='\033[0;31m'          # Red
Green='\033[1;32m'        # Green
Yellow='\033[0;33m'       # Yellow
Blue='\033[0;34m'         # Blue
Purple='\033[0;35m'       # Purple
Cyan='\033[0;36m'         # Cyan
White='\033[0;37m'        # White
ENDC="\033[0m"            # End coloring

recursive_spf () {
  #prints out ip addresses if found match
  nslookup -type=txt $1 | grep v=spf1 | tr ' ' '\n' | grep ip

  #Handles include, will print if match
  spfInc=`nslookup -type=txt $1 | grep v=spf1 | tr ' ' '\n' | grep include | cut -d ':' -f 2`
  for domain in $spfInc; do 
    recursive_spf $domain
  done

  #Handles redirects, will print if match TODO: FIX THIS
  spfRedirect=`nslookup -type=txt $1 | grep v=spf1 | tr ' ' '\n' | grep redirect | cut -d '=' -f 2 | cut -d "\"" -f 1`
  for domain in $spfRedirect; do
      recursive_spf $domain
  done
}


site=$1

printf "${Cyan}Checking mail records for $site${ENDC}\n"

#Find address and mail exchange server and spf records
ADDRESSES=`nslookup $site| grep "answer:" -A 50 | grep "Address" | cut -d " " -f2` 
MXSERVER=`dig mx $site +short | cut -d " " -f2`
SPF=`nslookup -type=txt $site`

#Print out information.
printf "${Green}# Mailservers are:${ENDC} \n$MXSERVER"
printf "\n${Green}# Has addresses:${ENDC} \n$ADDRESSES"

#Reverse lookup
printf "\n${Green}# Reverse lookup give names:${ENDC}\n"
 for address in $ADDRESSES;
  do
    revName=`nslookup $address | grep "name" | cut -d "=" -f2 | sed -e 's/^[[:space:]]*//'`
    if ! [[ -z "$revName" ]]
    then
        echo "$revName"
    else
        echo "No reverse record found"
    fi
  done

#Recursive SPF lookup
if [[ $SPF == *"v=spf1"* ]]
then
    printf "${Green}Site has SPF records${ENDC}"
    spfInc=`nslookup -type=txt $1 | grep v=spf1`
    printf "\n${Purple}Found: ${ENDC}\n$spfInc"
    printf "\n${Purple}Running recursive search on SPF${ENDC}\n"
    printf "\n${Green}Allowed addresses are:${ENDC}\n"
    recursive_spf $site
else
    echo "${Red}Site does not have SPF records${ENDC}"
fi 





