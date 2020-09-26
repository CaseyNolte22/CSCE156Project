package com.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//Class that contains a method for printing objects in an arraylist to a json
public class JsonWrite {

	public static <T> void printJSON(String filePath, ArrayList<T> list, String header) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();

		try {
			PrintWriter out = new PrintWriter(new File(filePath));
			out.write("{\n");
			out.write("\"" + header + "\":");
			out.write(gson.toJson(list));
			out.write("}");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}