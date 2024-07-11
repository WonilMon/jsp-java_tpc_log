package kr.project;

import org.json.JSONArray;
import org.json.JSONObject;

public class Project01_B {

	public static void main(String[] args) {

		JSONArray students = new JSONArray();
		JSONObject student = new JSONObject();// Json 객체 생성

		System.out.println("-----------student-----------");

		student.put("name", "홍길동");
		student.put("phone", "010-1234-5678");
		student.put("address", "서울시 종로구");
		System.out.println(student);

		students.put(student);// Json 객체를 JSONArray에 추가

		student.put("name", "서원일");
		student.put("phone", "010-5555-5555");
		student.put("address", "경기도 파주시");
		System.out.println(student);

		students.put(student);// Json 객체를 JSONArray에 추가

		System.out.println("-----------students-----------");
		System.out.println(students);
		System.out.println(students.toString(2));

		System.out.println("-----------object-----------");
		JSONObject object = new JSONObject();
		object.put("stuInfo", students);
		System.out.println(object);
		System.out.println(object.toString(2));

	}

}
