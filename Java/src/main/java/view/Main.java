package view;

import controller.AnimalController;
import repository.ArrayAnimalRepository;

public class Main {
    public static void main(String[] args) {
        AnimalController controller = new AnimalController(new ArrayAnimalRepository(20));
        new UI(controller).run();
    }
}
