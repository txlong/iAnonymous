package com.binpit.views;

import android.view.View;

public interface IView
{
    public View getView();
    
    /**
     * When activity have been changed, we must reset the object who dependents on activity
     */
    public void reset();
    
    public void update();
    
    public void release();

}
