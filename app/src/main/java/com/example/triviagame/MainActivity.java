package com.example.triviagame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.example.triviagame.api.ApiUtils;
import com.example.triviagame.databinding.ActivityMainBinding;
import com.example.triviagame.model.QuestionDO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private String strAnswer;
    private String apiAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();

        if (utils.isNetConnected()) {
            callQueApi();
        }

    }

    private void init() {
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAnswer = binding.etAnswer.getText().toString();
                if (strAnswer.length() == 0) {
                    binding.etAnswer.requestFocus();
                    binding.etAnswer.setError("Please Enter your answer");
                } else {
                    binding.etAnswer.setText("");
                    hideSoftKeyboard(MainActivity.this);
                    if (apiAnswer.toLowerCase().equals(strAnswer.toLowerCase())) {
                        checkAnswer(true);
                    } else {
                        checkAnswer(false);
                    }
                }
            }
        });


        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callQueApi();
            }
        });

    }

    private void callQueApi() {
        startProgress(this);

        ApiUtils.getAPIService().requestQuestion().enqueue(new Callback<List<QuestionDO>>() {
            @Override
            public void onResponse(Call<List<QuestionDO>> call, Response<List<QuestionDO>> response) {
                try {
                    dismissProgress();
                    //on status success
                    if (response.isSuccessful()) {
                        List<QuestionDO> questionDOList = response.body();
                        if (questionDOList != null & questionDOList.size() > 0) {
                            QuestionDO questionDO = questionDOList.get(0);
                            if (questionDO != null) {
                                apiAnswer = questionDO.getAnswer();
                                binding.setModel(questionDO);
                            } else {
                                utils.Toast("No questions available now");
                            }
                        }
                    } else {
                        utils.Toast("Wrong entry");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissProgress();
                    utils.Toast("Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<QuestionDO>> call, Throwable t) {
                dismissProgress();
                utils.Toast("Network error");
            }
        });
    }

    private void checkAnswer(boolean isSuccess) {
        if (isSuccess) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setMessage("Great Your answer is correct!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            callQueApi();
                        }
                    });
            alertDialog.show();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setMessage("Oops Your answer is wrong!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            callQueApi();
                        }
                    });
            alertDialog.show();
        }
    }


}