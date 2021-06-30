package com.example.triviagame.api;


import com.example.triviagame.model.QuestionDO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.example.triviagame.api.KeyAbstract.RANDOM_API;

public interface RequestAPI {

    /**
     * api ask for question
     */
    @GET(RANDOM_API)
    Call<List<QuestionDO>> requestQuestion();

}