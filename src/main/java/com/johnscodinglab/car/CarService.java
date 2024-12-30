package com.johnscodinglab.car;

import java.util.List;

public class CarService{
    private final CarDAO carDAO;

    public CarService(CarDAO carDAO){
        this.carDAO = carDAO;
    }

    public List<Car> getAllCars() {
        return carDAO.getCars();
    }

    public Car getCar(String plateNumber) {
        List<Car> cars = getAllCars();
        return cars.stream()
                .filter(car -> car.getPlateNumber().equals(plateNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No Car found with plate number " + plateNumber));
    }

    public List<Car> getAllElectricCars() {
        List<Car> cars = getAllCars();
        return cars.stream().filter(Car::isElectric).toList();
    }
}
