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
import org.apache.commons.lang3.StringUtils;

import com.sangupta.codefix.helper.CopyrightHelper;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;
import io.airlift.airline.Option;

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
		boolean hasCopyright = CopyrightHelper.checkCopyrightExists(contents);
		
		if(!hasCopyright) {
			// append the copyright and move on
			contents = this.copyrightNotice + SYSTEM_NEW_LINE + contents;
			FileUtils.writeStringToFile(file, contents);
			return "adding copyright";
		}
		
		// remove comment
		int index = CopyrightHelper.findCopyrightEnd(contents);
		if(index == -1) {
			System.out.println("No proper ending of comment detected, skipping!");
		}
		
		contents = contents.substring(index + 1);
		contents = this.copyrightNotice + SYSTEM_NEW_LINE + StringUtils.stripStart(contents, null);
		FileUtils.writeStringToFile(file, contents);
		return "copyright updated!";
	}

}
