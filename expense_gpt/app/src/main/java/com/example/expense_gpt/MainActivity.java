package com.example.expense_gpt;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> expenses = new ArrayList<>();
    private EditText expenseEditText;
    private TextView expenseListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseEditText = findViewById(R.id.expenseEditText);
        expenseListTextView = findViewById(R.id.expenseListTextView);
    }

    public void addExpense(View view) {
        String expense = expenseEditText.getText().toString().trim();
        if (!expense.isEmpty()) {
            expenses.add(expense);
            updateExpenseList();
            expenseEditText.setText("");
        }
    }

    private void updateExpenseList() {
        StringBuilder expenseList = new StringBuilder();
        for (String expense : expenses) {
            expenseList.append(expense).append("\n");
        }
        expenseListTextView.setText(expenseList.toString());
    }
}
