class Update:
    def __init__(self, row, col):
        # Location of inserted value
        self.row = row
        self.col = col
        # Holds row,col,val tuples of possibilities removed from board[row][col]
        self.update_elements = []

    def update(self, row, col, val):
        """
        Tracks when a board update causes a value to be impossible for a location
        :param row:
        :param col:
        :param val:
        :return:
        """
        self.update_elements.append((row, col, val))
