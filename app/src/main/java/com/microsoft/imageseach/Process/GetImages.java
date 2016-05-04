package com.microsoft.imageseach.Process;

import android.content.Context;
import android.os.AsyncTask;

import com.microsoft.imageseach.Model.Continue;
import com.microsoft.imageseach.Model.ImageSearch;
import com.microsoft.imageseach.Model.Pages;
import com.microsoft.imageseach.Model.Query;
import com.microsoft.imageseach.Model.ThumbNail;
import com.microsoft.imageseach.R;
import com.microsoft.imageseach.Utilities.Constants;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Tatyabhau Chavan on 5/3/2016.
 */
public class GetImages extends AsyncTask<String, Void, ImageSearch> {


    /*********************
     * START DECLARATION
     *********************/

    private Context context;
    private Exception exception = null;
    private OnProcessListener onProcessListener;


    /*********************
     * END DECLARATION
     ***********************/


    public GetImages(Context context) {
        this.context = context;
    }


    @Override
    protected ImageSearch doInBackground(String... params) {

        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(params[0]);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            return process(httpClient.execute(httpGet, responseHandler));
        } catch (IOException e) {
            exception = e;
            return null;
        }
    }

    private ImageSearch process(String response) {

        if (response.isEmpty() || response == null) {
            exception = new Exception(context.getString(R.string.INTERNAL_SERVER_ERROR));
            return null;
        } else if (response.equalsIgnoreCase("{\"batchcomplete\":\"\"}")) {
            exception = new Exception(context.getString(R.string.NO_DATA_FOUND));
            return null;
        }

        ImageSearch imageSearch = new ImageSearch();

        try {
            JSONObject jsonObject = new JSONObject(response);
            imageSearch.setBatchcomplete(jsonObject.getString(Constants.BATCHCOMPLETE));
            Continue aContinue = new Continue();
            JSONObject jContinue;
            if (jsonObject.has(Constants.CONTINUE)) {
                jContinue = jsonObject.getJSONObject(Constants.CONTINUE);
                aContinue.setContinues(jContinue.getString(Constants.CONTINUE));
                aContinue.setGpsoffset(jContinue.getInt(Constants.GPSOFFSET));
            } else {
                aContinue.setContinues("");
                aContinue.setGpsoffset(0);
            }
            imageSearch.setaContinue(aContinue);
            JSONObject query = jsonObject.getJSONObject(Constants.QUERY);
            JSONObject pages = query.getJSONObject(Constants.PAGES);
            Iterator<String> keyName = pages.keys();
            List<Pages> listPages = new ArrayList<>();
            Query queries = new Query();
            while (keyName.hasNext()) {
                String key = keyName.next();
                JSONObject page = pages.getJSONObject(key);
                Pages nodePage = new Pages();
                nodePage.setPageid(page.getLong(Constants.PAGEID));
                nodePage.setIndex(page.getInt(Constants.INDEX));
                nodePage.setNs(page.getInt(Constants.NS));
                nodePage.setTitle(page.getString(Constants.TITLE));
                ThumbNail thumbNail = new ThumbNail();

                JSONObject jThumbNail;
                if (page.has(Constants.THUMBNAIL)) {
                    jThumbNail = page.getJSONObject(Constants.THUMBNAIL);
                    thumbNail.setSource(jThumbNail.getString(Constants.SOURCE));
                    thumbNail.setWidth(jThumbNail.getInt(Constants.WIDTH));
                    thumbNail.setHeight(jThumbNail.getInt(Constants.HEIGHT));
                } else {
                    thumbNail.setSource("");
                    thumbNail.setWidth(0);
                    thumbNail.setHeight(0);
                }

                nodePage.setThumbNail(thumbNail);
                listPages.add(nodePage);
            }
            queries.setPage(listPages);
            imageSearch.setQuery(queries);
        } catch (JSONException e) {
            exception = new Exception(context.getString(R.string.INTERNAL_SERVER_ERROR));
        }
        return imageSearch;
    }

    @Override
    protected void onPostExecute(ImageSearch imageSearch) {
        super.onPostExecute(imageSearch);
        onProcessListener.onProcessComplete(imageSearch, exception);
    }

    /**
     * calling parent class executor
     */
    public void doLoadInBackground(String param) {
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, param);
    }

    /**
     * @param onProcessListener
     */
    public void setOnProcessListener(OnProcessListener onProcessListener, String param) {
        this.onProcessListener = onProcessListener;
        doLoadInBackground(param);
    }


}
