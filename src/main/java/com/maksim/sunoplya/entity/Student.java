package com.maksim.sunoplya.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Student {
    private String surname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return surname.equals(student.surname);
    }

    @Override
    public int hashCode() {
        return surname.hashCode();
    }
}