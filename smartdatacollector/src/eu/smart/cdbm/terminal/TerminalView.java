package eu.smart.cdbm.terminal;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.smart.cbdm.logs.LogManager;

@SessionScoped
@ManagedBean
public class TerminalView {

	public String handleCommand(String command, String[] params) {
		if (command.equals("greet")) {
			if (params.length > 0)
				return "Hello " + params[0];
			else
				return "Hello Stranger";
		} else if (command.equals("date"))
			return new Date().toString();
		else if (command.startsWith("lastlog")) {
			return executeLastLogCommand(command);
		} else if (command.startsWith("version")) {
			return "S3Log SMARTCOP Ver 1.0.0";
		} else
			return command + " not found";
	}

	private String executeLastLogCommand(String command) {
		String result = "Unrecognized parameters";
		result = LogManager.getInstance().getLastMessage().toString();

		return result;

	}
}
