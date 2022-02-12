package org.ntnu.wargames;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Army {
    private String name;
    private List<Unit> units;

    Army(String name) {
        this.name = name;
    }

    Army(String name, List<Unit> units) {
        this.name = name;
        this.units = units;
    }

    public void add(Unit unit) {
        this.units.add(unit);
    }

    public void addAll(List<Unit> units) {
        this.units.addAll(units);
    }

    public void remove(Unit unit) {
        this.units.remove(unit);
    }

    public boolean hasUnits() {
        return !units.isEmpty();
    }

    public List<Unit> getAllUnits() {
        return this.units;
    }

    public Unit getRandom() {
        Random random = new Random();
        return units.get(random.nextInt(units.size()));
    }

    @Override
    public String toString() {
        return "Army{" +
                "name='" + name + '\'' +
                ", units=" + units +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;
        return Objects.equals(name, army.name) && Objects.equals(units, army.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }
}
