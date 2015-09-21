package jpabook.second.chapter8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestFetchType2 {

	public static void main(String[] args) {

		String x = "1";
		switch (x) {
		case "Monday":
			System.out.println("x1");
		default:
			System.out.println("xdef");
		}
		
		List<Map<String, Object>> list = new ArrayList<>();
		System.out.println(list);

		try (InputStream in = new FileInputStream(new File("file-input-stream.xml"));
				OutputStream out = new FileOutputStream(new File("output-stream.log"));
				FileReader xyff = new FileReader(new File("readers.text"));
				Connection con = DriverManager.getConnection("jdbc:@12.168a.sdf");
				PreparedStatement ps = con.prepareStatement("select 1 from dual");) {

		} catch (IOException | SQLException ex) {

		}
		
		enumtest(PostType.MGR);
		enumtest(PostType.TESTER);

	}
	
	private static void enumtest(PostType type) {
		switch(type) {
		case INCIDED:
			System.out.println(type + "1");
			break;
		case MGR:
			System.out.println(type + "2");
			break;
		default:
			System.out.println("otherwise: " + type);
			break;
		}
	}

	public String getTypeOfDayWithSwitchStatement(String dayOfWeekArg) {
		String typeOfDay;
		switch (dayOfWeekArg) {
		case "Monday":
			typeOfDay = "Start of work week";
			break;
		case "Tuesday":
		case "Wednesday":
		case "Thursday":
			typeOfDay = "Midweek";
			break;
		case "Friday":
			typeOfDay = "End of work week";
			break;
		case "Saturday":
		case "Sunday":
			typeOfDay = "Weekend";
			break;
		default:
			throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeekArg);
		}
		return typeOfDay;
	}

	private static enum PostType {
		TESTER, MGR, OFF, INCIDED
	}
}
