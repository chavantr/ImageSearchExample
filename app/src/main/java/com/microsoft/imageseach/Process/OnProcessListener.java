package com.microsoft.imageseach.Process;

import com.microsoft.imageseach.Model.ImageSearch;

/**
 * Created by Tatyabhau Chavan on 5/3/2016.
 */
public interface OnProcessListener {
    public void onProcessComplete(ImageSearch result, Exception exception);
}
