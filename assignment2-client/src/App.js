// Filename - App.js

import axios from "axios";
import React, { useState } from "react";

const App = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [caption, setCaption] = useState("");
    const [date, setDate] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [uploadProgress, setUploadProgress] = useState(0);

    // On file select (from the pop up)
    const onFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
    };

    // On input change for caption and date
    const onInputChange = (event) => {
        const { name, value } = event.target;
        if (name === "caption") setCaption(value);
        if (name === "date") setDate(value);
    };

    // On file upload (click the upload button)
    const onFileUpload = async () => {
        if (!selectedFile) {
            alert("Please select a file to upload.");
            return;
        }

        setIsLoading(true);
        setUploadProgress(0);

        // Create an object of formData
        const formData = new FormData();
        formData.append("File", selectedFile);
        formData.append("caption", caption);
        formData.append("date", date);

        try {
            const response = await axios.post(
                "http://localhost:8082/assignment_war/",
                formData,
                {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                    onUploadProgress: ({ loaded, total }) => {
                        setUploadProgress(Math.round((loaded * 100) / total));
                    },
                },
            );
            console.log("File uploaded successfully:", response.data);
        } catch (error) {
            console.error("There was an error uploading the file!", error);
        } finally {
            setIsLoading(false);
        }
    };

    // File content to be displayed after file upload is complete
    const fileData = () => {
        if (selectedFile) {
            const lastModifiedDate = new Date(
                selectedFile.lastModified,
            ).toDateString();
            return (
                <div style={{ textAlign: "center", marginTop: "20px" }}>
                    <h2>File Details:</h2>
                    <p>
                        <strong>File Name:</strong> {selectedFile.name}
                    </p>
                    <p>
                        <strong>File Type:</strong> {selectedFile.type}
                    </p>
                    <p>
                        <strong>Last Modified:</strong> {lastModifiedDate}
                    </p>
                </div>
            );
        } else {
            return (
                <div style={{ textAlign: "center" }}>
                    <br />
                    <h4>Choose a file before pressing the Upload button</h4>
                </div>
            );
        }
    };

    return (
        <div style={{ textAlign: "center", marginTop: "50px" }}>
            <h1>
                File Upload Using&nbsp;
                <img
                    src="/logo192.png"
                    alt="Logo"
                    style={{ width: "20px", height: "20px" }}
                />
                React
            </h1>
            <div>
                <input
                    type="file"
                    onChange={onFileChange}
                    style={{ marginBottom: "20px", marginLeft: "3%" }}
                />
                <br />
                <input
                    type="text"
                    name="caption"
                    placeholder="Caption"
                    value={caption}
                    onChange={onInputChange}
                    style={{ marginBottom: "20px" }}
                />
                <br />
                <input
                    type="date"
                    name="date"
                    value={date}
                    onChange={onInputChange}
                    style={{ marginBottom: "20px" }}
                />
                <br />
                <button onClick={onFileUpload} disabled={isLoading}>
                    {isLoading ? "Uploading..." : "Upload!"}
                </button>
                <br />
                {isLoading && <progress value={uploadProgress} max="100" />}
            </div>
            {fileData()}
        </div>
    );
};

export default App;
