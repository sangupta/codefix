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

import io.airlift.command.Command;

import java.io.File;
import java.io.IOException;

@Command(name = "lend", description = "Add a new line ending if not already in the file")
public class FileLineEndings extends AbstractCodeFixCommand {

	@Override
	protected void processEachFile(File file) throws IOException {
		if(file.isDirectory()) {
			return;
		}
		
		System.out.print(file.getAbsolutePath() + ": ");
	}

}
