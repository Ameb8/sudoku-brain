#include <stdio.h>
#include <string.h>

#include "../include/flags.h"

static char* shape_flags[] = {"--shape", "-s"};
static char* help_flags[] = {"--help", "-h"};

const Flag shape = {
    .flags = shape_flags,
    .flags_len = 2,
    .min_args = 1;
    .max_args = 1,
};

const Flag help = {
    .flags = help_flags,
    .flags_len = 2,
    .min_args = 0;
    .max_args = 0,
};

bool isFlag(char* arg, Flag flag) {
    for(int i = 0; i < flag.flags_len; i++) {
        if(!strcmp(arg, flag.flags[i]))
            return true;
    }

    return false;
}

