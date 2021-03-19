#/bin/sh
find ~/Desktop/testFolder/ -mtime -7 -size +10k ! -name '*.gz' ! -name '*.zip' -exec gzip -q * -k {}  \; 