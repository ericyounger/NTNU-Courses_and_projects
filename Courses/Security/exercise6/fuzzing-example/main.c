#include "utility.h"
#include <stdio.h>
#include <string.h>
char* replaceString(const char *line, size_t len);


int main() {
	//Read in line from user
	char *line = NULL;
	size_t len = 0;
	size_t lineSize = getline(&line, &len, stdin);

	char *output = replaceString(line, len);
	printf("%s", output);

	char *example = "1 < 2 && 2 > 1";
	char *output2 = replaceString(example, strlen(example));

	printf("%s", output2);

	free(output2);
	free(output);
	return 0;
}

