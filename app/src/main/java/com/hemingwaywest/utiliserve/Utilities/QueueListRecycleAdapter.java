package com.hemingwaywest.utiliserve.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hemingwaywest.utiliserve.R;
import com.hemingwaywest.utiliserve.database.Forms;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Project: utiliserve-reboot
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 4/28/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
public class QueueListRecycleAdapter extends RecyclerView.Adapter<QueueListRecycleAdapter.MyViewHolder> {

    private Context mContext;
    private List<Forms> mQueue;
    final private ItemClickListener mItemClickListener;


    public QueueListRecycleAdapter(Context context, ItemClickListener listener){
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * This gets called when each new viewholder is created.
     *
     * @param viewGroup The viewgroup that these viewholders are contained within
     * @param viewType if the recycler view has more than one type of item, you use this viewType Integer
     *          to provide a different layout.
     * @return A new Recycler view Adapter that holds the view for each list item
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.item_queue;
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(layoutForListItem, viewGroup, false);

        return new MyViewHolder(v);
    }

    /**
     * Fetch Data from Model
     */
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            //Get position
            Forms form = mQueue.get(position);
            //Get Values
            String title = form.getName();
            String description = form.getDescription();
            String status = form.getFormType();
            Integer formNum = form.getId();
            //Set Values
            myViewHolder.tv_formTitle.setText(title);
            myViewHolder.tv_formDescription.setText(description);
            myViewHolder.tv_formStatus.setText(status);
            myViewHolder.tv_formNum.setText(formNum.toString());
    }

    @Override
    public int getItemCount() {
        if (null == mQueue) return 0;
        return mQueue.size();
    }

    public void setQueueData(List<Forms> queueData){
        mQueue = queueData;
        notifyDataSetChanged();
    }

    public List<Forms> getmQueue(){return mQueue;}

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    /**
     * Inner Class for views
     * Recycler view needs a viewholder class
     * This bind the data to the layout for the list item
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView tv_formTitle;
        public final TextView tv_formDescription;
        public final TextView tv_formStatus;
        public final TextView tv_formNum;


        //Constructor
        public MyViewHolder(View itemView) {
            super(itemView);

            tv_formTitle = (TextView)itemView.findViewById(R.id.form_title);
            tv_formDescription = (TextView)itemView.findViewById(R.id.form_description);
            tv_formStatus = (TextView)itemView.findViewById(R.id.form_status);
            tv_formNum = (TextView) itemView.findViewById(R.id.form_id);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //Get the DB id of the item at the recyclers position
            int elementId = mQueue.get(getAdapterPosition()).getId();
            //Pass to 'parent' so fragment using this adapter, QueueFragment
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
