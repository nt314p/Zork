package com.bayviewglen.zork;

/**
 * Command Class
 * 
 * Has two parts, the main command and the parameters
 * 
 * 
 */
class Command {
	private String mainCommandWord;
	private String[] commandParameters;

	/**
	 * Create a command object
	 * @param mainCommand the command to be created as an object
	 * @param params all the parameters of each individual command (can be null - indicates not defined command)
	 */
	public Command(String mainCommand, String[] params) {
		mainCommandWord = mainCommand;
		commandParameters = params;
	}

	/**
	 * @return mainCommand info properly formatted as a string
	 */
	public String toString() {
		String ret = "";

		ret += "Main: " + mainCommandWord + " | Params: ";
		try {
			for (int i = 0; i < commandParameters.length; i++) {
				ret += commandParameters[i] + ((i != commandParameters.length - 1) ? ", " : "");
			}
		} catch (NullPointerException e) { // catch null pointer exception, no parameters
			ret += "null";
		}

		return ret;
	}

	/**
	 * @return the command word (first word) in the command, null if not understood
	 */
	public String getCommandWord() {
		return mainCommandWord;
	}

//	/**
//	 * Return the second word of this command. Returns null if there was no second
//	 * word.
//	 */
//	public String getSecondWord() {
//		return secondWord;
//	}

	/**
	 * @return true if command is not understood (not in list)
	 */
	public boolean isUnknown() {
		return (mainCommandWord == null);
	}

	/**
	 * @return true if command contains a second word
	 */
	public boolean hasParameters() {
		return (commandParameters.length != 0);
	}
}
