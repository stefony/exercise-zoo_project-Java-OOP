package zoo.entities.areas;

import zoo.entities.animals.Animal;

public  class WaterArea extends BaseArea{

    private static final int CAPACITY = 10;

    public WaterArea(String name) {
        super(name, CAPACITY);
    }


    @Override
    public boolean hasCapacityFor(Animal newAnimal) {
        return true;
    }

    @Override
    public boolean isEnvironmentSuitableFor(Animal newAnimal) {
        return true;
    }

    @Override
    public int feedAllAnimals() {
        return 0;
    }

    @Override
    public double calculateTotalWeight() {
        return 0;
    }

    @Override
    public int getFoodCount() {
        return 0;
    }

    @Override
    public double calculateTotalCalories() {
        return 0;
    }
}
