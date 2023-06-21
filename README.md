Ephemeral File Sharing -

The file retention system allows users to upload images up to 10mb and save them for a requested number of minutes.
A unique link will be generated for every image uploaded, once the retention time has passed for a link it will be deleted, once all unique links are deleted that image will be deleted as well.


Installation guide:
Make sure to have a MySQL instance running on your machine on port 3306 and create a fileRetention Schema (this can be configured in the app.properties to something else)
Make sure to have JDK17 installed to run the server side on port 8080.
Make sure to have npm installed along with react-js, axios, react-copy-to-clipboard


System explanation:

The backend is written in Java using Spring and the images are saved to MySQL.
There are two tables: File and Link.
Every image gets uploaded to the File table and subsquently we will create unique link for it in the Links table.
If two files have the same hash value they are determined to be the same and in that case we will just generate a new link for that file with it's own retention time.

There are two SQL events that run in the background, one for deleting links which have expired and another for deleting files with 0 links.
I implemented a locking mechanism each time a file gets uploaded to handle a case in which we try to add a link to a file which could expire at a certain moment an SQL event might try to delete it.
Refer to fileRetention.SQL and fileServiceImpl.java for more information.


![image](https://github.com/Danic700/fileRetention/assets/14127006/76b2d726-3e7c-49ce-add4-de23f1e61764)






