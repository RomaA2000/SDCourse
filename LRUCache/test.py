from cache import cache


@cache(20)
def binomial(n, k):
    if k == 0:
        return 1
    if k > n:
        return 0
    return binomial(n - 1, k) + binomial(n - 1, k - 1)


@cache(512)
def fibonacci(n):
    print(f'counting for {n}')
    if n <= 1:
        return 1
    return fibonacci(n - 1) + fibonacci(n - 2)


def test_cache_not_changes_func():
    @cache(0)
    def func(a):
        """something"""
        return a

    assert func.__name__ == 'func'
    assert func.__doc__ == 'something'
    assert func.__module__ == __name__


def test_binomial_easy():
    result = sum(binomial(4, i) for i in range(5))
    assert result == 2 ** 4


def test_binomial_hard():
    result = sum(binomial(30, i) for i in range(31))
    assert result == 2 ** 30


def test_fibonacci_easy(capsys):
    result = fibonacci(5)
    assert result == 8
    assert capsys.readouterr().out.count('\n') == 6


def test_fibonacci_hard(capsys):
    result = fibonacci(64)
    assert result == 17167680177565
    assert capsys.readouterr().out.count('\n') == 59  # 59 because of previous test
