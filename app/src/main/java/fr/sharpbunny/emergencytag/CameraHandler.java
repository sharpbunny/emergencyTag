package fr.sharpbunny.emergencytag;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class CameraHandler implements PictureCallback{

    private final Context context;
    private File pictureFile;

    public CameraHandler(Context context) {
        this.context = context;
    }
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        File pictureFileDir = getDir();

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Log.e(CameraActivity.TAG, "Can't create directory to save image.");
            Toast.makeText(context, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return;

        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + date + ".jpg";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;

        pictureFile = new File(filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            Log.d(CameraActivity.TAG, "File" + filename + " saved!");
            Toast.makeText(context, "New Image saved:" + photoFile,
                    Toast.LENGTH_LONG).show();
            // send file to rest server
            new PostPicture().execute();

        } catch (Exception error) {
            Log.e(CameraActivity.TAG, "File" + filename + "not saved: "
                    + error.getMessage());
            Toast.makeText(context, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStorageDirectory();
        Log.d(CameraActivity.TAG, "Directory to store pictures: " + sdDir.toString());
        return new File(sdDir, "EmergencyTag");
    }

    /**
     * Async task to post picture
     */
    private class PostPicture extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "Sending file", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.i(TAG,"Path:" + pictureFile);
            try {
                Log.i(TAG, "start upload");
                put(context.getResources().getString(R.string.FileUploadUrl), pictureFile, null, null);
            } catch (Exception e) {
                Log.e(TAG, "file error");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(context, "File transfer ended",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Upload a file. take care to use in async task
     * @param targetURL url of server where we upload
     * @param file filename with path of the file we want to upload
     * @param username username if needed otherwise null
     * @param password password
     * @throws Exception
     */
    public static void put(String targetURL, File file, String username, String password) throws Exception {

        // Separator for multipart message
        String BOUNDARY = "===" + System.currentTimeMillis() + "===";
        String LINE_FEED = "\r\n";
        String fileName = file.getName();
        HttpURLConnection conn = null;

        try {

            Log.i(TAG, "Trying to upload: ");
            // These strings are sent in the request body. They provide information about the file being uploaded
            String contentDisposition = "Content-Disposition: form-data; name=\"userfile\"; filename=\"" + fileName + "\"";
            String contentType = "Content-Type: " + URLConnection.guessContentTypeFromName(fileName);

            // Make a connection to the server
            URL url = new URL(targetURL);
            conn = (HttpURLConnection) url.openConnection();

            // Put the authentication details in the request if username present
            if (username != null) {
                String usernamePassword = username + ":" + password;
                String encodedUsernamePassword = Base64.encodeToString(usernamePassword.getBytes(),Base64.DEFAULT);
                conn.setRequestProperty ("Authorization", "Basic " + encodedUsernamePassword);
            }

            Log.i(TAG, "Trying to set connection ");
            // Allow Outputs (POST)
            conn.setDoOutput(true);
            // Allow Inputs for the response from the server
            conn.setDoInput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            Log.i(TAG, "Trying to send body ");
            // Send the body
            DataOutputStream dataOS = new DataOutputStream(conn.getOutputStream());
            //dataOS.writeBytes(requestBody.toString());
            dataOS.writeBytes("--"+BOUNDARY + LINE_FEED);
            dataOS.writeBytes(contentDisposition + LINE_FEED);
            dataOS.writeBytes(contentType + LINE_FEED);
            dataOS.writeBytes("Content-Transfer-Encoding: binary");
            dataOS.writeBytes(LINE_FEED + LINE_FEED);
            dataOS.write(getBytesFromFile(file));
            dataOS.writeBytes(LINE_FEED + LINE_FEED);
            dataOS.writeBytes("--" + BOUNDARY + "--");
            dataOS.flush();
            dataOS.close();

            // Sent has been done, getting the response
            Log.i(TAG, "Trying to get response");
            // Ensure we got the HTTP 200 response code
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new Exception(String.format("Received the response code %d from the URL %s", responseCode, url));
            }
            Log.i(TAG, "Got response code from server: " + responseCode);

            // Read the response
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int bytesRead;
            while((bytesRead = is.read(bytes)) != -1) {
                baos.write(bytes, 0, bytesRead);
            }
            byte[] bytesReceived = baos.toByteArray();
            baos.close();

            is.close();
            String response = new String(bytesReceived);
            Log.i(TAG, "End of upload: " + response);

            // TODO: Do something here to handle the 'response' string

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

    }
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large >2Gb
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            is.close();
            throw new IOException("Could not completely read file "+file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
