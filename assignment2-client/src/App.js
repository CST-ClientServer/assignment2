// Filename - App.js

import axios from "axios";
import React, { Component } from "react";

class App extends Component {
    state = {
        // Initially, no file is selected
        selectedFile: null,
        caption: "",
        date: ""
    };

    // On file select (from the pop up)
    onFileChange = (event) => {
        // Update the state
        this.setState({
            selectedFile: event.target.files[0]
        });
    };

    // On input change for caption and date
    onInputChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    // On file upload (click the upload button)
    onFileUpload = () => {
        // Create an object of formData
        const formData = new FormData();

        // Update the formData object
        formData.append("file", this.state.selectedFile);
        formData.append("caption", this.state.caption);
        formData.append("date", this.state.date);

        // Details of the uploaded file
        console.log(this.state.selectedFile);

        // Request made to the backend api
        // Send formData object to the correct servlet URL
        axios.post("http://localhost:8082/upload", formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
            .then(response => {
                console.log("File uploaded successfully");
                console.log(response.data);
            })
            .catch(error => {
                console.error("There was an error uploading the file!", error);
            });
    };

    // File content to be displayed after
    // file upload is complete
    fileData = () => {
        if (this.state.selectedFile) {
            const lastModifiedDate = new Date(this.state.selectedFile.lastModified).toDateString();
            return (
                <div>
                    <h2>File Details:</h2>
                    <p>File Name: {this.state.selectedFile.name}</p>
                    <p>File Type: {this.state.selectedFile.type}</p>
                    <p>Last Modified: {lastModifiedDate}</p>
                </div>
            );
        } else {
            return (
                <div>
                    <br />
                    <h4>Choose before Pressing the Upload button</h4>
                </div>
            );
        }
    };

    render() {
        return (
            <div>
                <h1>File Upload App</h1>
                <h3>Upload a file along with caption and date</h3>

                <div>
                    <input type="file" onChange={this.onFileChange} />
                    <br />
                    <input
                        type="text"
                        name="caption"
                        placeholder="Caption"
                        value={this.state.caption}
                        onChange={this.onInputChange}
                    />
                    <br />
                    <input
                        type="date"
                        name="date"
                        value={this.state.date}
                        onChange={this.onInputChange}
                    />
                    <br />
                    <button onClick={this.onFileUpload}>Upload!</button>
                </div>

                {this.fileData()}
            </div>
        );
    }
}

export default App;
