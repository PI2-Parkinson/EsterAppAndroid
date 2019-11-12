package com.pidois.ester;

import com.pidois.ester.Models.Article;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArticleTest {
    @Test
    public void shouldValidateArticleAuthor(){
        Article article = new Article();
        article.setAuthor("Redator");
        assertEquals("Redator", article.getAuthor());
    }

    @Test
    public void shouldValidateArticleTitle(){
        Article article = new Article();
        article.setTitle("O mal de Parkinson");
        assertEquals("O mal de Parkinson", article.getTitle());
    }

    @Test
    public void shouldValidateArticleDescription(){
        Article article = new Article();
        article.setDescription("Uma descoberta recente de cientistas brasileiros...");
        assertEquals("Uma descoberta recente de cientistas brasileiros...", article.getDescription());
    }

    @Test
    public void shouldValidateArticleUrl(){
        Article article = new Article();
        article.setUrl("www.globo.com.br");
        assertEquals("www.globo.com.br", article.getUrl());
    }

    @Test
    public void shouldValidateArticlePublishedAt(){
        Article article = new Article();
        article.setPublishedAt("10/10/2019");
        assertEquals("10/10/2019", article.getPublishedAt());
    }
}