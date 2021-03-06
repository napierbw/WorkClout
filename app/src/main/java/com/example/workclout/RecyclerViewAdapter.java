package com.example.workclout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Double> mLengths = new ArrayList<>();
    private ArrayList<Double> mDifficulties = new ArrayList<>();
    private Context mContext;
    private ArrayList<String> mChallengeIDs = new ArrayList<>();
    private helperClass HC = new helperClass();

    private FirebaseFirestore firestore;

    public RecyclerViewAdapter(Context context, ArrayList<String> imageNames, ArrayList<String> images, ArrayList<String> descriptions, ArrayList<String> challengeIDs,
                               ArrayList<Double> length, ArrayList<Double> difficulty) {
        mContext = context;
        mImageNames = imageNames;
        mImages = images;
        mDescriptions = descriptions;
        mChallengeIDs = challengeIDs;
        mLengths = length;
        mDifficulties = difficulty;
        firestore = FirebaseFirestore.getInstance();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);

        holder.imageName.setText(mImageNames.get(position));
        holder.description.setText(mDescriptions.get(position));
        holder.difficulty.setText("Difficulty: " + mDifficulties.get(position));
        holder.length.setText("Length: " + mLengths.get(position));


        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

                String userID = HC.get_user_id();

                firestore.collection("athletes").document(userID).update("challenge1", mChallengeIDs.get(position));



                Toast.makeText(mContext, "Registered for " + mImageNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName, description, length, difficulty;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            description = itemView.findViewById(R.id.challengeDescription);
            length = itemView.findViewById(R.id.length);
            difficulty = itemView.findViewById(R.id.difficulty);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

    }

}
