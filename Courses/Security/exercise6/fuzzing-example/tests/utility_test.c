#include "../utility.h"
#include <assert.h>
#include <string.h>

int main() {
  char *stringInput = "1 < 2 && 2 > 1";
  char *stringResult = "1 &lt; 2 &amp;&amp; 2 &gt; 1";
  char *actualResult = replaceString(stringInput, strlen(stringInput));
  assert(actualResult == stringResult);
  free(actualResult);
}
