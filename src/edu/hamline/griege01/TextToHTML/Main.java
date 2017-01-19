package edu.hamline.griege01.TextToHTML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args){
		ArrayList<String> Output = new ArrayList<String>();
		if(args.length > 0){// if there are arguments
			File file = new File(args[0]); // get a file at the path of the first argument
			//System.out.println(args[0]); // print it out for debug
			if(file.exists()){ // if the file exists
				try { 
					FileReader fileReader = new FileReader(file);
					BufferedReader bufferedReader = new BufferedReader(fileReader);			
					for(Object string : bufferedReader.lines().toArray()){
						//String s = BR.readLine();
						//System.out.println(string instanceof String ? string + "" : "null");
						Output.add(string instanceof String ? string + "" : "null");
					}
					bufferedReader.close();
					Output = parse(Output);
					FileWriter FW = new FileWriter(file.getAbsolutePath() + ".html");
					BufferedWriter BW = new BufferedWriter(FW);
					for(String s : Output){
						BW.write(s);
						BW.newLine();
					}
					BW.flush();
					BW.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				System.out.println("Hey! No File! Exiting...");
			}
		}else{
			System.out.println("Nothing Here... bye");
		}
	}
	public static ArrayList<String> parse(ArrayList<String> in){
		ArrayList<String> TMP = new ArrayList<String>();
		ArrayList<String> Comments = new ArrayList<String>();
		TMP.add("<!--Generated using griege01's Text-to-HTML software-->");
		boolean isRight = true;
		
		for(String string : in){
			
			
			Comments.add(string);
			//System.out.println(string);
			boolean isHeader = false;
			if(string.contains("[img:\"")){
				isHeader = true;
				string = InsertImage(string,isRight);
				isRight = !isRight;
			}
			
			
			for(int i = 0; i < 4; i++){
				if(string.startsWith("[" + i + "]")){
					isHeader = true;
					string = string.replace("["+i+"]", "<h"+i+">");
					string += "</h"+i+">";
					if(i == 1){
						string += "<hr>";
					}
				}
			}
			if(!isHeader){
				string = "<p>" + string + "</p>";
			}
			string = string.replace("[b]","<b>").replace("[/b]", "</b>").replace("[i]", "<i>").replace("[/i]","</i>");
			TMP.add(string);
			System.out.println(string);
		}
		TMP.add("<!--This is the text from the original file as comments!-->");
		for(String string : Comments){
			TMP.add("<!--" +string+"-->");
		}
		return TMP;
	}
	public static String InsertImage(String s, boolean isRight){
		String returnStr = s;
		returnStr = returnStr.replace("[img:\"", "<div style=\"background-color:transparent;display:inline;float:"+(isRight ? "right" : "left")+";margin:5px 10px;font-size:1em;background-color:rgb(238,238,238);border:1px solid #c8ccd1\"><img src=").replace("\"]", " style=\"background-color:transparent;display:inline;float:"+(isRight ? "right" : "left")+";margin:5px 5px\"></img><div style=\"font-size:1em;background-color:rgb(238,238,238);border:1px solid #c8ccd1\"><p><i>").replace("[/img]", "</i></p></div></div>");
		return returnStr;
	}

}
