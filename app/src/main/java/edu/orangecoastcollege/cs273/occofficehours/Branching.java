package edu.orangecoastcollege.cs273.occofficehours;

/**
 * Created by AnhTran on 12/6/2017.
 */

public class Branching {

    private int mId;
    private Department mDepartment;
    private Instructor mInstructor;

    public Branching(int id, Department department, Instructor instructor) {
        mId = id;
        mDepartment = department;
        mInstructor = instructor;
    }

    public Branching(Department department, Instructor instructor) {
        mDepartment = department;
        mInstructor = instructor;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Department getDepartment() {
        return mDepartment;
    }

    public void setDepartment(Department department) {
        mDepartment = department;
    }

    public Instructor getInstructor() {
        return mInstructor;
    }

    public void setInstructor(Instructor instructor) {
        mInstructor = instructor;
    }

    @Override
    public String toString() {
        return "Branching{" +
                "Department=" + mDepartment +
                ", Instructor=" + mInstructor +
                '}';
    }
}
