package elements;

import java.io.Serializable;
import java.time.LocalDate;

public class HumanBeing implements Serializable {
    private Integer idUser;
    private Integer id; //Поле не может быть null, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private boolean hasToothpick;
    private float impactSpeed;
    private WeaponType weaponType; //Поле не может быть null
    private Mood mood; //Поле может быть null
    private Car car; //Поле не может быть null

    public HumanBeing(String name, Coordinates coordinates, boolean realHero, boolean hasToothpick,
                      float impactSpeed, WeaponType weaponType, Mood mood, Car car) {
        this.name = name;
        this.coordinates = coordinates;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    public HumanBeing(int idUser, int id, String name, Coordinates coordinates, LocalDate creationDate, boolean realHero, boolean hasToothpick, float impactSpeed, WeaponType weaponType, Mood mood, Car car) {
        this.idUser = idUser;
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    public void updateHuman(HumanBeing human) {
        this.name = human.name;
        this.coordinates = human.coordinates;
        this.realHero = human.realHero;
        this.hasToothpick = human.hasToothpick;
        this.impactSpeed = human.impactSpeed;
        this.weaponType = human.weaponType;
        this.mood = human.mood;
        this.car = human.car;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreationDate() {
        if(this.creationDate != null) {
            return;
        }
        this.creationDate = LocalDate.now();
    }

    public void setCreationDate(LocalDate creationDate) {
        if(this.creationDate != null) {
            return;
        }
        this.creationDate = creationDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean getRealHero() {
        return realHero;
    }

    public boolean getHasToothpick() {
        return hasToothpick;
    }

    public float getImpactSpeed() {
        return impactSpeed;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return  "HumanBeing [id_user=" + idUser + ", \n" +
                "         id=" + id + ", \n" +
                "         name=" + name + ", \n" +
                "         coordinates=" + coordinates.toString() + ", \n" +
                "         creationDate=" + creationDate.toString() + ", \n" +
                "         realHero=" + realHero + ", \n" +
                "         hasToothpick=" + hasToothpick + ", \n" +
                "         impactSpeed=" + impactSpeed + ", \n" +
                "         weaponType=" + weaponType + ", \n" +
                "         mood=" + mood + ", \n" +
                "         car= " + car.toString() + "]\n";
    }
}