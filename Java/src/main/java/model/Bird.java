package model;

public class Bird implements Animal {
    private final double weight;

    public Bird(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return "Bird";
    }

    @Override
    public String toString() {
        return getName() + " (" + weight + " kg)";
    }
}
