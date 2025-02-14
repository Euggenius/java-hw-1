import java.util.*;

interface IAlive {
    int getFood();
}

interface IInventory {
    int getNumber();
    void setNumber(int number);
}

abstract class Animal implements IAlive, IInventory {
    private int Number;
    protected String species;
    protected int Food;

    public Animal(String species, int Food) {
        this.species = species;
        this.Food = Food;
    }

    @Override
    public int getFood() {
        return Food;
    }

    @Override
    public int getNumber() {
        return Number;
    }

    @Override
    public void setNumber(int number) {
        this.Number = number;
    }

    public String getSpecies() {
        return species;
    }
}

abstract class Herbo extends Animal {
    protected int Kindness;

    public Herbo(String species, int Food, int Kindness) {
        super(species, Food);
        this.Kindness = Kindness;
    }

    public boolean isContactAllowed() {
        return Kindness > 5;
    }
}

abstract class Predator extends Animal {
    public Predator(String species, int Food) {
        super(species, Food);
    }
}

class Monkey extends Herbo {
    public Monkey(int Food, int Kindness) {
        super("Обезьяна", Food, Kindness);
    }
}

class Rabbit extends Herbo {
    public Rabbit(int Food, int Kindness) {
        super("Кролик", Food, Kindness);
    }
}

class Tiger extends Predator {
    public Tiger(int Food) {
        super("Тигр", Food);
    }
}

class Wolf extends Predator {
    public Wolf(int Food) {
        super("Волк", Food);
    }
}

abstract class Thing implements IInventory {
    protected String Name;
    private int Number;

    public Thing(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    @Override
    public int getNumber() {
        return Number;
    }

    @Override
    public void setNumber(int number) {
        this.Number = number;
    }
}

class Table extends Thing {
    public Table(String Name) {
        super(Name);
    }
}

class Computer extends Thing {
    public Computer(String Name) {
        super(Name);
    }
}

class VetClinic {
    private Scanner scanner = new Scanner(System.in);
    public boolean checkAnimal(Animal animal) {
        System.out.println("Проверка здоровья животного: " + animal.getSpecies());
        System.out.print("Введите состояние животного (здоров/не здоров): ");
        String status = scanner.nextLine();
        return status.trim().equalsIgnoreCase("здоров");
    }
}

class Zoo {
    private VetClinic vetClinic;
    private List<Animal> animals = new ArrayList<>();
    private List<Thing> things = new ArrayList<>();
    private int nextNumber = 1;

    public Zoo(VetClinic vetClinic) {
        this.vetClinic = vetClinic;
    }

    public boolean admitAnimal(Animal animal) {
        if (vetClinic.checkAnimal(animal)) {
            animal.setNumber(nextNumber++);
            animals.add(animal);
            System.out.println("Животное " + animal.getSpecies() + " принято в зоопарк.");
            return true;
        } else {
            System.out.println("Животное " + animal.getSpecies() + " не прошло проверку.");
            return false;
        }
    }

    public void addThing(Thing thing) {
        thing.setNumber(nextNumber++);
        things.add(thing);
        System.out.println("Вещь " + thing.getName() + " добавлена.");
    }

    public int getTotalFood() {
        int total = 0;
        for (Animal animal : animals) {
            total += animal.getFood();
        }
        return total;
    }

    public List<Animal> getContactZooAnimals() {
        List<Animal> contactAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal instanceof Herbo) {
                Herbo herbo = (Herbo) animal;
                if (herbo.isContactAllowed()) {
                    contactAnimals.add(animal);
                }
            }
        }
        return contactAnimals;
    }

    public List<IInventory> getAllInventoryItems() {
        List<IInventory> items = new ArrayList<>();
        items.addAll(animals);
        items.addAll(things);
        return items;
    }
}

public class ZooApp{
    public static void main(String[] args) {
        VetClinic vetClinic = new VetClinic();
        Zoo zoo = new Zoo(vetClinic);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить животное");
            System.out.println("2. Добавить вещь");
            System.out.println("3. Отчет по еде");
            System.out.println("4. Контактный зоопарк");
            System.out.println("5. Инвентаризация (животные и вещи)");
            System.out.println("6. Выход");
            System.out.print("Выберите опцию: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addAnimal(zoo, scanner);
                    break;
                case "2":
                    addThing(zoo, scanner);
                    break;
                case "3":
                    System.out.println("Общий расход еды: " + zoo.getTotalFood() + " кг");
                    break;
                case "4":
                    List<Animal> contactAnimals = zoo.getContactZooAnimals();
                    if (contactAnimals.isEmpty()) {
                        System.out.println("Нет животных для контактного зоопарка.");
                    } else {
                        for (Animal animal : contactAnimals) {
                            System.out.println(animal.getSpecies() + " #" + animal.getNumber());
                        }
                    }
                    break;
                case "5":
                    List<IInventory> inventory = zoo.getAllInventoryItems();
                    if (inventory.isEmpty()) {
                        System.out.println("Инвентаризация пуста.");
                    } else {
                        for (IInventory item : inventory) {
                            if (item instanceof Animal) {
                                Animal animal = (Animal) item;
                                System.out.println("Животное: " + animal.getSpecies() + " #" + animal.getNumber());
                            } else if (item instanceof Thing) {
                                Thing thing = (Thing) item;
                                System.out.println("Вещь: " + thing.getName() + " #" + thing.getNumber());
                            }
                        }
                    }
                    break;
                case "6":
                    System.out.println("Выход.");
                    return;
                default:
                    System.out.println("Неверная опция.");
            }
        }
    }

    private static void addAnimal(Zoo zoo, Scanner scanner) {
        System.out.println("Выберите тип животного:");
        System.out.println("1. Обезьяна");
        System.out.println("2. Кролик");
        System.out.println("3. Тигр");
        System.out.println("4. Волк");
        System.out.print("Опция: ");
        String type = scanner.nextLine();

        Animal animal = null;
        switch (type) {
            case "1":
                System.out.print("Введите количество еды: ");
                int food1 = Integer.parseInt(scanner.nextLine());
                System.out.print("Введите уровень доброты: ");
                int kindness1 = Integer.parseInt(scanner.nextLine());
                animal = new Monkey(food1, kindness1);
                break;
            case "2":
                System.out.print("Введите количество еды: ");
                int food2 = Integer.parseInt(scanner.nextLine());
                System.out.print("Введите уровень доброты: ");
                int kindness2 = Integer.parseInt(scanner.nextLine());
                animal = new Rabbit(food2, kindness2);
                break;
            case "3":
                System.out.print("Введите количество еды: ");
                int food3 = Integer.parseInt(scanner.nextLine());
                animal = new Tiger(food3);
                break;
            case "4":
                System.out.print("Введите количество еды: ");
                int food4 = Integer.parseInt(scanner.nextLine());
                animal = new Wolf(food4);
                break;
            default:
                System.out.println("Неверный тип животного.");
                return;
        }
        zoo.admitAnimal(animal);
    }

    private static void addThing(Zoo zoo, Scanner scanner) {
        System.out.println("Выберите тип вещи:");
        System.out.println("1. Стол");
        System.out.println("2. Компьютер");
        System.out.print("Опция: ");
        String type = scanner.nextLine();
        System.out.print("Введите название вещи: ");
        String name = scanner.nextLine();

        Thing thing = null;
        switch (type) {
            case "1":
                thing = new Table(name);
                break;
            case "2":
                thing = new Computer(name);
                break;
            default:
                System.out.println("Неверный тип вещи.");
                return;
        }
        zoo.addThing(thing);
    }
}
