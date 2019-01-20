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
		System.out.println("Main: " + mainCommand);
		System.out.print("Params: ");
		try {
			for (String s : params) {
				System.out.print(s + ", ");
			}
			System.out.println();
		} catch (NullPointerException e) {
			System.out.println("null");
		}
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
