#/bin/sh
find ~/Desktop/testFolder/ -name "jpg" -prune -o -name "*.jpg" -exec ./minflyttjpg.sh {} \;