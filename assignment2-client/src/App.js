// Filename - App.js

import axios from "axios";
import React, { Component } from "react";

class App extends Component {
    state = {
        // Initially, no file is selected
        selectedFile: null,
        caption: "",
        date: "",
        isLoading: false,
        uploadProgress: 0,
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
        this.setState({ isLoading: true, uploadProgress: 0 });
        // Create an object of formData
        const formData = new FormData();

        // Update the formData object
        formData.append("File", this.state.selectedFile);
        formData.append("caption", this.state.caption);
        formData.append("date", this.state.date);

        // Details of the uploaded file
        console.log(this.state.selectedFile);

        // Request made to the backend api
        // Send formData object to the correct servlet URL
        axios.post("http://localhost:8082/assignment_war/", formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            onUploadProgress: ({ loaded, total }) => {
                this.setState({ uploadProgress: Math.round((loaded * 100) / total) });
            }
        })
            .then(response => {
                this.setState({ isLoading: false });
                console.log("File uploaded successfully");
                console.log(response.data);
            })
            .catch(error => {
                this.setState({ isLoading: false });
                console.error("There was an error uploading the file!", error);
            });
    };

    // File content to be displayed after
    // file upload is complete
    fileData = () => {
        if (this.state.selectedFile) {
            const lastModifiedDate = new Date(this.state.selectedFile.lastModified).toDateString();
            return (
                <div style={{ textAlign: "center", marginTop: "20px" }}>
                    <h2>File Details:</h2>
                    <p><strong>File Name:</strong> {this.state.selectedFile.name}</p>
                    <p><strong>File Type:</strong> {this.state.selectedFile.type}</p>
                    <p><strong>Last Modified:</strong> {lastModifiedDate}</p>
                </div>
            );
        } else {
            return (
                <div style={{textAlign: "center"}}>
                    <br/>
                    <h4>Choose a file before pressing the Upload button</h4>
                </div>
            );
        }
    };

    render() {
        return (
            <div style={{textAlign: "center", marginTop: "50px"}}>
                <h1>File Upload Using&nbsp;
                    <img
                        src="/logo192.png"
                        alt="Logo"
                        style={{width: "20px", height: "20px"}}
                    />
                    React</h1>
                <div>
                    <input
                        type="file"
                        onChange={this.onFileChange}
                        style={{marginBottom: "20px", marginLeft: "3%"}}
                    />
                    <br/>
                    <input
                        type="text"
                        name="caption"
                        placeholder="Caption"
                        value={this.state.caption}
                        onChange={this.onInputChange}
                        style={{marginBottom: "20px"}}
                    />
                    <br/>
                    <input
                        type="date"
                        name="date"
                        value={this.state.date}
                        onChange={this.onInputChange}
                        style={{marginBottom: "20px"}}
                    />
                    <br/>
                    <button
                        onClick={this.onFileUpload}
                        disabled={this.state.isLoading}
                    >
                        {this.state.isLoading ? 'Uploading...' : 'Upload!'}
                    </button>
                    <br/>
                    {this.state.isLoading && <progress value={this.state.uploadProgress} max="100"/>}
                </div>
                {this.fileData()}
            </div>
        );
    }
}

export default App;
