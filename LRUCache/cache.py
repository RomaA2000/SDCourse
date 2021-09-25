import functools

from node import Node


class LRUCache:
    def __init__(self, size_limit):
        self.size_limit = size_limit
        self.tail = None
        self.head = None
        self.dict = dict()

    def _add_to_head(self, data):
        if self.head is None:
            self.tail = Node(data=data)
            self.head = self.tail
            assert self.head is self.tail, "one element list"
        else:
            self.head = self.head.insert_before(data)

    def _remove_from_tail(self):
        if self.tail is not None:
            if self.head is self.tail:
                self.tail.remove()
                self.head = None
                self.tail = None
            else:
                new_tail = self.tail.prev
                self.tail.remove()
                self.tail = new_tail

    def _add_key_value(self, key, value):
        assert key not in self.dict, "key should not be in dict"
        self._add_to_head((key, value))
        assert self.head.data == (key, value)
        self.dict[key] = self.head
        assert key in self.dict

    def __getitem__(self, key):
        assert key in self.dict, "key should be in dict"
        self.move_to_front(key)
        return self.dict[key].data[1]

    def move_to_front(self, key):
        assert key in self.dict, "key should be added before function call"
        node = self.dict[key]
        key, value = node.data
        node.remove()
        self.dict.pop(key)
        self._add_key_value(key, value)
        assert key in self.dict, "key should be added after function call"
        assert self.dict[key].data == (key, value), "data in dict should be equal to data at start"
        assert self.head.data == (key, value), "data should be in head after function call"

    def __setitem__(self, key, value):
        if key in self.dict:
            self.dict[key].data = (key, value)
            self.move_to_front(key)
        else:
            if len(self.dict) > self.size_limit:
                self._remove_from_tail()
            self._add_key_value(key, value)
        assert self.head.data == (key, value), "data in head node should be equal to data passed in function"
        assert self.dict[key].data == (key, value), "data in dict should be equal to data passed in function"

    def __contains__(self, key):
        return key in self.dict

    def __str__(self):
        result = "{"
        list_iter = self.head
        while list_iter is not None:
            result += " " + str(list_iter.data) + " "
            assert list_iter.data[0] in self.dict, "keys in all nodes should be in dict"
            assert list_iter == self.dict[list_iter.data[0]], "data in all nodes should be equal to data in dict"
            list_iter = list_iter.next
        result += "}"
        return result


def cache(max_size):

    def wraps(func):
        cacher = LRUCache(max_size)

        @functools.wraps(func)
        def wrapper(*args, **kwargs):
            key = str(args) + str(kwargs)
            if key in cacher:
                result = cacher[key]
            else:
                result = func(*args, **kwargs)
                cacher[key] = result
            return result

        return wrapper

    return wraps
