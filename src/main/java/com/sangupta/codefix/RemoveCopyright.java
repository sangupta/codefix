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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.sangupta.codefix.helper.CopyrightHelper;

import io.airlift.airline.Command;

/**
 * Remove copyright headers from given files
 * 
 * @author sangupta
 *
 */
@Command(name = "rmcopy", description = "Remove copyright amongst code files")
public class RemoveCopyright extends AbstractCodeFixCommand {

	@Override
	protected String processEachFile(File file) throws IOException {
		// read contents
		String contents = FileUtils.readFileToString(file).trim();
		
		// check if file contains comments or not
		boolean hasCopyright = CopyrightHelper.checkCopyrightExists(contents);
		if(!hasCopyright) {
			return "no copyright detected";
		}
		
		// find the ending location
		int index = CopyrightHelper.findCopyrightEnd(contents);
		if(index == -1) {
			return "no proper end to comment detected, skipping!";
		}
		
		contents = contents.substring(index + 1);
		FileUtils.writeStringToFile(file, contents);
		return "copyright removed!";
	}

}
