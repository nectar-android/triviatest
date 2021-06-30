package com.example.triviagame.model;

import com.google.gson.annotations.SerializedName;

import static com.example.triviagame.api.KeyAbstract.key_answer;
import static com.example.triviagame.api.KeyAbstract.key_question;

public class QuestionDO {

    @SerializedName(key_question)
    private String question;

    @SerializedName(key_answer)
    private String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
