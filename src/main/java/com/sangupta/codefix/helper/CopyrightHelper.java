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

package com.sangupta.codefix.helper;

import com.sangupta.jerry.io.StringLineIterator;

public class CopyrightHelper {

	/**
	 * Check if a copyright header exists or not in the files
	 * contents
	 * 
	 * @param contents
	 * @return
	 */
	public static boolean checkCopyrightExists(String contents) {
		if(contents == null) {
			return false;
		}
		
		if(contents.length() == 0) {
			return false;
		}
		
		contents = contents.trim();
		if(contents.startsWith("/*") || contents.startsWith("//")) {
			return true;
		}
		
		return false;
	}

	public static int findCopyrightEnd(String contents) {
		if(contents.startsWith("/*")) {
			// this is a multi-line comment - remove it
			return contents.indexOf("*/") + 1;
			
		}
		
		// single line comment
		// we need to find the last line starting with '//'
		// and then return its end location
		StringLineIterator iterator = new StringLineIterator(contents);
		while(iterator.hasNext()) {
			String line = iterator.next();
			line = line.trim();
			if(line.startsWith("//")) {
				continue;
			}
			
			if(line.equals("")) {
				continue;
			}
			
			// we did encounter a line that is not a comment start
			// break at this point
			final int begin = iterator.getLineBegin();
			if(begin == 0) {
				return begin;
			}
			
			return begin - 1;
		}
		
		return -1;
	}
	
}
