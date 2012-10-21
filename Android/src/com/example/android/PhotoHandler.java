package com.example.android;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.widget.Toast;

/**
 * 	 Copyright© 2012, Grupp02
 * 
 *     This file is part of Handalfabetet.
 *
 *   Handalfabetet is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Handalfabetet is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Handalfabetet.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * The PhotoHandler class is used to handle taking pictures with the camera.
 * 
 * @author : Grupp02
 * @version : 2012-10-19, v1.0
 */
public class PhotoHandler implements PictureCallback {

    private final Context context;

    public PhotoHandler(Context context) {
	this.context = context;
    }

    public void onPictureTaken(byte[] data, Camera camera) {

	File pictureFileDir = getDir();

	if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

	    Log.d(BaseColumns._COUNT, "Can't create directory to save image.");
	    Toast.makeText(context, "Can't create directory to save image.",
		    Toast.LENGTH_LONG).show();
	    return;

	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
	String date = dateFormat.format(new Date());
	String photoFile = "Picture_" + date + ".jpg";

	String filename = pictureFileDir.getPath() + File.separator + photoFile;

	File pictureFile = new File(filename);

	try {
	    FileOutputStream fos = new FileOutputStream(pictureFile);
	    fos.write(data);
	    fos.close();
	    Toast.makeText(context, "New Image saved:" + photoFile,
		    Toast.LENGTH_LONG).show();
	} catch (Exception error) {
	    Log.d(BaseColumns._COUNT,
		    "File" + filename + "not saved: " + error.getMessage());
	    Toast.makeText(context, "Image could not be saved.",
		    Toast.LENGTH_LONG).show();
	}
    }

    private File getDir() {
	File sdDir = Environment
		.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	return new File(sdDir, "CameraAPIDemo");
    }
}