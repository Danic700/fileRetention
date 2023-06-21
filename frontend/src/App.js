import React, { useState } from 'react';
import axios from 'axios';
import { CopyToClipboard } from 'react-copy-to-clipboard';

function FileUploader() {
  const [file, setFile] = useState(null);
  const [retentionTime, setRetentionTime] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [shareableUrl, setShareableUrl] = useState('');
  const [response, setResponse] = useState('');
  const [error, setError] = useState('');

  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];
    setFile(selectedFile);
  };

  const handleRetentionChange = (event) => {
    setRetentionTime(event.target.value);
  };

  const handleSubmit = async () => {
    try {
      const formData = new FormData();
      formData.append('file', file);

      const headers = {
        'Retention-Time': retentionTime,
      };

      const response = await axios.put('http://localhost:8080/v1/file', formData, { headers });

      // Assuming the response is a string
      const responseData = response.data;

      setModalVisible(true);
      setShareableUrl(responseData);
      setResponse(responseData);
      setError('');
    } catch (error) {
      console.error('Error uploading file:', error);
      setError('Error uploading file. Please try again.');
    }
  };

  const handleCopySuccess = () => {
    console.log('URL copied to clipboard:', shareableUrl);
    // Additional actions after successful copy
  };

  return (
    <div className="card">
      <div className="card-content">
        <h3>File Uploader</h3>
        <input type="file" onChange={handleFileChange} />
        <input
          type="text"
          value={retentionTime}
          onChange={handleRetentionChange}
          placeholder="Retention Time"
        />
        <button onClick={handleSubmit}>Submit</button>
        {error && <p className="error-message">{error}</p>}
      </div>
      {modalVisible && (
        <div className="modal">
          <div className="modal-content">
            <h4>File Uploaded Successfully</h4>
            <p>Shareable URL: {shareableUrl}</p>
            <CopyToClipboard text={shareableUrl} onCopy={handleCopySuccess}>
              <button>Copy URL</button>
            </CopyToClipboard>
          </div>
        </div>
      )}
    </div>
  );
}

export default FileUploader;
