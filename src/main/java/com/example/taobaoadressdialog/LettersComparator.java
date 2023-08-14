package com.example.taobaoadressdialog;



import java.util.Comparator;

public class LettersComparator<T extends FirstLetterBean> implements Comparator<T> {

	public int compare(T o1, T o2) {
		if(o1!=null && o2!=null) {
			if (o1.getFirstLetter().equals("@")
					|| o2.getFirstLetter().equals("#")) {
				return 1;
			} else if (o1.getFirstLetter().equals("#")
					|| o2.getFirstLetter().equals("@")) {
				return -1;
			} else {
				return o1.getFirstLetter().compareTo(o2.getFirstLetter());
			}
		}
		return -1;
	}

}
