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
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import io.airlift.airline.Command;
import io.airlift.airline.Option;

/**
 * Tool to fix file encodings - reading from a source encoding and
 * convert the file to a different encoding.
 * 
 * @author sangupta
 *
 */
@Command(name = "encoding", description = "Convert the file encoding")
public class FileEncoding extends AbstractCodeFixCommand {
	
	@Option(name = "-s", description = "Source encoding, default is platform encoding")
	private String sourceEncoding;
	
	@Option(name = "-t", description = "Target encoding, default is UTF-8")
	private String targetEncoding;
	
	private Charset source;
	
	private Charset target;
	
	@Override
	protected void beforeProcessing() {
		if(this.targetEncoding == null || this.targetEncoding.trim().isEmpty()) {
			this.targetEncoding = "UTF-8";
		}
		
		if(this.sourceEncoding == null || this.sourceEncoding.trim().isEmpty()) {
			this.source = Charset.defaultCharset();
		} else {
			this.source = Charset.forName(this.sourceEncoding);
		}
		
		this.target = Charset.forName(this.targetEncoding);
	}

	@Override
	protected String processEachFile(File file) throws IOException {
		String contents = FileUtils.readFileToString(file, this.source);
		FileUtils.writeStringToFile(file, contents, this.target);
		return "encoded";
	}

	@Override
	protected void afterProcessing() {
		this.source = null;
		this.target = null;
	}
}