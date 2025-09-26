#include <stdio.h>
#include <string.h>

#include "../include/flags.h"

static char* shape_flags[] = {"--shape", "-s"};
static char* help_flags[] = {"--help", "-h"};
static char* daily_flags[] = {"--daily", "-d"};

const Flag shapeFlag = {
    .flags = shape_flags,
    .flags_len = 2,
    .min_args = 1,
    .max_args = 1,
};

const Flag helpFlag = {
    .flags = help_flags,
    .flags_len = 2,
    .min_args = 0,
    .max_args = 0,
};

const Flag dailyFlag = {
	.flags = daily_flags,
	.flags_len = 2,
	.min_args = 0,
	.max_args = 0,
};

bool isFlag(char* arg, Flag flag) {
    for(size_t i = 0; i < flag.flags_len; i++) {
        if(!strcmp(arg, flag.flags[i]))
            return true;
    }

    return false;
}

