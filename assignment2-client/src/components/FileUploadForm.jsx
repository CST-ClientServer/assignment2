import React, { useState } from 'react';

const FileUploadForm = () => {
    const [caption, setCaption] = useState('');
    const [date, setDate] = useState('');
    const [file, setFile] = useState(null);

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('caption', caption);
        formData.append('date', date);
        formData.append('fileName', file);

        try {
            const response = await fetch('http://localhost:8082', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                const result = await response.text();
                console.log('Success:', result);
            } else {
                console.error('Upload failed:', response.statusText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <label>
                Caption:
                <input
                    type="text"
                    value={caption}
                    onChange={(e) => setCaption(e.target.value)}
                />
            </label>
            <br />
            <label>
                Date:
                <input
                    type="date"
                    value={date}
                    onChange={(e) => setDate(e.target.value)}
                />
            </label>
            <br />
            <label>
                File:
                <input type="file" onChange={handleFileChange} />
            </label>
            <br />
            <button type="submit">Upload</button>
        </form>
    );
};

export default FileUploadForm;
