package com.oni.pixelchat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oni.pixelchat.databinding.FragmentDisplayImageBinding;
import com.oni.pixelchat.databinding.FragmentInboxBinding;
import com.squareup.picasso.Picasso;

public class DisplayImageFragment extends Fragment {
FragmentDisplayImageBinding binding;
ImageView displayImage;
    public DisplayImageFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDisplayImageBinding.inflate(inflater,container,false);

        Bundle b = this.getArguments();
        String url = b.getString("DownloadUrl");
        displayImage = binding.ivDisplayFullImage;
        Picasso.get().load(url).into(displayImage);

        return binding.getRoot();
    }
}