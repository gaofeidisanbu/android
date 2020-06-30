package com.gaofei.library;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.function.Supplier;

/**
 * Created by gaofei on 09/01/2018.
 */

public class Testmali {
    public static class Car {
        @RequiresApi(api = Build.VERSION_CODES.N)
        public static Car create(final Supplier<Car> supplier) {
            return supplier.get();
        }

        public static void collide(final Car car) {
            System.out.println("Collided " + car.toString());
        }

        public void follow(final Car another) {
            System.out.println("Following the " + another.toString());
        }

        public void repair() {
            System.out.println("Repaired " + this.toString());
        }
    }

    public static void main(String[] args) {
        Car car = Car.create(Car::new);
        car.repair();
    }
}
