import java.io.File;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("getPath");
		System.out.println("''=" + new File("abc/a.txt").getPath());
		System.out.println("./=" + new File("./a.txt").getPath());
		System.out.println("/=" + new File("/a.txt").getPath());
		System.out.println();
		System.out.println("getAbsolutePath");
		System.out.println("''=" + new File("abc/a.txt").getAbsolutePath());
		System.out.println("''=" + new File("abc/a.txt").getAbsoluteFile());
		System.out.println("./=" + new File("./a.txt").getAbsolutePath());
		System.out.println("/=" + new File("/a.txt").getAbsolutePath());
		
		System.out.println();
		System.out.println(Test.class.getResource("/Test.class").getFile());
		System.out.println(Test.class.getResource("/Test.class").getPath());
	}

}
