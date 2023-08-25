package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GroupRVAdapter extends RecyclerView.Adapter<GroupRVAdapter.ViewHolder> {
    int lastPos = -1;
    //creating variables for our list, context, interface and position.
    private ArrayList<GroupRVModal> groupRVModalArrayList;
    private Context context;
    private CourseClickInterface courseClickInterface;

    //creating a constructor.
    public GroupRVAdapter(ArrayList<GroupRVModal> groupRVModalArrayList, Context context, CourseClickInterface courseClickInterface) {
        this.groupRVModalArrayList = groupRVModalArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //setting data to our recycler view item on below line.
        GroupRVModal groupRVModal = groupRVModalArrayList.get(position);
        holder.courseTV.setText(groupRVModal.getGroupName() + " \nNext Activity: " + groupRVModal.getActivityDetail());
        holder.coursePriceTV.setText("Price: " + groupRVModal.getGroupPrice());
        String groupImg = groupRVModal.getGroupImg();
        if (groupImg == null || groupImg.equals("")) {
            groupImg = "https://media.istockphoto.com/id/481197688/photo/aerial-shot-of-a-crowd-of-people-form-word-group.jpg?s=1024x1024&w=is&k=20&c=9x3wK-JG4rVinLSe90wgaEPO-iB-UyNfb-NbkNvCgdk=";
        }
        ;
        Picasso.get().load(groupImg).into(holder.courseIV);
        //adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.courseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseClickInterface.onCourseClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            //on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return groupRVModalArrayList.size();
    }

    //creating a interface for on click
    public interface CourseClickInterface {
        void onCourseClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //creating variable for our image view and text view on below line.
        private ImageView courseIV;
        private TextView courseTV, coursePriceTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initializing all our variables on below line.
            courseIV = itemView.findViewById(R.id.idIVCourse);
            courseTV = itemView.findViewById(R.id.idTVCOurseName);
            coursePriceTV = itemView.findViewById(R.id.idTVCousePrice);
        }
    }
}
