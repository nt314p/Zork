package com.bayviewglen.zork.main;

import java.util.ArrayList;

public class Riddle {
	
	final int ASCII_a = 97;
	String question;
	String answer;
	
	ArrayList<String> choices;
	
	public Riddle(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public Riddle(String question, ArrayList<String> choices, String answer) {
		this.question = question;
		this.choices = choices;
		this.answer = answer;
		
		if(hasChoices() && !answerInChoices()) {
			choices.add(answer);
		}
	}
	
	public String toString() {
		String str = question + "\n";
		if(hasChoices()) {
			for(int i = 0; i<choices.size(); i++) {
				str+= (char)(i+ASCII_a) + ": " + choices.get(i);
			}
		}
		return str;
	}
	
	public boolean isAnswer(String answer) {
		return this.answer.equals(answer);
	}
	
	public boolean isAnswer(int index) {
		if(!hasChoices())
			return false;
		
		return isAnswer(choices.get(index));
	}
	
	public boolean isAnswer(char answer) {
		if(!hasChoices())
			return false;
		
		int temp = (int)answer - ASCII_a;
		return isAnswer(choices.get(temp));
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public int getAnswerIndex() {
		if(!hasChoices())
			return -1;
		
		return choices.indexOf(answer);
	}
	
	public char getAnswerChar() {
		int temp = getAnswerIndex();
		if(temp == -1)
			return (char)temp;
		
		return (char)(temp + ASCII_a);
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
		if(hasChoices() && !answerInChoices())
			choices.add(answer);
	}
	
	
	public ArrayList<String> getChoices(){
		return choices;
	}
	
	public boolean hasChoices() {
		return choices != null;
	}
	
	public boolean answerInChoices() {
		if(hasChoices())
			return choices.contains(answer);
		return false;
	}
	
	public boolean equals(Riddle riddle) {
		return question.equals(riddle.question) && answer.equals(riddle.answer);
	}

}
