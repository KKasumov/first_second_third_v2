import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Foo {
    private final Lock lock = new ReentrantLock();
    private final Condition firstCondition = lock.newCondition();
    private final Condition secondCondition = lock.newCondition();
    private boolean isFirstCalled;
    private boolean isSecondCalled;

    public void first() throws InterruptedException {
        lock.lock();
        try {
            // Реализация метода first()
            System.out.println("First");
            isFirstCalled = true;
            firstCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void second() throws InterruptedException {
        lock.lock();
        try {
            while (!isFirstCalled) {
                firstCondition.await();
            }
            // Реализация метода second()
            System.out.println("Second");
            isSecondCalled = true;
            secondCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void third() throws InterruptedException {
        lock.lock();
        try {
            while (!isSecondCalled) {
                secondCondition.await();
            }
            // Реализация метода third()
            System.out.println("Third");
        } finally {
            lock.unlock();
        }
    }
}


/*
Этот код показывает класс `Foo`, который использует механизмы блокировки
и условия для синхронизации выполнения трех методов: `first()`, `second()` и `third()`.



private final Lock lock = new ReentrantLock();
private final Condition firstCondition = lock.newCondition();
private final Condition secondCondition = lock.newCondition();
private boolean isFirstCalled;
private boolean isSecondCalled;
В этих строках создаются объекты блокировки `Lock` и
условия `Condition`. Блокировка используется для синхронизации доступа к общим ресурсам,
а условия позволяют потокам ожидать определенных событий.


public void first() throws InterruptedException {
        lock.lock();
        try {
        // Реализация метода first()
        System.out.println("First");
        isFirstCalled = true;
        firstCondition.signal();
        } finally {
        lock.unlock();
        }
        }
Метод `first()` блокирует блокировку, чтобы предотвратить доступ других потоков.
Затем он выводит сообщение "First", устанавливает флаг `isFirstCalled` в `true`
и сигнализирует условию `firstCondition`. Это позволяет ожидающему
потоку `second()` продолжить выполнение.


public void second() throws InterruptedException {
        lock.lock();
        try {
        while (!isFirstCalled) {
        firstCondition.await();
        }
        // Реализация метода second()
        System.out.println("Second");
        isSecondCalled = true;
        secondCondition.signal();
        } finally {
        lock.unlock();
        }
        }
Метод `second()` сначала проверяет флаг `isFirstCalled`.
Если он `false`, то поток вызывает `await()` для условия `firstCondition`,
чтобы ожидать сигнала от метода `first()`. Когда поток получает сигнал,
он выполняет свою реализацию, выводя сообщение "Second",
устанавливает флаг `isSecondCalled` в `true` и сигнализирует условию `secondCondition`.


public void third() throws InterruptedException {
        lock.lock();
        try {
        while (!isSecondCalled) {
        secondCondition.await();
        }
        // Реализация метода third()
        System.out.println("Third");
        } finally {
        lock.unlock();
        }
        }
Метод `third()` проверяет флаг `isSecondCalled`.
Если он `false`, то поток вызывает `await()` для условия `secondCondition`,
чтобы ожидать сигнала от метода `second()`.
Когда поток получает сигнал, он выполняет свою реализацию, выводя сообщение "Third".

В итоге, при запуске трех потоков из класса `Main`.
Метод `first()` будет выполняться первым, затем метод `second()`, а затем метод `third()`.
*/