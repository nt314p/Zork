package com.bayviewglen.zork.main;

import java.util.ArrayList;

public class Riddle {
	
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
	
	public boolean isAnswer(String answer) {
		return this.answer.equals(answer);
	}
	
	public boolean isAnswer(int index) {
		if(!hasChoices())
			return false;
		
		return isAnswer(choices.get(index));
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
	
	public void setAnswer(String answer) {
		this.answer = answer;
		if(hasChoices() && !answerInChoices())
			choices.add(answer);
	}
	
	public int getAnswerIndex() {
		return choices.indexOf(answer);
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
