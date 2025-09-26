#include <stdio.h>
#include <time.h>
#include "../include/build_board.h"
#include "../include/select_clues.h"
#include "../include/board_util.h"
#include "../include/upload_puzzle.h"
#include "../include/flags.h"


int gets_puzzle(int board[9][9], int num_clues) {
    get_board(board);
    get_puzzle(board, num_clues);
}

bool eval_args(int argc, char *argv[], int *num_puzzles, int *num_clues, char** filepath, bool* daily) {
    int pos_args = 0;

    for(int i = 1; i < argc; i++) {
		if(isFlag(argv[i], helpFlag)) {
            // printHelp();
            return false;
        } else if(isFlag(argv[i], shapeFlag)) {
            if(i >= argc - 1) { // No filepath given
                // wrong_arg_num(shape)
                return false;
            }

            *filepath = argv[++i]; // Get filepath
		} else if(isFlag(argv[i], dailyFlag)) {
			*daily = true;
		} else {
            if (pos_args == 0) {
                *num_puzzles = atoi(argv[i]);
            } else if (pos_args == 1) {
                *num_clues = atoi(argv[i]);
            }

            pos_args++;
        }
	}

    if(pos_args != 2) {
        //wrong_pos_num(pos_args);
        return false;
    }

    return true;
}

int main(int argc, char *argv[]) {
    int num_puzzles = 0;
    int num_clues = 0;
    char* filepath = NULL;
	bool daily = false;

    bool run_builder = eval_args(argc, argv, &num_puzzles, &num_clues, &filepath, &daily);

	// DEBUG
	printf("\n\nArgs: %d, %d, %s", num_puzzles, num_clues, filepath);

    if(!run_builder) {
    	printf("\n\nrun=false"); // DEBUG
	    return 0;
	}

    if(num_puzzles < 1 || num_clues < 17 || num_clues > 80) {
        printf("Arguments are invalid. Please try again\n");

        return -1;
    }

    srand(time(NULL));

    for(int i = 0; i < num_puzzles; i++) {
        int board[9][9];
        gets_puzzle(board, num_clues);
        print_board(board);
        upload_puzzle(board, daily);
    }
}
