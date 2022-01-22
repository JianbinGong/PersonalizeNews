package com.example.tinnews.ui.home;

import android.view.View;
import android.widget.ProgressBar;

import com.example.tinnews.model.Article;

public abstract class Test<Param, Progress, Result> {
    Result result;

    protected void onPreExecute() {

    }

    abstract Result doInBackground(Param param);

    protected void onPostExecute(Result result) {

    }

    protected void onProgressUpdate(Progress progress) {
        // back to UI thread
    }

    public Test<Param, Progress, Result> execute(Param param) throws InterruptedException {
        //UI
        onPreExecute();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                result = doInBackground(param);
            }

        });
        thread.run();
        thread.join();
        //UI
        // Where is on progressupdate
        onPostExecute(result);
        return this;

    }

}
    class MyTest extends Test<Article, Integer, Boolean> {
        private ProgressBar progressBar;
        MyTest(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }
        @Override
        Boolean doInBackground(Article article) {
            onProgressUpdate(5);
            onProgressUpdate(10);
            onProgressUpdate(100);
            return null;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }