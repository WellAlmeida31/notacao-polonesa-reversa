package com.wellalmeida31.notpolonesa;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static java.util.Collections.unmodifiableList;
/**
 *
 * @author Wellingon Almeida
 */
public class PosFixedCalc {
    
    public Integer calcPosFixed(String str){
        char[] caracteres = str.toCharArray();
        Stack<Integer> num = new Stack<>();
        Stack<Character> op = new Stack<>();
        final List<String> OPERACOES = unmodifiableList(Arrays.asList("+","-","*","/"));
        
        for(int i = 0; i < caracteres.length; i++){
            char c = caracteres[i];
            if(c == ' '){
                continue;
            }
            if(Character.isDigit(c)){
                String digitos = c + "";
                while(i + 1 < caracteres.length && Character.isDigit(caracteres[i + 1])){
                    digitos = digitos + caracteres[i + 1];
                    i++;
                }
                num.push(Integer.valueOf(digitos));
            } else if(c == '('){
                op.push(c);
            } else if(c == ')'){
                while(op.peek() != '('){
                    int res = calcular(op.pop(), num.pop(), num.pop());
                    num.push(res);
                }
                op.pop();
            } else if(OPERACOES.contains(c + "")){
                while(!op.empty() && ordem(op.peek()) >= ordem(c)){
                    int res = calcular(op.pop(), num.pop(), num.pop());
                    num.push(res);
                }
                op.push(c);
            }
        }
        
        while(!op.empty()){
            num.push(calcular(op.pop(), num.pop(), num.pop()));
        }
        
        return num.pop();
    }

    private int calcular(Character op, Integer b, Integer a) {
        switch(op){
            case '/':
                if(b == 0) throw new ArithmeticException("Error div 0");
                return a/b;
            case '*':
                return a*b;
            case '+':
                return a+b;
            case '-':
                return a-b;
        }
        return 0;
    }

    private int ordem(char op) {
        switch(op){
            case '/':
            case '*':
                return 2;
            case '+':
            case '-':
                return 1;
        }
        return 0;
    }
    
}
