public class Car {
    String model;
    int year;

    // Constructor
    public Car(String model, int year) {
        this.model = model;
        this.year = year;
    }

    // Method
    public void start() {
        System.out.println(model + " is starting.");
    }

    // Main method: Entry point of the program
    public static void main(String[] args) {
        Car myCar = new Car("Toyota", 2020);
        myCar.start();  // Output: Toyota is starting.
    }
}


// Encapsulation
public class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}


// Inheritance
class Animal {
    public void eat() {
        System.out.println("This animal is eating.");
    }
}

class Dog extends Animal {
    public void bark() {
        System.out.println("The dog is barking.");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat();  // Inherited method
        dog.bark(); // Dog's own method
    }
}


// Polymorphism
class Animal {
    public void sound() {
        System.out.println("This animal makes a sound.");
    }
}

class Dog extends Animal {
    @Override
    public void sound() {
        System.out.println("The dog barks.");
    }
}


// Abstraction 
abstract class Shape {
    abstract void draw();
}

class Circle extends Shape {
    void draw() {
        System.out.println("Drawing a circle.");
    }
}



