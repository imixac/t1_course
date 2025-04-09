package ru.t1.task2;

import ru.t1.task2.enums.Profession;

import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        getThreeOfMax();
        System.out.println("=========================");
        findMaxUniqThree();
        System.out.println("=========================");
        getThreeOldEngineerOrderDesc();
        System.out.println("=========================");
        getMiddleAgeByEngineer();
        System.out.println("=========================");
        getLongWord();
        System.out.println("=========================");
        createdRepeatWords("qwe qwe qwe sd asd xzc 123 we asd qwe");
        System.out.println("=========================");
        softByLengthAndByAlphabet();
        System.out.println("=========================");
        maxWordByListList();
    }

    //    Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
    static void getThreeOfMax() {
        int threeMax = Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13)
                .sorted(Comparator.reverseOrder())
                .toList().get(2);
        System.out.println(threeMax);
    }

    //    Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9, в отличие от прошлой задачи здесь разные 10 считает за одно число)
    static void findMaxUniqThree() {
        int threeUniqMax = Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList().get(2);
        System.out.println(threeUniqMax);

    }

    //    Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
    static void getThreeOldEngineerOrderDesc() {
        List<Worker> workers = new ArrayList<>(Arrays.asList(
                new Worker("Misha1", 33, Profession.ENGINEER),
                new Worker("Misha2", 55, Profession.DIRECTOR),
                new Worker("Misha3", 25, Profession.MANAGER),
                new Worker("Misha4", 22, Profession.ENGINEER),
                new Worker("Misha5", 27, Profession.ENGINEER),
                new Worker("Misha6", 23, Profession.ENGINEER),
                new Worker("Misha7", 45, Profession.ENGINEER)
        ));

        List<String> engineerNames = workers.stream()
                .filter(worker -> worker.getProfession() == Profession.ENGINEER)
                .sorted(Comparator.comparing(Worker::getAge).reversed())
                .map(Worker::getName)
                .toList().subList(0, 3);

        System.out.println(engineerNames);
    }

    //    Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст сотрудников с должностью «Инженер»
    static void getMiddleAgeByEngineer() {
        List<Worker> workers = new ArrayList<>(Arrays.asList(
                new Worker("Misha1", 33, Profession.ENGINEER),
                new Worker("Misha2", 55, Profession.DIRECTOR),
                new Worker("Misha3", 25, Profession.MANAGER),
                new Worker("Misha4", 22, Profession.ENGINEER),
                new Worker("Misha5", 27, Profession.ENGINEER),
                new Worker("Misha6", 23, Profession.ENGINEER),
                new Worker("Misha7", 45, Profession.ENGINEER)
        ));

        OptionalDouble avg = workers.stream()
                .filter(worker -> worker.getProfession() == Profession.ENGINEER)
                .mapToInt(Worker::getAge).average();
        System.out.println(avg.getAsDouble());
    }

    //    Найдите в списке слов самое длинное
    static void getLongWord() {
        String[] words = {"qwe", "qweqwe", "asd", "q"};

        String longWord =Arrays.stream(words)
                .max(Comparator.comparingInt(String::length)).get();

        System.out.println(longWord);
    }

    //    Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке
    static void createdRepeatWords(String str) {
        Map<String, Integer> repeatedWords = new HashMap<>();
        Arrays.stream(str.split(" ")).forEach(w -> {
            if (!repeatedWords.containsKey(w)) {
                repeatedWords.put(w, 1);
            } else {
                repeatedWords.put(w, repeatedWords.get(w) + 1);
            }
        });

        System.out.println(repeatedWords);
    }

    //    Отпечатайте в консоль строки из списка в порядке увеличения длины слова, если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок
    static void softByLengthAndByAlphabet() {
        String[] words = {"qwe", "qweqwe", "asd", "q"};
        Arrays.stream(words)
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);
    }

    //    Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом, найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них
    static void maxWordByListList() {
        String[] words = {
                "qwe asd fvf dsfre dsdfsdfs",
                "qweqwe s d dsadasd defw",
                "asd df df df sdfsdfsdfsdf",
                "sdfsdfsdfsdf asd df df df"
        };

        Optional<Optional<String>> s = Arrays.stream(words)
                .map(o -> Arrays.stream(o.split(" "))
                        .max(Comparator.comparingInt(String::length)))
                .findFirst();

        System.out.println(s.get().get());
    }
}
