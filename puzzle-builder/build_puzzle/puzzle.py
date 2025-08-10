import numpy as np

class Puzzle:
    def __init__(self):
        self.board = np.zeros((9, 9), dtype=int)
        self.num_clues = 0

