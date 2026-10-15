package view;

import controller.AnimalController;
import model.Animal;
import model.Bird;
import model.Cow;
import model.Pig;
import repository.RepositoryException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    private final AnimalController controller;
    private final Scanner scanner = new Scanner(System.in);

    public UI(AnimalController controller) {
        this.controller = controller;
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n1. Add animal\n" +
                    "2. Remove animal\n" +
                    "3. Show all animals\n" +
                    "4. Show animals heavier than 3 kg\n" +
                    "0. Exit");
            System.out.print("Option: ");
            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1: addAnimal(); break;
                    case 2: removeAnimal(); break;
                    case 3: print(controller.getAllAnimals()); break;
                    case 4: print(controller.getAnimalsHeavierThan(3)); break;
                    case 0: running = false; break;
                    default: System.out.println("Unknown option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number.");
                scanner.nextLine();
            } catch (RepositoryException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addAnimal() throws RepositoryException {
        System.out.print("Type (1 = Bird, 2 = Cow, 3 = Pig): ");
        int type = scanner.nextInt();
        System.out.print("Weight (kg): ");
        double weight = scanner.nextDouble();
        if (weight <= 0) {
            throw new RepositoryException("Weight must be positive");
        }
        switch (type) {
            case 1: controller.addAnimal(new Bird(weight)); break;
            case 2: controller.addAnimal(new Cow(weight)); break;
            case 3: controller.addAnimal(new Pig(weight)); break;
            default: throw new RepositoryException("Unknown animal type: " + type);
        }
    }

    private void removeAnimal() throws RepositoryException {
        System.out.print("Position (starting from 0): ");
        controller.removeAnimal(scanner.nextInt());
    }

    public static void print(Animal[] animals) {
        if (animals.length == 0) {
            System.out.println("(no animals)");
            return;
        }
        for (Animal a : animals) {
            System.out.println(a);
        }
    }
}
