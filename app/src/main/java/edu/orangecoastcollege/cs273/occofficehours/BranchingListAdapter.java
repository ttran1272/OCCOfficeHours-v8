package edu.orangecoastcollege.cs273.occofficehours;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnhTran on 12/6/2017.
 */

public class BranchingListAdapter extends ArrayAdapter<Branching> {
    private Context mContext;
    private List<Branching> mBranchingsList = new ArrayList<>();
    private int mResourceId;

    /**
     * Creates a new <code>OfferingListAdapter</code> given a mContext, resource id and list of offerings.
     *
     * @param c The mContext for which the adapter is being used (typically an activity)
     * @param rId The resource id (typically the layout file name)
     * @param branchings The list of branchings to display
     */
    public BranchingListAdapter(Context c, int rId, List<Branching> branchings) {
        super(c, rId, branchings);
        mContext = c;
        mResourceId = rId;
        mBranchingsList = branchings;
    }

    /**
     * Gets the view associated with the layout.
     * @param pos The position of the Offering selected in the list.
     * @param convertView The converted view.
     * @param parent The parent - ArrayAdapter
     * @return The new view with all content set.
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        final Branching selectedBranching = mBranchingsList.get(pos);
        final Department selectedDepartment = selectedBranching.getDepartment();
        final Instructor selectedInstructor = selectedBranching.getInstructor();

        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        LinearLayout branchingListLinearLayout =
                (LinearLayout) view.findViewById(R.id.branchingListLinearLayout);

        TextView branchingListDepartmentTextView =
                (TextView) view.findViewById(R.id.branchingListDepartmentTextView);
        TextView branchingListInstructorTextView =
                (TextView) view.findViewById(R.id.branchingListInstructorTextView);

        branchingListLinearLayout.setTag(selectedBranching);

        branchingListDepartmentTextView.setText(selectedDepartment.getDepartment());
        branchingListInstructorTextView.setText(selectedInstructor.getFullName());

        return view;
    }
}
