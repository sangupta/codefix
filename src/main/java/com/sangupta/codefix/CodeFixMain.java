/**
 *
 * codefix - Perform minor code refactoring tasks
 * Copyright (c) 2014, Sandeep Gupta
 * 
 * http://sangupta.com/projects/codefix
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

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
				.withCommands(Help.class, FixCopyright.class, RightTrim.class);
		
		Cli<Runnable> parser = builder.build();
		
		parser.parse(args).run();
	}

}
