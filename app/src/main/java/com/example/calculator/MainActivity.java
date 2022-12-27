package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public static String removeLastCharRegex(String s) {
    return (s == null) ? null : s.replaceAll(".$", "");
    }
    public void input(View view){

        TextView output = (TextView)findViewById(R.id.out);
        String curr = output.getText().toString();


        switch(view.getId()) {
            case R.id.b0:
                curr += '0';
                output.setText(curr);
                break;

            case R.id.b1:
                curr += '1';
                output.setText(curr);
                break;

            case R.id.b2:
                curr += '2';
                output.setText(curr);
                break;
            case R.id.b3:
                curr += '3';
                output.setText(curr);
                break;
            case R.id.b4:
                curr += '4';
                output.setText(curr);
                break;
            case R.id.b5:
                curr += '5';
                output.setText(curr);
                break;
            case R.id.b6:
                curr += '6';
                output.setText(curr);
                break;
            case R.id.b7:
                curr += '7';
                output.setText(curr);
                break;
            case R.id.b8:
                curr += '8';
                output.setText(curr);
                break;
            case R.id.b9:
                curr += '9';
                output.setText(curr);
                break;
            case R.id.bplus:
                curr += '+';
                output.setText(curr);
                break;
            case R.id.bminus:
                curr += '-';
                output.setText(curr);
                break;
            case R.id.bmul:
                curr += '*';
                output.setText(curr);
                break;
            case R.id.bdiv:
                curr += '/';
                output.setText(curr);
                break;
            case R.id.bdel:
                curr=removeLastCharRegex(curr);
                output.setText(curr);
                break;
            case R.id.bac:
                curr = "";
                output.setText(curr);
                break;
            case R.id.bdot:
                curr += '.';
                output.setText(curr);
                break;

            case R.id.beq:
                Double result = eval(curr);
                output.setText(result.toString());
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}