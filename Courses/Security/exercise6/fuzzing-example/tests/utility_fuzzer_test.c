#include "../utility.h"
#include <stddef.h>
#include <stdint.h>

int LLVMFuzzerTestOneInput(const uint8_t *data, size_t size) {
  char *test = replaceString((const char *)data, size);
  free(test);
  return 0;
}
