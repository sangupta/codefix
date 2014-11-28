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
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

@Command(name = "copyright", description = "Fix copyright amongst code files")
public class FixCopyright implements Runnable {
	
	public static final String SYSTEM_NEW_LINE = System.getProperty("line.separator");
	
	@Option(name = "-f", description = "The file from which to read the copyright notice")
	private String copyrightFile;
	
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
		
		String copyright = "";
//		try {
//			copyright = FileUtils.readFileToString(new File(this.copyrightFile));
//		} catch (IOException e) {
//			System.out.println("Unable to read copyright notice from file: " + this.copyrightFile);
//			return;
//		}
		
		// now find the files
		List<File> matchedFiles = getMatchedFiles();
		
		if(matchedFiles == null) {
			return;
		}
		
		for(File file : matchedFiles) {
			try {
				processEachFile(file, copyright);
			} catch (IOException e) {
				System.out.println("Could not process file: " + file.getAbsolutePath() + " due to error: " + e.getMessage());
			}
		}
		
	}
	
	private void processEachFile(File file, String copyrightNotice) throws IOException {
		System.out.print(file.getAbsolutePath() + ": ");
		// read contents
		String contents = FileUtils.readFileToString(file).trim();
		
		// check if file contains comments or not
		boolean hasCopyright = checkCopyrightExists(contents);
		
		if(!hasCopyright) {
			// append the copyright and move on
			System.out.println("adding copyright!");
			contents = copyrightNotice + SYSTEM_NEW_LINE + contents;
//			FileUtils.writeStringToFile(file, contents);
			return;
		}
		
		// remove comment
		if(contents.startsWith("/*")) {
			// this is a multi-line comment - remove it
			int index = contents.indexOf("*/");
			if(index == -1) {
				System.out.println("No end-of-multi-line-comment found, skipping.");
				return;
			}
			
			contents = contents.substring(index + 1);
			contents = copyrightNotice + SYSTEM_NEW_LINE + contents;
//			FileUtils.writeStringToFile(file, contents);
			System.out.println("Multi-line comment changed!");
			return;
		}
		
		// something different
		System.out.println("not yet supported");
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

	public static void main(String[] args) {
		FixCopyright fc = new FixCopyright();
		fc.copyrightFile = "abc";
		fc.recursive = true;
		fc.pattern = "*.java";
		fc.workingFolder = "/Users/sangupta/git/sangupta/html-gen";
		fc.run();
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
