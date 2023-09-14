public class Main {
    public static void main(String[] args) {
        Foo foo = new Foo();

        Thread threadA = new Thread(() -> {
            try {
                foo.first();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                foo.second();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                foo.third();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }
}

/*
Класс создает экземпляр класса `Foo` и запускает три потока
(`threadA`, `threadB` и `threadC`),
каждый из которых вызывает соответствующие методы `first()`, `second()` и `third()`
объекта `foo`. Таким образом, методы будут вызываться в нужном порядке
и выводить соответствующие сообщения.
Когда код запускается, поток `threadA` вызывает метод `first()`,
который выводит "First" и сигнализирует условию `firstCondition`.
После этого поток `threadB` вызывает метод `second()`, который ожидает
сигнала от `firstCondition`, после получения сигнала выводит "Second"
и сигнализирует условию `secondCondition`.
Затем поток `threadC` вызывает метод `third()`, который ожидает
сигнала от `secondCondition` и после получения сигнала выводит "Third".

Результат выполнения кода будет:

        First
        Second
        Third
 */
