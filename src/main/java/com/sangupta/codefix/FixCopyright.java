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

import io.airlift.command.Arguments;
import io.airlift.command.Command;
import io.airlift.command.Option;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

@Command(name = "addcopy", description = "Fix copyright amongst code files")
public class FixCopyright extends AbstractCodeFixCommand {
	
	@Option(name = "-f", description = "The file from which to read the copyright notice")
	private String copyrightFile;

	@Arguments
	private String workingFolder;
	
	private String copyrightNotice;
	
	@Override
	protected void beforeProcessing() {
		try {
			this.copyrightNotice = FileUtils.readFileToString(new File(this.copyrightFile));
		} catch (IOException e) {
			System.out.println("Unable to read copyright notice from file: " + this.copyrightFile);
			return;
		}
	}

	protected String processEachFile(File file) throws IOException {
		// read contents
		String contents = FileUtils.readFileToString(file).trim();
		
		// check if file contains comments or not
		boolean hasCopyright = checkCopyrightExists(contents);
		
		if(!hasCopyright) {
			// append the copyright and move on
			contents = this.copyrightNotice + SYSTEM_NEW_LINE + contents;
//			FileUtils.writeStringToFile(file, contents);
			return "adding copyright";
		}
		
		// remove comment
		if(contents.startsWith("/*")) {
			// this is a multi-line comment - remove it
			int index = contents.indexOf("*/");
			if(index == -1) {
				return "No end-of-multi-line-comment found, skipping.";
			}
			
			contents = contents.substring(index + 1);
			contents = this.copyrightNotice + SYSTEM_NEW_LINE + contents;
//			FileUtils.writeStringToFile(file, contents);
			return "Multi-line comment changed!";
		}
		
		// something different
		return "not yet supported";
	}

	/**
	 * Check if a copyright header exists or not
	 * 
	 * @param contents
	 * @return
	 */
	private boolean checkCopyrightExists(String contents) {
		if(contents.startsWith("/*") || contents.startsWith("//")) {
			return true;
		}
		
		return false;
	}

}
