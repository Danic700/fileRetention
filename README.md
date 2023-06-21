Ephemeral File Sharing -

The file retention system allows users to upload images up to 10mb and save them for a requested number of minutes.
A unique link will be generated for every image uploaded, once the retention time has passed for a link it will be deleted, once all unique links are deleted that image will be deleted as well.


Installation guide:
Make sure to have a MySQL instance running on your machine on port 3306 and create a fileRetention Schema (this can be configured in the app.properties to something else)
Make sure to have JDK17 installed to run the server side on port 8080.
Make sure to have npm installed along with react-js, axios, react-copy-to-clipboard

Example of the UI:
![image](https://github.com/Danic700/fileRetention/assets/14127006/1e380bb7-7822-4d0a-8a65-4982d5001a31)


The system can also be tested easily using the Swagger UI: http://localhost:8080/swagger-ui/index.html


System explanation:

The backend is written in Java using Spring and the images are saved to MySQL.
There are two tables: File and Link.
Every image gets uploaded to the File table and subsquently we will create unique link for it in the Links table.
If two files have the same hash value they are determined to be the same and in that case we will just generate a new link for that file with it's own retention time.
Example of tables:
![image](https://github.com/Danic700/fileRetention/assets/14127006/8d22473c-17cd-4a66-b7de-f695a2be3631)
![image](https://github.com/Danic700/fileRetention/assets/14127006/e8107e97-b2cd-4c80-824f-56ce2f6b3869)



There are two SQL events that run in the background, one for deleting links which have expired and another for deleting files with 0 links.
A locking mechanism was implemented on the File table for each time a file gets uploaded to handle a case in which we try to add a link to a duplicate file which could expire at a certain moment an SQL event might try to delete it.
Refer to fileRetention.SQL and fileServiceImpl.java for more information.








