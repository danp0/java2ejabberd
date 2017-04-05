# java2ejabberd

An application demonstrating communication with the ejabberd
chat server over 
[jinterface](http://erlang.org/doc/apps/jinterface/jinterface_users_guide.html).

## Building

You will need [maven](https://maven.apache.org) to build this project.

## Usage

To run the application:

* Copy the java2ejabberd-1.0-SNAPSHOT.jar to the server hosting ejabberd.
* Copy the erlang cookie to the same directory as your jar file. For example,
```
 # cp /var/lib/ejabberd/spool/.erlang.cookie .
 # chown <owner>:<group> .erlang.cookie
```
* Run the application:
```
  $ java -jar java2ejabberd-1.0-SNAPSHOT.jar
```

### Bugs
...

## License

Copyright © 2017 D. Pollind

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.


