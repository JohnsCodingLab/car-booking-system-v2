package com.johnscodinglab.car;

import java.math.BigDecimal;
import java.util.List;

public class CarDAO {
    private static final List<Car> CARS = List.of(
            new Car("1234", Brand.TESLA, new BigDecimal(80), false),
            new Car("6789", Brand.TOYOTA, new BigDecimal(20), false),
            new Car("2345", Brand.LEXUS, new BigDecimal(100), false),
            new Car("5432", Brand.HONDA, new BigDecimal(50), false)
    );


    public List<Car> getCars() {
        return CARS;
    }
}
