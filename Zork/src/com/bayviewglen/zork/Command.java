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
	 * Create a command object. Params can be <null>, but if the main command is
	 * <null>, it indicates the command is not defined
	 */
	public Command(String mainCommand, String[] params) {
		mainCommandWord = mainCommand;
		commandParameters = params;
	}

	/**
	 * Returns all command info formatted in a nice string
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
	 * Return the command word (the first word) of this command. If the command was
	 * not understood, the result is null.
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
	 * Return true if this command was not understood.
	 */
	public boolean isUnknown() {
		return (mainCommandWord == null);
	}

	/**
	 * Return true if the command has a second word.
	 */
	public boolean hasParameters() {
		return (commandParameters.length != 0);
	}
}
