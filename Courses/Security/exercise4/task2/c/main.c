#include <stdio.h>
#include <string.h>

const char* replaceString(const char *line, size_t len);


int main() {
	//Read in line from user
	char *line = NULL;
	size_t len = 0;
	size_t lineSize = getline(&line, &len, stdin);

	char *example = "1 < 2 && 2 > 1";
	printf("%s", replaceString(line, len));
	printf("%s", replaceString(example, strlen(example)));
	return 0;
}

const char* replaceString(const char *line, size_t len){
	char *and = "&amp;";
	char *gt = "&gt;";
	char *lt = "&lt;";
	static char replacedString[256];
	int j =0;

	//Search for all occurenses for <, >, &.
	for(size_t i=0; i<len; ++i){
		if(line[i] == '<'){
			strcpy(&replacedString[j], lt);
			j += 4;

		} else if(line[i] == '>'){
			strcpy(&replacedString[j], gt);
			j += 4;

		} else if(line[i] == '&'){
			strcpy(&replacedString[j], and);
			j +=5;

		} else if(line[i] == '/0'){
			break;
		} else {
			replacedString[j] = line[i];
			j += 1;
		}
	}

	return replacedString;
}
