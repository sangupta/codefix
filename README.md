codefix
=======

`codefix` allows to achieve the following code refactoring tasks via the command line
like add/update/remove copyright headers, remove trailing white spaces and more. See below
for more details on each command.

Available commands are:

```
usage: codefix <command> [<args>]

The most commonly used codefix commands are:
    addcopy    Fix copyright amongst code files
    encoding   Convert the file encoding
    ending     Add a new line ending if not already in the file
    help       Display help information
    rmcopy     Remove copyright amongst code files
    rtrim      Remove trailing white-spaces

See 'codefix help <command>' for more information on a specific command.
```

#### Add/update copyright headers

To replace the copyright text contained in file **COPYRIGHT.txt** in all **.JAVA** files
in the folder **c:\code** and all its child folders

```
$ java -jar codefix.jar addcopy -f COPYRIGHT.txt -p *.java -r c:\code
```

#### To remove copyright from files

To remove from all **.JAVA** files under **c:\code** folder and all its child folders:

```
$ java -jar codefix.jar -p *.java -r c:\code
```

#### To update line endings

To add an empty line at the end of each **.TXT** file in folder **c:\docs** and all its child
folders:

```
$ java -jar codefix.jar ending -p *.txt -r c:\docs
```

#### To remove trailing white spaces

To remove all all trailing white sapces from all **.JAVA** files in folder **c:\code** and all
its child folders:

```
$ java -jar codefix.jar rtrim -p *.java -r c:\code
```

#### To change file encoding

To change file encoding from **ISO-8969** to **UTF-8** for all **.TXT** files in folder **c:\textdocs**

```
$ java -jar codefix.jar encoding -p *.txt -r -s ISO-8969 -t UTF-8 c:\textdocs
```


Changelog
---------

**Current Development**

* [Feature] Add/update copyright headers
* [Feature] Remove copyright headers
* [Feature] Remove trailing white-spaces
* [Feature] Add a new line at the end of file

Downloads
---------

The library can be downloaded from Maven Central using:

```xml
<dependency>
    <groupId>com.sangupta</groupId>
    <artifactId>codefix</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Versioning
----------

For transparency and insight into our release cycle, and for striving to maintain backward compatibility,
`codefix` will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

	<major>.<minor>.<patch>

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

License
-------

```
codefix -  Perform minor code refactoring tasks
Copyright (c) 2014-2016, Sandeep Gupta

	http://sangupta.com/projects/codefix

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
