package com.bayviewglen.zork.main;

import java.util.ArrayList;

public class Riddle {
	
	private String question;
	private String answer;
	
	private ArrayList<String> choices;
	
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
	
	public Riddle(String question, ArrayList<String> choices, int ansIndex) {
		this.question = question;
		this.choices = choices;
		if(choices.size() <= ansIndex)
			throw new IndexOutOfBoundsException(ansIndex + " was out of bounds");
		this.answer = choices.get(ansIndex);
	}
	
	public String toString() {
		String str = question + "\n";
		if(hasChoices()) {
			for(int i = 0; i<choices.size(); i++) {
				str+= (char)(i+'a') + ": " + choices.get(i) + "\n";
			}
		}
		return str;
	}
	
	public String displayAll() {
		String str = toString();
		str += hasChoices() ? "\n" : "";
		str += "Answer: ";
		str += hasChoices() ? getAnswerChar() : getAnswer();
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
		
		int temp = (int)(answer - 'a');
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
		if(!hasChoices()) {
			System.out.println("This riddle doesn't have choices.");
			return -1;
		}
		
		return choices.indexOf(answer);
	}
	
	public char getAnswerChar() {
		int temp = getAnswerIndex();
		if(temp == -1)
			return ' ';
					
		return (char)(temp + 'a');
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
