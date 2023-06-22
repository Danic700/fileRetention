_Ephemeral File Sharing -_

The file retention system allows users to upload images of up to 10mb in size and save them for a requested number of minutes.

A url will be generated for every image uploaded, once the retention time has passed for a url it will be deleted, once all urls for an image are deleted that image will be deleted as well.

The storage layer conserves space by only storing 1 copy of an image even if that image was uploaded multiple times. 


**Installation guide:**

Make sure to have a MySQL instance running on your machine on port 3306 and create a fileRetention Schema (this can be configured in the application.properties to something else).

Make sure to have JDK17 installed to run the server side on port 8080.

Make sure to have npm installed along with react-js, axios, react-copy-to-clipboard to run the front end.

Example of the UI:
![image](https://github.com/Danic700/fileRetention/assets/14127006/1e380bb7-7822-4d0a-8a65-4982d5001a31)

The system can also be tested easily using the Swagger UI: http://localhost:8080/swagger-ui/index.html


**System explanation:**


The backend is written in Java using Spring and the images are saved to MySQL.

There are two tables: File and Link.

Every image gets uploaded to the File table and subsequently we will create a unique link for it in the Link table.

If two files have the same hash value they are determined to be the same and in that case we will just generate a new link for that file with its own retention time instead of uploading that file again.

Example of tables:

![image](https://github.com/Danic700/fileRetention/assets/14127006/8d22473c-17cd-4a66-b7de-f695a2be3631)
![image](https://github.com/Danic700/fileRetention/assets/14127006/e8107e97-b2cd-4c80-824f-56ce2f6b3869)



There are two SQL events that run in the background once every minute, one for deleting links whose ttl (retention time) has passed and another for deleting files with 0 links which also cleans up the storage of the files.

A locking mechanism was implemented on the File table to handle a case in which a duplicate file gets uploaded and during it's processing and link creation an SQL event might try to delete it.
Refer to fileRetention.SQL and fileServiceImpl.java for more information.








