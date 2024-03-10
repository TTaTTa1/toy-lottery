import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;

class Toy {
    int id;
    String name;
    int frequency;

    public Toy(int id, String name, int frequency) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFrequency() {
        return frequency;
    }
}

public class ToyStore {

    public static void main(String[] args) {
        // Создаем коллекцию PriorityQueue для хранения игрушек
        PriorityQueue<Toy> toysQueue = new PriorityQueue<>((t1, t2) -> t2.getFrequency() - t1.getFrequency());

        // Добавляем игрушки в очередь
        addToy(toysQueue, "1 2 конструктор");
        addToy(toysQueue, "2 2 робот");
        addToy(toysQueue, "3 6 кукла");

        // Вызываем метод Get 10 раз и записываем результат в файл
        try {
            FileWriter writer = new FileWriter("output.txt");
            for (int i = 0; i < 10; i++) {
                int toyId = getToy(toysQueue);
                writer.write(Integer.toString(toyId) + "\n");
            }
            writer.close();
            System.out.println("Результаты записаны в файл output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для добавления игрушки в очередь
    public static void addToy(PriorityQueue<Toy> toysQueue, String toyString) {
        String[] parts = toyString.split(" ");
        int id = Integer.parseInt(parts[0]);
        int frequency = Integer.parseInt(parts[1]);
        String name = parts[2];
        Toy toy = new Toy(id, name, frequency);
        toysQueue.add(toy);
    }

    // Метод для получения случайной игрушки в соответствии с её весом
    public static int getToy(PriorityQueue<Toy> toysQueue) {
        int totalFrequency = toysQueue.stream().mapToInt(Toy::getFrequency).sum();
        int randomNumber = (int) (Math.random() * totalFrequency) + 1;
        int sum = 0;
        for (Toy toy : toysQueue) {
            sum += toy.getFrequency();
            if (randomNumber <= sum) {
                return toy.getId();
            }
        }
        return -1; // Вернуть -1 в случае ошибки
    }
}