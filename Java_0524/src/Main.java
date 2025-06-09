import ui.*;
import util.FontUtil;

public class Main {

	public static void main(String[] args) {
		System.out.println("✅ main 시작됨");
		FontUtil.initDefaultFont("/fonts/Yuzifont.ttf", 14f);
		System.out.println("✅ 폰트 설정 완료");
		Launcher.launch();
		System.out.println("✅ launch 호출됨");
	}

}
