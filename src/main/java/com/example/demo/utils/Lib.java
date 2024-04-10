package com.example.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public final class Lib {
    public static Object convertBase64ToObject(String base64Str) {
		try {

			byte[] byteVal = Base64.getDecoder().decode(base64Str);
			return toObject(byteVal);

		} catch (Exception e) {
			return null;
		}
	}

	public static Object toObject(byte[] bytes) {
		Object object = null;
		try {
			object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
		} catch (java.io.IOException ioe) {
		} catch (java.lang.ClassNotFoundException cnfe) {
		}
		return object;
	}

    public static String convertToBase64Binary(Object jsObject) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // Ghi đối tượng vào luồng
            oos.writeObject(jsObject);
            oos.close();
            // Mã hóa dữ liệu trong luồng thành chuỗi Base64
            byte[] bytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);

		} catch (Exception e) {
            e.printStackTrace();
			return null;
		}
	}

	public static boolean isBlank (String object) {
		return object == null || object.equals("") || object.length() == 0;
	}
}
