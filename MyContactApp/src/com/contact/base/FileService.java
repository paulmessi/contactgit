package com.contact.base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

public class FileService {

	private Context cxt;
	private File sdCardPath = Environment.getExternalStorageDirectory();

	public FileService(Context _context) {
		this.cxt = _context;
	}

	public FileService() {

	}

	public String getSDCardSysPath() {
		String sdCardSysPath = null;
		sdCardSysPath = this.sdCardPath.toString();
		return sdCardSysPath;

	}

	public boolean creatDir(String dirPath) {
		boolean flag = false;
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
			flag = true;
		}
		return flag;
	}

	public boolean delDirOrFile(String dirOrFilePath) {
		File file = new File(dirOrFilePath);
		return file.delete();
	}

	public boolean checkDirOrFileExists(String dirOrFilePath) {
		File dir = new File(dirOrFilePath);
		return dir.exists();
	}

	public String readFileFromSdCard(String dirPath, String fileName) {
		String outString = null;
		FileInputStream inputStream = null;
		// 缓存流
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		File dir = new File(this.sdCardPath + "/" + dirPath);
		File file = new File(dir + "/" + fileName);

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			try {
				inputStream = new FileInputStream(file);
				int len = 0;
				byte data[] = new byte[1024];
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				outString = new String(outputStream.toByteArray());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return outString;
	}

	public boolean saveFileToSdCard(String dirPath, String fileName,
			String content) {
		boolean flag = false;
		FileOutputStream fileOutputStream = null;

		File dir = new File(this.sdCardPath + "/" + dirPath);
		File file = new File(dir + "/" + fileName);

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			try {
				if (!dir.exists()) {
					dir.mkdirs();
				} else {
					fileOutputStream = new FileOutputStream(file);
					fileOutputStream.write(content.getBytes());
					flag = true;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

}