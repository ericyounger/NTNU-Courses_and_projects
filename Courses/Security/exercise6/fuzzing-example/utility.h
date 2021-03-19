#pragma once
#include <stdbool.h>
#include <stddef.h>
#include <string.h>
#include <stdlib.h>


char* replaceString(const char *line, size_t len){
	char *and = "&amp;";
	char *gt = "&gt;";
	char *lt = "&lt;";
	char *replacedString = (char *) malloc(sizeof(char)*len*2);
	size_t j =0;

	//Search for all occurenses for <, >, &.
	for(size_t i=0; i<len; i++){


		if(j < (strlen(replacedString)-sizeof(char)*6)){
			replacedString = (char *) realloc(replacedString, strlen(replacedString)+sizeof(char)*10);
		}

		if(line[i] == '<'){
			strcpy(&replacedString[j], lt);
			j += 4;

		} else if(line[i] == '>'){
			strcpy(&replacedString[j], gt);
			j += 4;

		} else if(line[i] == '&'){
			strcpy(&replacedString[j], and);
			j +=5;

		} else if(line[i] == '\0'){
			break;
		} else {
			replacedString[j] = line[i];
			j += 1;
		}
	}

	return replacedString;
}
