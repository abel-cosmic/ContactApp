package trident.contactapp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class ContactItemAdapter extends RecyclerView.Adapter<ContactItemAdapter.ViewHolder> {

    private static List<Contact> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView initial;
        private final TextView name;
        private final View line;

        public ViewHolder(View view) {
            super(view);
            FrameLayout f = (FrameLayout) view.findViewById(R.id.item);
            // Define click listener for the ViewHolder's View
            initial = (TextView) view.findViewById(R.id.initial);
            name = (TextView) view.findViewById(R.id.name);
            line = view.findViewById(R.id.lineView);
            f.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                Contact clickedContact = localDataSet.get(position);
                                System.out.println(clickedContact.toString());
                                //TODO: PASS THE CURRENT ITEM TO CONTACTS PAGE
                            }
                        }
                    }
            );

        }

        public TextView getName() {
            return name;
        }
        public TextView getInitial() {
            return initial;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public ContactItemAdapter(List<Contact> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getName().setText(localDataSet.get(position).getName());
        viewHolder.getInitial().setText(localDataSet.get(position).getName().substring(0,1));

        String[] COLORS = {
                "#274C77", "#635255", "#36494E", "#DB6C79", "#413C58",
                "#413C58", "#DBCBD8", "#9BD1E5", "#157145", "#2176FF", "#FDCA40","#66A182"
        };
        Random random = new Random();
        String randomColor = COLORS[random.nextInt(COLORS.length)];
        viewHolder.getInitial().setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(randomColor)));;
//        if (position == getItemCount() - 1){
//            viewHolder.line.setVisibility(View.INVISIBLE);
//        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
