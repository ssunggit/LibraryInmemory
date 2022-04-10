package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import VO.BookVO;
import VO.UserVO;

public class BookImpl implements Book {
	Scanner sc = new Scanner(System.in);
	public static List<BookVO> bookList = new ArrayList<>();

	@Override
	public void rental() {
		System.out.print("대여할 도서의 제목을 입력하세요 : ");
		String inputTitle = sc.nextLine();
		int test = 0;
		for (int i = 0; i < bookList.size(); i++) {
			if (inputTitle.equals(bookList.get(i).getTitle())) {
				test = 1;
				if (bookList.get(i).getRental() == true) {
					System.out.println("→ 검색하신 [" + bookList.get(i).getTitle() + "] 책은 대여 중입니다.");

				} else {
					System.out.println("검색하신 [" + bookList.get(i).getTitle() + "] 책이 있습니다.");
					System.out.print(" [" + bookList.get(i).getTitle() + "] 책을 대여하시겠습니까?(y/n) : ");
					String yesNo = sc.nextLine();

					if (yesNo.equals("y") || yesNo.equals("Y")) {
						bookList.get(i).setRental(true);
						bookList.get(i).setUserId(UserImpl.loginUserID);
						System.out.println("[" + bookList.get(i).getTitle() + "]" + "대여되었습니다.");
					}
				}
			}
		}
		if (test == 0) {
			System.out.println("→ 검색하신 [" + inputTitle + "] 책이 없습니다.");
		}
	}

	@Override
	public void back() {
		int test = 0;
		if (bookList.size() > 0) {
			// 반납 목록 출력
			for (int i = 0; i < bookList.size(); i++) {
				if (UserImpl.loginUserID.equals(bookList.get(i).getUserId())) {
					System.out.println("● " + bookList.get(i).getTitle());
					test = 1;
				}
			}
		}

		// 반납
		if (test == 1) {
			System.out.print("반납할 책의 제목을 입력해주세요.  =>");
			String backInputTitle = sc.nextLine();
			for (int i = 0; i < bookList.size(); i++) {
				if (backInputTitle.equals(bookList.get(i).getTitle())) {
					bookList.get(i).setRental(false);
					bookList.get(i).setUserId(null);
				}

			}
		} else {
			System.out.println("→ 대여한 책이 없습니다.");

		}

	}

	@Override
	public void myRentalCheck() {
		int test = 0;
		if (bookList.size() > 0) {
			int cnt = 1;
			System.out.println("------------------ < 나의 대여 목록 > ------------------");
			for (int i = 0; i < bookList.size(); i++) {
				if (UserImpl.loginUserID.equals(bookList.get(i).getUserId())) {
					System.out.print("[" + cnt + "]  ");
					System.out.println(bookList.get(i).getTitle());
					cnt++;
					test = 1;
				}
			}
		}
		if (test == 0) {
			System.out.println("→ 대여목록이 없습니다.");
		}
	}

	@Override
	public void showBookList() {
		System.out.println("------------------ < 도서리스트 출력 > ------------------");
		if (bookList.size() > 0) {
			for (int i = 0; i < bookList.size(); i++) {
				System.out.println("[" + (i + 1) + "번 도서]");
				System.out.println("[제목] " + bookList.get(i).getTitle());
				System.out.println("[글쓴이] " + bookList.get(i).getAuthor());
				System.out.println("[출판사] " + bookList.get(i).getPublisher());
				System.out.println();
			}
		} else {
			System.out.println("→ 현재 등록된 책이 없습니다.");
		}
	}

	// 관리자 기능
	@Override
	public void bookAdd() {
		BookVO book = new BookVO();
		if (UserImpl.loginUserID.equals("admin")) {
			System.out.println("--------------------- < 도서 등록 > ---------------------");
			// id 자동생성
			book.setId(bookList.size());
			// 대여여부 false
			book.setRental(false);

			System.out.print("책 제목을 입력해주세요 : ");
			book.setTitle(sc.nextLine());

			System.out.print("지은이를 입력해주세요 : ");
			book.setAuthor(sc.nextLine());

			System.out.print("출판사를 입력해주세요 : ");
			book.setPublisher(sc.nextLine());

			bookList.add(book);
		}
	}

	@Override
	public void bookDel() {
		System.out.println("------------------ < 도서리스트 출력 > ------------------");
		for (int i = 0; i < bookList.size(); i++) {
			System.out.print("[" + (i + 1) + "]  ");
			System.out.println(bookList.get(i).getTitle());
		}

		System.out.print("삭제할 책의 번호를 입력해주세요 : ");
		int delBook = Integer.parseInt(sc.nextLine()) - 1;

		bookList.remove(delBook);

		System.out.println("---------------- < 변경된 도서리스트 출력 > ---------------");
		for (int i = 0; i < bookList.size(); i++) {
			System.out.print("[" + (i + 1) + "]  ");
			System.out.println(bookList.get(i).getTitle());
		}

	}

}
