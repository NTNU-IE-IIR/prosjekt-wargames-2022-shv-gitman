package org.ntnu.wargames;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Army {
    private String name;
    private List<Unit> units = new ArrayList<>();

    /**
     * Creates an empty army.
     * @param name the name of the army.
     */
    Army(String name) {
        this.name = name;
    }

    /**
     * Creates an army with an existing list of units.
     * @param name the name of the army.
     * @param units the list of units.
     */
    Army(String name, List<Unit> units) {
        this.name = name;
        this.units = units;
    }

    /**
     * Adds an unit to the army.
     * @param unit the unit to be added.
     */
    public void add(Unit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit is empty");
        }
        this.units.add(unit);
        // TODO: check if unit is already in list.
        // TODO: check if unit = null
    }

    /**
     * Adds a list of units to the army.
     * @param units the list of unit to be added.
     */
    public void addAll(List<Unit> units) {
        this.units.addAll(units);
    }

    /**
     * Removes a unit from the army.
     * @param unit the unit to be removed.
     */
    public void remove(Unit unit) {
        this.units.remove(unit);
    }

    /**
     * Checks if the army has units.
     * Returns true if the army has units, false if not.
     * @return true if the army has units, false if not.
     */
    public boolean hasUnits() {
        return !units.isEmpty();
    }

    /**
     * Returns a list of all units in the army.
     * @return a list of all units in the army.
     */
    public List<Unit> getAllUnits() {
        return this.units;
    }

    /**
     * Returns a random unit from the army.
     * @return a random unit from the army.
     */
    public Unit getRandom() {
        Random random = new Random();
        return units.get(random.nextInt(units.size()));
    }

    public int getAmountOfUnits() {
        int size = 0;
        for (int i = 0; i < units.size(); i++) {
            size++;
        }
        return size;
    }

    /**
     * Returns a string representing the whole army.
     * @return a string representing the whole army.
     */
    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", units=" + getAmountOfUnits();
    }

    /**
     * Checks if the army is the same as another army.
     * Both name and units must be the same to return true.
     * @param o the object to be checked if it is the same as this army.
     * @return true if the object is the same as this army, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;
        return Objects.equals(name, army.name) && Objects.equals(units, army.units);
    }

    /**
     * Returns a hash value of the Army object.
     * @return a hash value of the Army object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }
}
