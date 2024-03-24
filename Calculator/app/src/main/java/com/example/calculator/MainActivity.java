package com.example.calculator;//package com.example.calculator;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.google.android.material.button.MaterialButton;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//    TextView result_tv,solution_tv;
//    MaterialButton button_clear,button_openbracket,button_closebracket,button_divide,button_multiply,button_substraction,button_addition,button_equal,button_Ac;
//    MaterialButton button_1,button_2,button_3,button_4,button_5,button_6,button_7,button_8,button_9,button_0,button_point;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        result_tv=findViewById(R.id.result_tv);
//        solution_tv=findViewById(R.id.solution_tv);
//        assignId(button_clear,R.id.button_clear);
//        assignId(button_openbracket,R.id.button_openbracket);
//        assignId(button_closebracket,R.id.button_closebracket);
//        assignId(button_divide,R.id.button_divide);
//        assignId(button_8,R.id.button_8);
//        assignId(button_9,R.id.button_9);
//        assignId(button_7,R.id.button_7);
//        assignId(button_6,R.id.button_6);
//        assignId(button_5,R.id.button_5);
//        assignId(button_4,R.id.button_4);
//        assignId(button_3,R.id.button_3);
//
//        assignId(button_2,R.id.button_2);
//        assignId(button_1,R.id.button_1);
//        assignId(button_0,R.id.button_0);
//        assignId(button_point,R.id.button_point);
//        assignId(button_addition,R.id.button_addition);
//        assignId(button_substraction,R.id.button_substraction);
//        assignId(button_multiply,R.id.button_multiply);
//        assignId(button_equal,R.id.button_equal);
//        assignId(button_Ac,R.id.button_Ac);
//
//
//
//
//
//
//
//
//
//    }
//    void assignId(MaterialButton btn, int id){
//        btn=findViewById(id);
//        btn.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        MaterialButton button=(MaterialButton) v;
//        String buttonText = button.getText().toString();
//        String dataToCalculate = solution_tv.getText().toString();
//        if (buttonText.equals("Ac")){
//            solution_tv.setText("");
//            result_tv.setText("0");
//            return;
//        }
//        if (buttonText.equals("=")){
//            solution_tv.setText(result_tv.getText());
//            return;
//        }
//        if(buttonText.equals("C")){
//            dataToCalculate=dataToCalculate.substring(0,dataToCalculate.length()-1);
//        }else {
//            dataToCalculate=dataToCalculate+buttonText;
//
//        }
//
//        solution_tv.setText(dataToCalculate);
//    }
//    String getResult(String data){
//
//        return data;
//    }
//}
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private TextView solutionTextView;
    private String currentInput = "";
    private double operand1 = Double.NaN;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.result_tv);
        solutionTextView = findViewById(R.id.solution_tv);

        int[] numberBtnIds = {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
                R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};
        for (int id : numberBtnIds) {
            Button btn = findViewById(id);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleInput(((Button) v).getText().toString());
                }
            });
        }

        Button clearBtn = findViewById(R.id.button_clear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                operand1 = Double.NaN;
                operator = "";
                resultTextView.setText("");
                solutionTextView.setText("");
            }
        });

        int[] operatorBtnIds = {R.id.button_addition, R.id.button_substraction, R.id.button_multiply, R.id.button_divide};
        for (int id : operatorBtnIds) {
            Button operatorBtn = findViewById(id);
            operatorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOperator(((Button) v).getText().toString());
                }
            });
        }

        Button equalsBtn = findViewById(R.id.button_equal);
        equalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });

        Button decimalBtn = findViewById(R.id.button_point);
        decimalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.contains(".")) {
                    currentInput += ".";
                    resultTextView.setText(currentInput);
                }
            }
        });
    }

    private void handleInput(String input) {
        currentInput += input;
        resultTextView.setText(currentInput);
    }

    private void handleOperator(String op) {
        if (!Double.isNaN(operand1)) {
            calculateResult();
        }
        operand1 = Double.parseDouble(currentInput);
        operator = op;
        solutionTextView.setText(currentInput + " " + operator);
        currentInput = "";
    }

    private void calculateResult() {
        if (!Double.isNaN(operand1) && !currentInput.isEmpty()) {
            double operand2 = Double.parseDouble(currentInput);
            double result = Double.NaN;
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 != 0) {
                        result = operand1 / operand2;
                    }
                    break;
            }
            if (!Double.isNaN(result)) {
                resultTextView.setText(String.valueOf(result));
                solutionTextView.setText("");
                operand1 = result;
                currentInput = "";
            } else {
                resultTextView.setText("Error");
            }
        }
    }
}

