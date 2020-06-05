package collection;

import application.Context;
import elements.Car;
import elements.HumanBeing;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;


public class HumanList {
    private LinkedList<HumanBeing> humans;
    private Context context;

    private ReadWriteLock readWriteLock;
    private Lock readLock;
    private Lock writeLock;

    public HumanList(Context context) {
        humans = new LinkedList<>();
        this.context = context;
        readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    public LinkedList<HumanBeing> getHumans() {
        return humans;
    }

    public void addFromDatabase(HumanBeing human) {
        humans.add(human);
    }

    public String addHuman(HumanBeing human, int idUser) {
        writeLock.lock();

        human.setCreationDate();
        human.setIdUser(idUser);
        try {
            human = context.handlerDatabase.addHumanBeing(human);
            humans.add(human);
            writeLock.unlock();
            return "Элемент добавлен.";
        } catch (SQLException e) {
            writeLock.unlock();
            return "Элемент не удалось добавить в базу данных.";
        }
    }

    public void remove(HumanBeing human) {
        writeLock.lock();
        try {
            context.handlerDatabase.removeHuman(human);
        } catch (SQLException e) {
            System.out.println("kek");
            writeLock.unlock();
            return;
        }
        humans.remove(human);
        writeLock.unlock();
    }

    public String clear(int idUser) {
        writeLock.lock();
        for (int i = 0; i < humans.size(); ++i) {
            if(humans.get(i).getIdUser() == idUser) {
                remove(humans.get(i));
                i--;
            }
        }
        writeLock.unlock();
        return "Ваши элементы удалены.";
    }

    public String removeHead(int idUser) {
        writeLock.lock();
        try {
            if (humans.getFirst().getIdUser() == idUser) {
                remove(humans.getFirst());
                writeLock.unlock();
                return "Первый элемент коллекции удален.";
            }
            else {
                writeLock.unlock();
                return "Элемент удалить не удалось, т.к. он вам не принадлежит.";
            }
        }
        catch (Exception e) {
            writeLock.unlock();
            return "Коллекция пуста, первого элемент удалить не удалось.";
        }
    }

    public String removeById(int id, int idUser) {
        writeLock.lock();
        for (HumanBeing human : humans) {
            if(human.getId() == id) {
                if(human.getIdUser() != idUser) {
                    writeLock.unlock();
                    return "Вы не можете удалить этот элемент HumanBeing, так как он вам не принадлежит.";
                }
                else {
                    remove(human);
                    writeLock.unlock();
                    return "Элемент HumanBeing с таким id удален.";
                }
            }
        }
        writeLock.unlock();
        return "Элемент HumanBeing с таким id не найден.";

    }

    public String updateById(int id, HumanBeing modifiedHuman, int idUser) {
        writeLock.lock();
        for (HumanBeing human : humans) {
            if(human.getId() == id) {
                if(human.getIdUser() != idUser) {
                    writeLock.unlock();
                    return "HumanBeing нельзя обновить, так как он вам не принадлежит.";
                }
                else {
                    human.updateHuman(modifiedHuman);
                    try {
                        context.handlerDatabase.updateHuman(human);
                        writeLock.unlock();
                        return "Элемент HumanBeing обновлен.";
                    } catch (SQLException e) {
                        writeLock.unlock();
                        return "Обновить элемент в базе данных не удалось.";
                    }
                }
            }
        }
        writeLock.unlock();
        return "Элемент HumanBeing с таким id не найден.";
    }

    public String removeGreater(HumanBeing srcHuman, int idUser) {
        writeLock.lock();
        for (int i = 0; i < humans.size(); ++i) {
            if(humans.get(i).getImpactSpeed() > srcHuman.getImpactSpeed() && humans.get(i).getIdUser() == idUser) {
                remove(humans.get(i));
                i--;
            }
        }
        writeLock.unlock();
        return "Команда выполнена!";
    }

    public void sort(Comparator<HumanBeing> comparator) {
        writeLock.lock();
        humans = humans.stream().sorted(comparator).collect(Collectors.toCollection(LinkedList::new));
        writeLock.unlock();
    }

    public void sort() {
        writeLock.lock();
        humans = humans.stream().sorted((h1, h2) -> Float.compare(h1.getImpactSpeed(), h2.getImpactSpeed())).collect(Collectors.toCollection(LinkedList::new));
        writeLock.unlock();
    }

    public String printInfo() {
        readLock.lock();
        String result = "Тип коллекции: " + humans.getClass() + ", Размер: " + humans.size();
        readLock.unlock();
        return result;
    }

    private LinkedList<HumanBeing> getDescendingHumans() {
        writeLock.lock();
        LinkedList<HumanBeing> result = humans.stream().sorted((h1, h2) -> Float.compare(h1.getImpactSpeed(), h2.getImpactSpeed()) * -1).collect(Collectors.toCollection(LinkedList::new));
        writeLock.unlock();
        return result;
    }

    public String printDescending() {
        readLock.lock();
        String result = getDescendingHumans().toString();
        readLock.unlock();
        return result;
    }

    public String printFieldDescendingCar() {
        writeLock.lock();
        LinkedList<Car> result = getDescendingHumans().stream().map(x -> x.getCar()).collect(Collectors.toCollection(LinkedList::new));
        writeLock.unlock();
        return result.toString();
    }

    public String printUniqueCar() {
        writeLock.lock();
        HashSet<Car> cars = humans.stream().map(x -> x.getCar()).collect(Collectors.toCollection(HashSet::new));
        writeLock.unlock();
        return cars.toString();
    }

    public String getAlphabet(LinkedList<HumanBeing> srcHumans) {
        writeLock.lock();
        srcHumans = srcHumans.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toCollection(LinkedList::new));
        writeLock.unlock();
        return srcHumans.toString();
    }

    public String getAlphabet() {
        writeLock.lock();
        String result = getAlphabet(new LinkedList<>(humans));
        writeLock.unlock();
        return result;
    }
}
