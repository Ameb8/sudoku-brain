import numpy as np

class SolvedBuilder:
    def __init__(self):
        self.puzzle = np.zeros((9, 9), dtype=int)
        self.row_vals = np.zeros((9, 9), dtype=bool)
        self.col_vals = np.zeros((9, 9), dtype=bool)
        self.sqr_cals = np.zeros((9, 9), dtype=bool)
        self.empty_cells = 81

    def build_solved(self):
        self.add_clue()

    def add_clue(self):
        row = np.random

    def get_sqr(self, row, col):
         return (row // 3) * 3 + col // 3