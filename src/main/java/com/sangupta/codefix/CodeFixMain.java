package com.sangupta.codefix;

import io.airlift.command.Cli;
import io.airlift.command.Help;
import io.airlift.command.Cli.CliBuilder;

public class CodeFixMain {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		CliBuilder<Runnable> builder = Cli.<Runnable>builder("codefix")
				.withDescription("Fix code refactoring issues")
				.withDefaultCommand(Help.class)
				.withCommands(Help.class, FixCopyright.class);
		
		Cli<Runnable> parser = builder.build();
		
		parser.parse(args).run();
	}

}
