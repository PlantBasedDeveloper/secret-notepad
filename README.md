# Getting Started

### Steps

To run the application on your local machine, follow the following steps from the project's root directory:

* make the shell script `run.sh` executable
  * `$ chmod +x run.sh`
* run it
  * `./run.sh`

### Required resources

* docker
* maven

### Tests

A tool like postman can be used to test.

To create a secret note, send a POST request to http://localhost:1234/secret-notepad/create. A JSON string containing the fields of the note has to be added to the body. If there were no errors, The response should contain the id of the newly created note. 
```
{
  "title": "<title>",
  "note":"<note contents>"
}
```

To read a note, send a GET request to http://localhost:1234/secret-notepad/note/<id> . The last URL is the id of the note.

To fetch all notes, send a GET request to http://localhost:1234/secret-notepad/notes

To update a node send a POST request to http://localhost:1234/secret-notepad/update/<id> . The last URL is the id of the note.
A JSON string containing the updated fields of the note has to be added to the body.
```
{
  "title": "<title>",
  "note":"<note contents>"
}
```

To delete a note, send a DELETE request to http://localhost:1234/secret-notepad/delete/<id> The last URL is the id of the note.
Please note that this request triggers a hard delete. Soft delete was not implemented.