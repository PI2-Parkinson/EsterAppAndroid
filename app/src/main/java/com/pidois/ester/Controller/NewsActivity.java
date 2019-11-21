package com.pidois.ester.Controller;

import com.pidois.ester.Controller.Adapter.Adapter;
import com.pidois.ester.Models.News;

import android.content.Intent;
import android.os.Build;
//import android.support.v4.app.ActivityOptionsCompat;
import androidx.core.app.ActivityOptionsCompat;
//import android.support.v4.util.Pair;
import androidx.core.util.Pair;
//import android.support.v4.view.ViewCompat;
import androidx.core.view.ViewCompat;
//import android.support.v4.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pidois.ester.R;
import com.pidois.ester.api.ApiClient;
import com.pidois.ester.api.ApiInterface;
import com.pidois.ester.Models.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener{

    public static final String API_KEY = "5d6e98ed77d54c2fbc45ef1b68095ea6";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();
    private TextView topHeadline;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        topHeadline = findViewById(R.id.topheadelines);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(NewsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh("parkinson");

        errorLayout = findViewById(R.id.errorLayout);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);

    }

    public void LoadJson(final String keyword){

        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String country = "br";
        String language = "pt";
        //String sources = "globo,blasting-news-br,google-news-br,Gazetadopovo.com.br,InfoMoney,Foxsportsla.com,Correiodoestado.com.br,Abril.com.br,Superesportes.com.br,Diariodocentrodomundo.com.br,Oantagonista.com,IGN,Gazetadopovo.com.br";
        String q = "parkinson";
        //String qInTitle = "doença AND parkinson";
        String excludeDomains = "uol.com.br";
        String sortBy = "publishedAt";

        Call<News> call;

        if (keyword.length() > 0 ){
            call = apiInterface.getNewsSearch(q, excludeDomains, language, sortBy, API_KEY);
        } else {
            call = apiInterface.getNews(country, q, excludeDomains, language, API_KEY);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    adapter = new Adapter(articles, NewsActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    initListener();

                    topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);


                } else {

                    topHeadline.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 não encontrado";
                            break;
                        case 500:
                            errorCode = "500 servidor fora do ar";
                            break;
                        default:
                            errorCode = "Erro desconhecido";
                            break;
                    }

                    showErrorMessage(
                            R.drawable.no_result,
                            "Nenhum resultado encontrado",
                            "Tente novamente!\n"+
                                    errorCode);

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                topHeadline.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(
                        R.drawable.oops,
                        "Oops..",
                        "Sem conexão com a internet\n"+
                                t.toString());
            }
        });

    }



    private void initListener(){

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",  article.getUrlToImage());
                intent.putExtra("date",  article.getPublishedAt());
                intent.putExtra("source",  article.getSource().getName());
                intent.putExtra("author",  article.getAuthor());

                Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        NewsActivity.this,
                        pair
                );


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, optionsCompat.toBundle());
                }else {
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onRefresh() {
        LoadJson("parkinson");
    }

    private void onLoadingSwipeRefresh(final String keyword){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson(keyword);
                    }
                }
        );

    }

    private void showErrorMessage(int imageView, String title, String message){

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadingSwipeRefresh("parkinson");
            }
        });

    }


}