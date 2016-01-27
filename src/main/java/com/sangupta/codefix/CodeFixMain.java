/**
 *
 * codefix - Perform minor code refactoring tasks
 * Copyright (c) 2014-2016, Sandeep Gupta
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

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.sangupta.jerry.util.AssertUtils;

import io.airlift.airline.Cli;
import io.airlift.airline.Cli.CliBuilder;
import io.airlift.airline.Help;

/**
 * The main entry point to the command line tool.
 * 
 * @author sangupta
 *
 */
public class CodeFixMain {
	
	public static void main(String[] args) {
		// detect all available commands
		Reflections reflections = new Reflections("com.sangupta.codefix");
		Set<Class<? extends AbstractCodeFixCommand>> commands = reflections.getSubTypesOf(AbstractCodeFixCommand.class);
		
		if(AssertUtils.isEmpty(commands)) {
			System.out.println("No codefix command available.");
			return;
		}
		
		Iterator<Class<? extends AbstractCodeFixCommand>> iterator = commands.iterator();
		while(iterator.hasNext()) {
			Class<? extends AbstractCodeFixCommand> clazz = iterator.next();
			if(Modifier.isAbstract(clazz.getModifiers())) {
				iterator.remove();
			}
		}
		
		// add help command
		List<Class<? extends Runnable>> commandList = new ArrayList<>();
		commandList.addAll(commands);
		commandList.add(Help.class);
		
		// build up the command line tool
		CliBuilder<Runnable> builder = Cli.<Runnable>builder("codefix")
				.withDescription("Fix code refactoring issues")
				.withDefaultCommand(Help.class)
				.withCommands(commandList);
		
		Cli<Runnable> parser = builder.build();
		
		parser.parse(args).run();
	}

}