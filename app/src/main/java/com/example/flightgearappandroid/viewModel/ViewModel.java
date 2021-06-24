package com.example.flightgearappandroid.viewModel;
import com.example.flightgearappandroid.model.FgModel;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.SeekBar;

import androidx.databinding.BaseObservable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.databinding.Bindable;

import java.io.PrintWriter;
import java.net.Socket;

public class ViewModel extends BaseObservable {
    private FgModel fgmodel;
    private String succes = "Connected to the FG";
    private String fail = "the connectopn failed, please check the you entered valid ip";
    private String toastMessage = null;

    // constructor
    public ViewModel(){
        this.fgmodel = new FgModel();
    }
    // functions
    @Bindable
    public String getToastMessage(){
        return toastMessage;
    } // to know is the connection succesed.
    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
         notifyPropertyChanged(BR.toastMessage);
    }
    @Bindable
    public String getUserIp() {
        return fgmodel.getIp();
    }
    public void setUserIp(String i) {
        fgmodel.setIp(i);
        notifyPropertyChanged(BR.userIp);
    }
    @Bindable
    public String getUserPort() {
        return fgmodel.getPort();
    }
    public void setUserPort(String p) {
        fgmodel.setPort(p);
        notifyPropertyChanged(BR.userPort);
    }
    public void onButtonClicked() {
        if (fgmodel.connect())
            setToastMessage(succes);
        else
            setToastMessage(fail);
    }
    public void changeThrottle(){
        if(!fgmodel.isConnected()) return;
        fgmodel.ChangeTrottle();
    }
    @Bindable
    public int getUserThrottle(){
        return fgmodel.getThrottle();
    }
    public void setUserThrottle(int t){
       fgmodel.setThrottle(t);
       notifyPropertyChanged(BR.userThrottle);
    }
    @Bindable
    public int getUserRudder(){
        return fgmodel.getRudder();
    }
    public void setUserRudder(int t){
        fgmodel.setRudder(t);
        notifyPropertyChanged(BR.userThrottle);
    }
    public void changeRudder(){
        if(!fgmodel.isConnected()) return;
        fgmodel.changeRudder();
    }
    public void changeElevator(float x){
        if(!fgmodel.isConnected()) return;
        fgmodel.changeEvelator(x);
    }
    public void changeAileron(float x){
        if(!fgmodel.isConnected()) return;
        fgmodel.changeAileron(x);
    }






}
