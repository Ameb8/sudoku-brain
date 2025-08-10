import numpy as np

# Holds locations of all values affected by an update in board[row][col]
class UpdatePath:
    def __init__(self, row_val, col_val):
        self.rows = self.get_row(row_val)
        self.cols = self.get_col(col_val)
        self.sqrs = self.get_sqr(row_val, col_val)

    def get_row(self, row_val):
        row = np.zeros((2, 9), dtype=int)
        row[0, :] = row_val
        row[1, :] = np.arange(0, 9)
        return row

    def get_col(self, col_val):
        col = np.zeros((2, 9), dtype=int)
        col[0, :] = np.arange(0, 9)
        col[1, :] = col_val
        return col

    def get_sqr(self, row_val, col_val):
        # Get location of 3 x 3 square
        sqr = np.zeros((2, 9), dtype=int)
        sqr_row = row_val // 3
        sqr_col = col_val // 3

        for i in range(9): # Get indices of elements in square
            sqr[0][i] = (i // 3) + (sqr_row * 3)
            sqr[1][i] = (i % 3) + (sqr_col * 3)

        return sqr

    def __str__(self):
        str = []
        str.append(f'\nrows: {self.row}')
        str.append(f'cols: {self.col}')
        str.append(f'sqr: {self.sqr}')
        return '\n'.join(str)
