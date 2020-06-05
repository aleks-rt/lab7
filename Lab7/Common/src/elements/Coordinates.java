package elements;

import java.io.Serializable;


public class Coordinates implements Serializable {
    private Float x; //Поле не может быть null
    private Float y; //Поле не может быть null

    public Coordinates(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates [x=" + x + ", y=" + y + "]";
    }
}