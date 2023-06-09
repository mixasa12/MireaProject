package ru.mirea.bandurin.mireaproject.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.mirea.bandurin.mireaproject.R;
import ru.mirea.bandurin.mireaproject.databinding.FragmentProfileBinding;
import ru.mirea.bandurin.mireaproject.ui.home.HomeViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private TextView name;
    private TextView age;
    private TextView prof;
    private Button  bsave;
    private Button bopen;
    private EditText ename;
    private EditText eage;
    private EditText eprof;
    private String zname;
    private int zage;
    private String zprof;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private boolean vi=false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        age=binding.age;
        name=binding.name;
        prof=binding.prof;
        bopen=binding.open;
        bsave=binding.save;
        ename=binding.editname;
        eage=binding.editage;
        eprof=binding.editprof;
        sharedPref = getActivity().getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        get();
        View root = binding.getRoot();
        bsave.setOnClickListener(view -> set());
        bopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vi){
                   ename.setVisibility(View.GONE);
                    eage.setVisibility(View.GONE);
                    eprof.setVisibility(View.GONE);
                    bsave.setVisibility(View.GONE);
                    name.setVisibility(View.VISIBLE);
                    age.setVisibility(View.VISIBLE);
                    prof.setVisibility(View.VISIBLE);
                    bopen.setText("Открыть");
                    vi=false;
                }
                else{
                    ename.setVisibility(View.VISIBLE);
                    eage.setVisibility(View.VISIBLE);
                    eprof.setVisibility(View.VISIBLE);
                    bsave.setVisibility(View.VISIBLE);
                    name.setVisibility(View.GONE);
                    age.setVisibility(View.GONE);
                    prof.setVisibility(View.GONE);
                    bopen.setText("Закрыть");
                    vi=true;
                }
            }
        });
        final TextView textView = binding.textProfile;
        //profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void set(){
        editor.putString("NAME", ename.getText().toString());
        editor.putInt("AGE", Integer.valueOf(eage.getText().toString()));
        editor.putString("PROF", eprof.getText().toString());
        editor.apply();
        get();
    }
    private void get(){
        zname = sharedPref.getString("NAME", "unknown");
        name.setText(zname);
        zprof = sharedPref.getString("PROF", "unknown");
        prof.setText(zprof);
        ename.setText(zname);
        eprof.setText(zprof);
        zage = sharedPref.getInt("AGE", 0);
        age.setText("Возраст "+String.valueOf(zage));
        eage.setText(String.valueOf(zage));
        /*zage = sharedPref.getInt("AGE", 0);
        name.setText(zname);
        age.setText("Возраст "+String.valueOf(zage));
        prof.setText(zprof);
        ename.setText(zname);
        eage.setText(zage);
        eprof.setText(zprof);*/
    }
}