import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Bookmark{
	public Bookmark() {}
	
	String name= "";
	String datetime= ""; 
	String url = "";
	String groupname = "";
	String memo = "";
	
	Bookmark(String name, String datetime, String url, String groupname, String memo) {
		this.name = name;
		this.datetime = datetime;
		this.url = url;
		this.groupname = groupname;
		this.memo = memo;
	} // ���ڵ��� �Է¹ް� �ϸ�ũ ��ü�� �������ִ� �޼���
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm"); // ���� �ð��� ��ȯ�� ����
	Bookmark(String url) {
		this.url = url;
		this.datetime = LocalDateTime.now().format(formatter);
	} // url�� ���ڷ� �޾� ���� �ð����� ���� �ð��� �־ �ϸ�ũ ��ü�� �߰��ϴ� �޼���

	public void print() {
		System.out.println(this.name + "," + this.datetime + "," + this.url + "," + this.groupname + "," + this.memo);
	} // �ϸ�ũ�� ������ ������ִ� �޼���
	
} // ä�¿� �½��ϴ�.