# zip-streaming
Example of zipping output stream

* Uses basic RestTemplate
* Requests a list of files via Http, streams the output to the reponse output buffer, which is wrapper in a ZipOutputStream
 
* Tested by requesting files from s3, but also from a local web server to allow for easier testing of larger file streaming.
