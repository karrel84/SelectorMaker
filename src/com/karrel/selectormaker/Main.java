package com.karrel.selectormaker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 안드로이드에서 사용하는 이미지 셀럭터를 자동으로 생성해주기 위해 만들었다.
 * 
 * @author karrel
 *
 */
public class Main {

	private static final String fileNameExtension = ".png"; // 확장자명
	private static final String normalEndsWith = "_n.png"; // 일반 이미지
	private static final String pressedEndsWith = "_p.png"; // 눌렸을때의 이미지

	public static void main(String[] args) {
		File dir = new File("images");
		File[] files = dir.listFiles();

		for (File fn : files) {
			String normalFileName = null; // 일반 파일명

			if (fn.getName().endsWith(normalEndsWith)) { // 끝이 normalEndsWith로 끝나면 파일명을 대입한다.
				normalFileName = fn.getName();
			}

			if (normalFileName == null) { // 파일명이 없으면다음으로 넘긴다.
				continue;
			}

			// pressedEndsWith 부분
			for (File fs : files) {
				String pressedFileName = null; // 눌렸을때의 파일명

				if (fs.getName().endsWith(pressedEndsWith)) { // 끝이 normalEndsWith로 끝나면 파일명을 대입한다.
					pressedFileName = fs.getName();
				}

				if (pressedFileName == null) {
					continue;
				}

				final String unBindedNormalFileName = normalFileName.substring(0,
						normalFileName.length() - normalEndsWith.length());

				final String unBindedPressedFileName = pressedFileName.substring(0,
						pressedFileName.length() - pressedEndsWith.length());

				// System.out.println("unBindedNormalFileName : " + unBindedNormalFileName);
				// System.out.println("unBindedPressedFileName : " + unBindedPressedFileName);

				if (unBindedNormalFileName.equals(unBindedPressedFileName)) {
					// System.out.println("normalFileName : " + normalFileName);
					// System.out.println("pressedFileName : " + pressedFileName);
					// 파일 만들기
					makeFile(normalFileName, pressedFileName);
					break;
				}
			}
		}
	}

	private static void makeFile(String normalFileName, String pressedFileName) {
		try {
			////////////////////////////////////////////////////////////////

			String saveFileName = String.format("selector_%s.xml",
					normalFileName.substring(0, normalFileName.length() - normalEndsWith.length())); // 저장할 파일명
			String saveFilePath = String.format("selector/%s", saveFileName);
			BufferedWriter out = new BufferedWriter(new FileWriter(saveFilePath));
			String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
			out.write(s);
			out.newLine();
			s = "<selector xmlns:android=\"http://schemas.android.com/apk/res/android\">";
			out.write(s);
			out.newLine();

			pressedFileName = pressedFileName.substring(0, pressedFileName.length() - fileNameExtension.length());
			normalFileName = normalFileName.substring(0, normalFileName.length() - fileNameExtension.length());

			s = String.format("<item android:drawable=\"@drawable/%s\" android:state_pressed=\"true\" />",
					pressedFileName);
			out.write(s);
			out.newLine();
			s = String.format("<item android:drawable=\"@drawable/%s\" android:state_focused=\"true\" />",
					pressedFileName);
			out.write(s);
			out.newLine();
			s = String.format("<item android:drawable=\"@drawable/%s\" android:state_selected=\"true\" />",
					pressedFileName);
			out.write(s);
			out.newLine();
			s = String.format("<item android:drawable=\"@drawable/%s\" android:state_checked=\"true\" />",
					pressedFileName);
			out.write(s);
			out.newLine();
			s = String.format("<item android:drawable=\"@drawable/%s\"/>", normalFileName);
			out.write(s);
			out.newLine();
			s = "</selector>";
			out.write(s);
			out.newLine();

			out.close();

			System.out.println("created file " + saveFilePath);
			////////////////////////////////////////////////////////////////
		} catch (IOException e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
			System.exit(1);
		}
	}
}
