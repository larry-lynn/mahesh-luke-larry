## Git Cheat Sheet ##

### How to get a local repository of the source code to work on ###
```
git clone https://code.google.com/p/mahesh-luke-larry/
```


### Get your local copy of the repository up to date ###
this is a good idea to do just before you start a coding session to make sure you have all of the code from the other team members
```
git pull
```

### Get the copy of the code without updating ###
Sometimes you want to get the code without possibly changing what you have locally
```
git fetch
```

### Stage a locally modified file to be committed to your local repository ###
```
git add myfile
```

### See the difference between your locally modified file and the most recent version of that file that was checked into the repository ###
It is a very good idea to get into the habit of doing this before your commit to double check your work
```
git diff myfile
```

### Check the current status of files and your local repository ###
Shows new files that have not yet been added to the repository, files that exist in the repository but have local changes that have not been checked in and files that have been checked in that are scheduled to be committed.  Again, it's a good idea to get into the habit of doing this before a commit.
```
git status
```

### Commit to your local repsitory ###
This commits all files that are staged, not just 1
```
git commit -m 'my commit message here'
```

### Publish the changes you have committed to your local repository back to the master repository ###
```
git push
```

### Check the log to see who made changes to a file and when ###
```
git log myfile
```

### See the differences between any 2 versions of a file in the repository for 2 commits at any point in time in the file's history ###
you get those big long hashes from the git log
```
git diff -r <big long hash 1> -r <big long hash 2> myfile
```

### Set it up so I don't have to authenticate every time I push with that weird password for Google code ###
Go to your home directory (location changes from machine to machine)
```
pwd
/students/hilarion.lynn
```
Make a file with this special name.  If it already exists, add an extra line to it
```
.netrc
```
Add information that looks like this to the file and save it
```
machine code.google.com login <someuser>@gmail.com password <some-weird-password>
```

### If you get an error on git pull ###
and the error says something like
'this would overwrite local changes.  stash your current work first'
try
```
mv file.java file.java.bak
git pull
```
you can still get all of your existing work out of file.java.bak

### What if I deleted a file and git pull says 'up to date' ###
you can refresh the file from the repository with
```
rm file.java
git checkout file.java
```

### Unstage a locally modified file & remove it from the commit list ###
use this to undo your changes if you messed up a 'git add'.
you can't undo your work this way after you have done a 'git commit'
```
git reset HEAD myfile
```

### Delete a file from the repository ###
```
git rm <file>
```

### Add a git tag ###
This tags the current point in history of the git repository
```
git tag -a parser-freeze -m 'freezing a copy of the parser for testing by Dr. Ross & his TA'
git tag
git push --tags
```

### Get a copy of the code in the state marked by a certain git tag ###
```
git clone https://code.google.com/p/mahesh-luke-larry/
cd mahesh-luke-larry/
git tag -l
git checkout parser-freeze
```