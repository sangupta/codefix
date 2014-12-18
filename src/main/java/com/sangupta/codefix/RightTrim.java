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
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;

@Command(name = "rtrim", description = "Remove trailing white-spaces")
public class RightTrim implements Runnable {
	
	public static final String SYSTEM_NEW_LINE = System.getProperty("line.separator");
	
	@Option(name = "-p", description = "The file pattern to match")
	private String pattern;
	
	@Option(name = "-r", description = "Recurse way through subdirectories")
	private boolean recursive;
	
	@Arguments
	private String workingFolder;

	public void run() {
		if(this.pattern == null || this.pattern.trim().isEmpty()) {
			this.pattern = "*.*";
		}
		
		List<File> matchedFiles = getMatchedFiles();
		
		if(matchedFiles == null) {
			return;
		}
		
		for(File file : matchedFiles) {
			try {
				processEachFile(file);
			} catch (IOException e) {
				System.out.println("Could not process file: " + file.getAbsolutePath() + " due to error: " + e.getMessage());
			}
		}
		
	}
	
	private void processEachFile(File file) throws IOException {
		if(file.isDirectory()) {
			return;
		}
		
		final List<String> lines = new ArrayList<String>();
		System.out.print(file.getAbsolutePath() + ": ");

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
		System.out.println(modified);
	}

	/**
	 * Get a list of all matched files
	 * 
	 * @return
	 */
	private List<File> getMatchedFiles() {
		if(this.workingFolder == null || this.workingFolder.isEmpty()) {
			this.workingFolder = ".";
		}
		
		final File currentDir = new File(this.workingFolder);
		if(!currentDir.exists() || !currentDir.isDirectory()) {
			System.out.println("Directory does not exists: " + this.workingFolder);
			return null;
		}
		
		final FileFilter wildcardFileFilter = new WildcardFileFilter(this.pattern, IOCase.SYSTEM);
		
		if(!this.recursive) {
			File[] files = currentDir.listFiles(wildcardFileFilter);
			return Arrays.asList(files);
		}
		
		// we need to go recursive
		List<File> files = new ArrayList<File>();
		getMatchedFiles(files, currentDir, wildcardFileFilter);
		
		return files;
	}

	/**
	 * Get all matched files for the wild card filter in the given directory and all
	 * its child folders
	 * 
	 * @param list
	 * @param dir
	 * @param wildcardFileFilter
	 */
	private void getMatchedFiles(List<File> list, File dir, FileFilter wildcardFileFilter) {
		File[] files = dir.listFiles(wildcardFileFilter);
		if(files != null) {
			list.addAll(Arrays.asList(files));
		}
		
		// check for all sub folders
		File[] dirs = dir.listFiles((FileFilter) DirectoryFileFilter.INSTANCE);
		if(dirs == null || dirs.length == 0) {
			return;
		}
		
		for(File subDir : dirs) {
			getMatchedFiles(list, subDir, wildcardFileFilter);
		}
	}
	
}
