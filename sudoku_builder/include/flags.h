#ifndef SUDOKU_BUILDER_FLAGS_H
#define SUDOKU_BUILDER_FLAGS_H

#include <stdbool.h>
#include <stddef.h>

typedef struct {
    char** flags;
    size_t flags_len;
    int min_args;
    int max_args;
} Flag;

extern const Flag shape;
extern const Flag help;

bool isFlag(char* arg, Flag flag);

#endif //SUDOKU_BUILDER_FLAGS_H