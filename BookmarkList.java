import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.DateTimeException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BookmarkList {
	private int index;
	private Bookmark[] bm;
	
	private int isBMcheck(String[] str) {
		if(isDateTimeright(str[1])==1 && isURLright(str[2])==1) {
			return 1;
		} else {
			return 0;
		}
	}
	private int isURLright(String input) {
		try {
			URL nurl = new URL(input);
			return 1;	
		} catch(MalformedURLException e){ //���� url ������ �����ϸ� 
			System.out.print("MalformedURLException: wrong URL - No URL ; invalid Bookmark info line: ");
		} catch(Exception e){ // �� �� ����ġ ���� ������ ��� ���� ���� ���
			e.printStackTrace();
		}
		return 0;
	}
	private int isDateTimeright(String input) {
		String regex = "^19[0-9][0-9]|20[0-2][0-2]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])_([0-1][0-9]|2[0-3]):[0-5][0-9]$";
		// yyyy-MM-dd_HH:mm�̶�� ��¥/�ð��� ����� ��Ÿ���� ����ǥ����
		try {
			if(input.matches(regex)) {
				return 1;
			} else { // ���Ŀ� ���� �ʴ´ٸ� ���� ������ �߻�
				throw new Exception();
			}
		} catch(Exception e){ 
			System.out.print("Date Format Error -> No Created Time invalid Bookmark info line: ");
		}
		return 0;
	}
	
	public void makeBookmark(File file) {
		try {
			Scanner input = new Scanner(file);
			while(input.hasNext()) {
				String line = input.nextLine();
				if(line.startsWith("//") || line.isEmpty()){ // �ּ� �Ǵ� ������ ��� �ϸ�ũ ��ü�� ��� �� ����.
					 continue;
				}
				String[] data = line.split(",|;", 5);
				String[] information = new String[5]; // ũ�Ⱑ 5�� ������ ���ڿ� �迭 ����
				Arrays.fill(information, ""); // ���� �̸� ������ �Է��ص�
				for(int i=0; i<data.length; i++) { // data �迭�� ũ�⸸ŭ ��ȸ
					data[i] = data[i].trim(); // ���� ����
					information[i] = data[i]; // information �迭�� ����.
				}	
				
				if(isBMcheck(information)==1) { // ���� ������ �����ϸ� �ϸ�ũ ��ü�� ����. 
					bm[index] = new Bookmark(information[0], information[1], information[2], information[3], information[4]);
					// ���� �Ľ̵� �����͸� ���� data �迭�� ũ�Ⱑ 5�� �ƴϴ���, �̹� information�� �ε��� 5������ ���� �̹� �������� �����ϰ� �ֱ� ������,
					// �� �������� ArrayIndexOutOfBoundsException�� �߻����� ����.
					bm[index].print();
					index++;
				} else {
					System.out.println(line); // ���� ������ ������� ���ϸ� �� line�� �����. �� line�� �ϸ�ũ�� ����� ����.
				}
			}
			input.close();
		} catch(IOException e) { // ���� �б� ����
			System.out.println("Unknown BookmarkList data File");
		} catch(Exception e) { // �� ���� ����ó��
			e.printStackTrace();
		}
	} 
	
	
	BookmarkList(String FileName) {
		bm = new Bookmark[100];
		index = 0;
		try {
			File file = new File(FileName);
			makeBookmark(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} // �ϸ�ũ ��� ����
	public int numBookmarks() {
		return index;
	} // ��ü �ϸ�ũ�� ������ �˷��ִ� �޼���
	public Bookmark getBookmark(int i) {
		return bm[i];
	} // i��° �ϸ�ũ�� ���� ���� ������ ����ϴ� �޼���
	
	public void mergeByGroup() {
		try {
			for(int i=0; i<index; i++) {
				for(int j=i+1; j<index; j++) {
					if(getBookmark(i).groupname.equals(getBookmark(j).groupname) && !getBookmark(i).groupname.isEmpty()){
						// �� �ϸ�ũ�� �׷���� ���� ��� ������ ����, �� �׷���� ����(����ִ�) ��� ���������� ó��.
						Bookmark tmp = bm[j]; // j�ε��� �ϸ�ũ�� �޾ƿ�					
						for(int k=j; k>i+1; k--) { // j���� i+2���� �������� ��ȸ
							bm[k] = bm[k-1]; // i+1���� j-1������ �ϸ�ũ �迭�� ��°�� �� ĭ�� ���������� �о.
						}	
						bm[i+1] = tmp; // i+1��° �ε����� j��° �ε��� �ϸ�ũ ��ü�� ����.
						i++;
					}
				}
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}
	} // ���� �׷� �̸��� ���� �ϸ�ũ���� ��� �������ϴ� �޼���
	
	public void WriteBookmarkToFile(String file) {
		try {
		File tofile = new File(file);
		PrintWriter pw = new PrintWriter(tofile);
		for(int i=0; i<index; i++) {
			pw.println(getBookmark(i).name+ "," + getBookmark(i).datetime+ "," + getBookmark(i).url+ "," + getBookmark(i).groupname+ "," +getBookmark(i).memo);
		}
		pw.flush();
		pw.close();
	} catch (Exception e) {
		e.printStackTrace();
		}
	} // BookmarkList�� ��� �ϸ�ũ ��ü���� ������ ���Ͽ� ����ִ� �޼���
}