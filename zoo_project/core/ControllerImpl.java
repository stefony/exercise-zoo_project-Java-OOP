package zoo.core;

import zoo.entities.animals.Animal;
import zoo.entities.animals.AquaticAnimal;
import zoo.entities.animals.TerrestrialAnimal;
import zoo.entities.areas.Area;
import zoo.entities.areas.LandArea;
import zoo.entities.areas.WaterArea;
import zoo.entities.foods.Food;
import zoo.entities.foods.Meat;
import zoo.entities.foods.Vegetable;
import zoo.repositories.FoodRepository;
import zoo.repositories.FoodRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static zoo.common.ConstantMessages.*;
import static zoo.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {


     private FoodRepository foodRepository;
     private Collection<Area>areas;

    public ControllerImpl() {
   this.foodRepository= new FoodRepositoryImpl();
   this.areas = new ArrayList<>();

    }

    @Override
    public String addArea(String areaType, String areaName) {
        Area newArea;

        switch (areaType) {
            case "WaterArea":
                newArea = new WaterArea(areaName);
                break;
            case "LandArea":
                newArea = new LandArea(areaName);
                break;
            default:
                throw new NullPointerException(INVALID_AREA_TYPE);
        }

        areas.add(newArea);
        return String.format(SUCCESSFULLY_ADDED_AREA_TYPE,areaType);
    }

    //INVALID_AREA_TYPE
    //SUCCESSFULLY_ADDED_AREA_TYPE,areaType
    @Override
    public String buyFood(String foodType) {
        if (!"Vegetable".equals(foodType) && !"Meat".equals(foodType)) {
            throw new IllegalArgumentException(INVALID_FOOD_TYPE);
        }
        foodRepository.addFood(foodType);
        return String.format(SUCCESSFULLY_ADDED_FOOD_TYPE,foodType);
    }

    @Override
    public String foodForArea(String areaName, String foodType) {
        Area area = findAreaByName(areaName);
        if (!foodRepository.hasFoodType(foodType)) {
            throw new IllegalArgumentException(String.format(NO_FOOD_FOUND,foodType));
        }
        Food food = createFoodByType(foodType);
        area.addFood(food);
        foodRepository.removeFood(foodType);
        return String.format(SUCCESSFULLY_ADDED_FOOD_IN_AREA,foodType,areaName);
    }

    private Food createFoodByType(String foodType) {
        if ("Vegetable".equals(foodType)) {
            return new Vegetable();
        } else if ("Meat".equals(foodType)) {
            return new Meat();
        } else {
            throw new IllegalArgumentException("Invalid food type.");
        }
    }

    private Area findAreaByName(String areaName) {
        return areas.stream()
                .filter(area -> area.getName().equals(areaName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Area not found."));
    }

    @Override
    public String addAnimal(String areaName, String animalType, String animalName, String kind, double price) {
        Area area = findAreaByName(areaName);
        Animal newAnimal;
        if ("AquaticAnimal".equals(animalType)) {
            newAnimal = new AquaticAnimal(animalName, kind, price);
        } else if ("TerrestrialAnimal".equals(animalType)) {
            newAnimal = new TerrestrialAnimal(animalName, kind, price);
        } else {
            throw new IllegalArgumentException(INVALID_ANIMAL_TYPE);
        }

        if (!area.hasCapacityFor(newAnimal)) {
            return NOT_ENOUGH_CAPACITY;
        } else if (!area.isEnvironmentSuitableFor(newAnimal)) {
            return AREA_NOT_SUITABLE;
        } else {
            area.addAnimal(newAnimal);
            return String.format(SUCCESSFULLY_ADDED_ANIMAL_IN_AREA,animalType,areaName);
        }
    }



    @Override
    public String feedAnimal(String areaName) {
        Area area = findAreaByName(areaName);
        int fedCount = area.feedAllAnimals();
        return "Animals fed: " + fedCount;
    }

    @Override
    public String calculateKg(String areaName) {
        Area area = findAreaByName(areaName);
        double value = area.calculateTotalWeight();
        return String.format(KILOGRAMS_AREA,areaName,value);
    }

    @Override
    public String getStatistics() {
        return areas.stream()
                .map(this::areaStatistics)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String areaStatistics(Area area) {
        String animalNames = area.getAnimals().isEmpty() ? "none" : area.getAnimals().stream()
                .map(Animal::getName)
                .collect(Collectors.joining(" "));
        int foodCount = area.getFoodCount();
        double areaCalories = area.calculateTotalCalories();

        return String.format("%s (%s):%nAnimals: %s%nFoods: %d%nCalories: %.2f",
                area.getName(), area.getClass().getSimpleName(), animalNames, foodCount, areaCalories);
    }
}
