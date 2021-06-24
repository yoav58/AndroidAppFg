package com.example.flightgearappandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.flightgearappandroid.databinding.ActivityMainBinding;
import com.example.flightgearappandroid.viewModel.ViewModel;
import com.example.flightgearappandroid.views.JoyStick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModel viewModel;
        // ViewModel updates the Model
        // after observing changes in the View

        // model will also update the view
        // via the ViewModel
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModel();
        activityMainBinding.setViewModel(viewModel);
        //activityMainBinding.setViewModel(new ViewModel());
        activityMainBinding.executePendingBindings();
        JoyStick joystick = findViewById(R.id.joyStick);
        joystick.onChange=(a,e)->{
            viewModel.changeAileron(a);
            viewModel.changeElevator(e);
        };

    }

    // any change in toastMessage attribute
    // defined on the Button with bind prefix
    // invokes this method
    @BindingAdapter({"toastMessage"})
    public static void runMe( View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}