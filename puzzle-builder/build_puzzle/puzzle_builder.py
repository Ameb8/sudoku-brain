from functools import update_wrapper
from update_path import UpdatePath
from update import Update
import numpy as np

class PuzzleBuilder:
    """
    A class to generate and solve Sudoku puzzles using heuristic-based methods.
    Attributes:
        board (np.ndarray): A 9x9 array representing the Sudoku board.
        element_vals (np.ndarray): A 9x9x9 boolean array tracking possible values for each cell.
        num_clues (int): Number of given clues in the puzzle.
        num_vals (int): Number of cells that have been filled in the board.
    """
    def __init__(self):
        self.board = np.zeros((9, 9), dtype=int)
        self.element_vals = np.ones((9,9,9), dtype=bool)
        self.num_clues = 0
        self.num_vals = 0
        self.prev_states = []

    def add_clue(self, verbose=False):
        """
        Adds valid clue to board and updates state
        :param verbose:
        :return: (row, col) if successfully added, otherwise (-1, -1)
        """
        for i in range(100): # 100 attempts to find valid clue
            row, col, val = self.get_pos_clue()
            if self.is_valid(row, col, val): # Valid clue found
                self.update(row, col, val * -1)
                self.num_clues += 1
                self.num_vals += 1
                return row, col
        return -1, -1 # Valid clue could not be found

    def get_easy(self, verbose=False):
        # Reset board state
        self.board = np.zeros((9, 9), dtype=int)
        self.element_vals = np.ones((9,9,9), dtype=bool)
        self.num_clues = 0
        self.num_vals = 0

        while self.num_vals < 81: # Add clues and solve until board full
            row, col = self.add_clue() # Add clue
            if row == -1 or col == -1: # Backtrack if clue not added
                undo = self.undo()
                if not undo:
                    print('Error Occured')
                    return self.board
            elif verbose:
                print('\nclue added:')
                print(self.board)
            self.num_vals += self.hidden_singles(row, col) # Attempt to solve
            if verbose:
                print('\nsolved:')
                print(self.board)
                print(self.element_vals)
        return self.board, self.num_clues

    # returns np 9 x 9 array and number of clues
    def get_easy_puzzle(self, verbose=False):
        """
        Creates puzzle that is solvable with only naked and hidden singles
        :param verbose:
        :return: 9x9 nd array with negative values for clues, positives for solution values, and number of clues
        """
        # Reset board state
        self.board = np.zeros((9, 9), dtype=int)
        self.element_vals = np.ones((9,9,9), dtype=bool)
        self.num_clues = 0
        self.num_vals = 0

        # Initialize board with 10 random clues
        for _ in range(10):
            self.add_clue(verbose)

        # Run until board is full
        while self.num_vals < 81:
            # Solve with naked/hidden singles
            while self.hidden_singles(): # Solve with hidden singles as much as possible
                self.num_vals += 1

            if not self.add_clue(): # Add random clue
                self.undo()

        return self.board, self.num_clues

    def naked_single(self):
        """
        Solves puzzle with naked single method
        :return: returns True if value found, otherwise false
        """
        for i in range(9): # Iterate rows
            for j in range(9): # Iterate cols
                possible = []
                for k in range(9): # Check values
                    if self.element_vals[i][j][k]:
                        possible.append(k)
                if len(possible) == 1: # Only 1 possible value for (i, j) cell
                    self.update(i, j, possible[0])
                    return True
        return False



    def is_valid(self, row, col, val):
        # Checks if location and value is possible in that cell
        if self.board[row][col] != 0:
            return False
        if not self.element_vals[row][col][val-1]:
            return False
        return True

    def update(self, row, col, val):
        """
        Update puzzle with new value
        :param row: Row of new value
        :param col: Column of new value
        :param val: New value for puzzle
        :return:
        """
        self.board[row][col] = val
        path = UpdatePath(row, col)
        self.prev_states.append(Update(row, col))
        val = abs(val)
        self.update_subsection(path.rows, val - 1)
        self.update_subsection(path.cols, val - 1)
        self.update_subsection(path.sqrs, val - 1)

    def update_subsection(self, subsection, val):
        for i in range(9):
            row = subsection[0][i]
            col = subsection[1][i]
            if self.element_vals[row][col][val]:
                self.element_vals[row][col][val] = False
                self.track_pos_updates(row, col, val)

    def get_pos_clue(self):
        """
        Get random possible location and value of clue
        :return: row, location, and value of potential clue
        """
        row = np.random.randint(0, 9)
        col = np.random.randint(0, 9)
        val = np.random.randint(1, 10)
        return row, col, val

    def hidden_singles(self, row, col):
        """
        Solves puzzle using hidden singles method
        :param row: Row location of most recent added value
        :param col: Column location of most recently added value
        :return: number of affected cells solved with hidden singles
        """
        path = UpdatePath(row, col)
        num_added = self.solve_hidden(path.rows)
        num_added += self.solve_hidden(path.cols)
        num_added += self.solve_hidden(path.sqrs)

        return num_added


    def solve_hidden(self, subsection):
        """
        Solves given puzzle subsection using hidden singles method
        :param subsection: 2x9 ndarray contain index locations in subset
        :return: number of elements solved in subsection
        """
        num_pos = np.zeros(9) # Number of possible placements for each digit
        location = {} # Most recently found placement for each digit
        num_added = 0

        # Count number of possible placements for each digit
        for i in range(9): # Iterate through cells in subsection
            this_row = subsection[0][i]
            this_col = subsection[1][i]
            for j in range(9): # Check possible values for empty cells
                if self.board[this_row][this_col] == 0 and self.element_vals[this_row][this_col][j]:
                    num_pos[j] += 1
                    location[j] = this_row, this_col

        # Update board with found cells
        for i, num_locs in enumerate(num_pos):
            if num_locs == 1:
                # DEBUG
                print(f'\n\n\nSolution Found!: ({location[i]}) = {i} ')
                print(self.element_vals)
                # END DEBUG

                num_added += 1
                update_row, update_col = location[i]
                self.update(update_row, update_col, i + 1)

                # DEBUG
                print(self.board)
                print('\n\n\n')
                # END DEBUG

        return num_added

    def undo(self):
        """
        Resets board to previous state by undoing last move
        :return: True if state could be reverted, False if no further backtracking possible
        """
        if len(self.prev_states) < 1:
            return False

        prev = self.prev_states.pop()
        val = self.board[prev.row][prev.col]
        if val < 0:
            self.num_clues -= 1
        self.num_vals -= 1
        for i in prev.update_elements:
            self.element_vals[i[0]][i[1]][i[2]] = True

        return True

    def track_pos_updates(self, row, col, val):
        self.prev_states[-1].update(row, col, val)

puzzle = PuzzleBuilder()
easy = puzzle.get_easy()
print(easy)



