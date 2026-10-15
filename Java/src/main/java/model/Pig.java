package model;

public class Pig implements Animal {
    private final double weight;

    public Pig(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return "Pig";
    }

    @Override
    public String toString() {
        return getName() + " (" + weight + " kg)";
    }
}
