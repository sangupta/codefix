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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import io.airlift.airline.Command;

/**
 * Remove trailing white spaces from files.
 * 
 * @author sangupta
 *
 */
@Command(name = "rtrim", description = "Remove trailing white-spaces")
public class RightTrim extends AbstractCodeFixCommand {
	
	protected String processEachFile(File file) throws IOException {
		final List<String> lines = new ArrayList<String>();
		LineIterator iterator = FileUtils.lineIterator(file);
		boolean modified = false;
		while(iterator.hasNext()) {
			String line = iterator.next();
			
			String newline = StringUtils.stripEnd(line, null);
			if(line.length() != newline.length()) {
				modified = true;
			}
			
			lines.add(newline);
		}
		
		FileUtils.writeLines(file, lines);
		return String.valueOf(modified);
	}

}