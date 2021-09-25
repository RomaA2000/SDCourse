class Node:
    def __init__(self, left=None, right=None, data=None):
        assert (right is not left) or (right is None and left is None), "loop in nodes structure is prohibited"
        self._right = right
        self._left = left
        self.data = data

    @property
    def next(self):
        return self._right

    @property
    def prev(self):
        return self._left

    def remove(self):
        if self.prev is not None:
            self.prev._right = self.next
        if self.next is not None:
            self.next._left = self.prev
        self._right = None
        self._left = None
        assert self._right is None and self._left is None, "after removing node it should not link somewhere"

    def insert_after(self, data):
        new_node = Node(self, self.next, data)
        if self.next is not None:
            self._right._left = new_node
        self._right = new_node
        assert new_node.prev == self, "inserting after means previous should be this node"
        return new_node

    def insert_before(self, data):
        new_node = Node(self.prev, self, data)
        if self.prev is not None:
            self._left._right = new_node
        self._left = new_node
        assert new_node.next == self, "inserting before means next should be this node"
        return new_node