package edu.orangecoastcollege.cs273.occofficehours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

//import android.widget.ThemedSpinnerAdapter;

public class SearchByDepartmentActivity extends AppCompatActivity {

    private DBHelper db;
    private List<Instructor> allInstructorsList;


    private List<Department> allDepartmentsList;
    private List<Branching> allBranchingsList;
    private List<Branching> filteredBranchingsList;
    private BranchingListAdapter branchingListAdapter;

    private Spinner departmentSpinner;
    private ListView branchingsListView;

    // Shake animation, used when the user clicks the reset button
    private Animation shakeAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_department);

        deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);

        db.importInstructorsFromCSV("instructors2.csv");

        allBranchingsList = db.getAllBranchings();
        filteredBranchingsList = new ArrayList<>(allBranchingsList);
        allInstructorsList = db.getAllInstructors();
        allDepartmentsList = db.getAllDepartments();


        departmentSpinner = (Spinner) findViewById(R.id.departmentSpinner);

        branchingsListView = (ListView) findViewById(R.id.branchingsListView);
        branchingListAdapter =
                new BranchingListAdapter(this, R.layout.branching_list_item, filteredBranchingsList);
        branchingsListView.setAdapter(branchingListAdapter);


        //TODO (1): Construct instructorSpinnerAdapter using the method getInstructorNames()
        //TODO: to populate the spinner.
        ArrayAdapter<String> instructorSpinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getDepartments());
        departmentSpinner.setAdapter(instructorSpinnerAdapter);
        departmentSpinner.setOnItemSelectedListener(instructorSpinnerListener);
    }

    private String[] getDepartments()
    {
        String[] departments = new String[allDepartmentsList.size() + 1];
        departments[0] = "[Select Department]";
        for (int i = 1; i < departments.length; ++i)
            departments[i] = allDepartmentsList.get(i - 1).getDepartment();

        return departments;
    }

    public void reset(View v)
    {
        toggleShakeAnim(v);
        // Set spinner back to position 0
        departmentSpinner.setSelection(0);
        // Clear out the list adapter
        branchingListAdapter.clear();
        // Repopulate it from allOfferingsList
        branchingListAdapter.addAll(allBranchingsList);

    }

    public AdapterView.OnItemSelectedListener instructorSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {
            // Retrieve the instructor name
            String departmentName = String.valueOf(spinner.getItemAtPosition(i));
            // Clear the adapter
            branchingListAdapter.clear();
            if (departmentName.equals("[Select Department]"))
                branchingListAdapter.addAll(allBranchingsList);
            else
                for (Branching branching : allBranchingsList)
                    if (branching.getInstructor().getDepartment().equals(departmentName))
                        branchingListAdapter.add(branching);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    /**
     * Plays the animation from shake_anim.xml
     * Shakes the image horizontally
     * Used in reset function
     *
     * @param v
     */
    public void toggleShakeAnim(View v)
    {
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
        branchingsListView.startAnimation(shakeAnim);
    }

    public void viewInstructorDetails(View v)
    {
        LinearLayout selectedLayout = (LinearLayout) v;
        Offering selectedOffering = (Offering) selectedLayout.getTag();
        Instructor selectedInstructor = (Instructor) selectedOffering.getInstructor();
        Intent detailsIntent = new Intent(this, InstructorDetailsActivity.class);
        detailsIntent.putExtra("Name", selectedInstructor.getFullName());
        detailsIntent.putExtra("Email", selectedInstructor.getEmail());
        detailsIntent.putExtra("Department", selectedInstructor.getDepartment());
        detailsIntent.putExtra("Building", selectedInstructor.getBuilding());
        detailsIntent.putExtra("Room", selectedInstructor.getRoom());
        detailsIntent.putExtra("Monday", selectedInstructor.getMonday());
        detailsIntent.putExtra("Tuesday", selectedInstructor.getTuesday());
        detailsIntent.putExtra("Wednesday", selectedInstructor.getWednesday());
        detailsIntent.putExtra("Thursday", selectedInstructor.getThursday());
        detailsIntent.putExtra("Friday", selectedInstructor.getFriday());
        startActivity(detailsIntent);
    }
}